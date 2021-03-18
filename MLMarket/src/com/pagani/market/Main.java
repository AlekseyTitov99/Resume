package com.pagani.market;


import com.pagani.market.api.Category;
import com.pagani.market.api.Item;
import com.pagani.market.commands.MercadoCommand;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.listener.*;
import com.pagani.market.menu.Mercado;
import com.pagani.market.objeto.User;
import com.pagani.market.sql.AtlasStorage;
import com.pagani.market.sql.MySQL;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main extends JavaPlugin {


    public static HashMap<CategoryType, Category> cache = new HashMap<>();
    public static Economy economy;
    public static HashMap<String, User> cache2 = new HashMap<>();

    public static void onExpirar() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<CategoryType, Category>> iterator = cache.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<CategoryType, Category> categoryEntry = iterator.next();
                    Iterator<Item> itemIterator = categoryEntry.getValue().getItemsInCategory().iterator();
                    while (itemIterator.hasNext()) {
                        Item item = itemIterator.next();
                        if (System.currentTimeMillis() > item.getaLong() + 1000L * 60L + 60L) {
                            if (Main.cache2.containsKey(item.getSender())) {
                                itemIterator.remove();
                                User user = Main.cache2.get(item.getSender());
                                user.getVendendo().removeIf(s -> s.equals(item));
                                if (!user.getExpirados().contains(item)) {
                                    user.getExpirados().add(item);
                                }
                            } else {
                                itemIterator.remove();
                                AtlasStorage.RemoveItemFromPessoalVendaAndExpire(item.getSender(),item);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L*4);
    }

    public static MySQL mySQLConnection;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        mySQLConnection = new MySQL(Main.getPlugin(Main.class), this.getConfig().getString("host"), this.getConfig().getString("username")
                , this.getConfig().getString("password"), this.getConfig().getString("database"), 3306);
        mySQLConnection.addTableStatement("CREATE TABLE IF NOT EXISTS `categoria` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`usuario` VARCHAR(50) NOT NULL , `json` LONGTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLConnection.addTableStatement("CREATE TABLE IF NOT EXISTS `mercadousers` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`user` VARCHAR(50) NOT NULL , `json` LONGTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLConnection.openConnection();
        basicSystems();
        this.saveDefaultConfig();
        getCommand("mercado").setExecutor(new MercadoCommand());
        getServer().getPluginManager().registerEvents(new GeneralListeners(), this);
        getServer().getPluginManager().registerEvents(new Mercado(), this);
        getServer().getPluginManager().registerEvents(new GeneralGetExpired(), this);
        getServer().getPluginManager().registerEvents(new GeneralHistoric(), this);
        getServer().getPluginManager().registerEvents(new GeneralGuiHolder(), this);
        getServer().getPluginManager().registerEvents(new GeneralPrivadoHolder(), this);
        getServer().getPluginManager().registerEvents(new GeneralVendaHolder(), this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            AtlasStorage.loadUser(p.getName());
        }
    }

    public void basicSystems(){
        setupEconomy();
        AtlasStorage.loadCategorias();
        onExpirar();
        runnables();
    }

    public void runnables(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<CategoryType, Category> category : cache.entrySet()) {
                    AtlasStorage.saveCategory(category.getKey().name().toUpperCase(), category.getValue(), true);
                }
                for (Map.Entry<String, User> stringUserEntry : cache2.entrySet()) {
                    AtlasStorage.savePlayer(stringUserEntry.getKey(), stringUserEntry.getValue(), true);
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),20L*2,20L*20);
    }

    @Override
    public void onDisable() {
        mySQLConnection.closeConnection();
    }

    public static boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Main.getPlugin(Main.class).getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }


}