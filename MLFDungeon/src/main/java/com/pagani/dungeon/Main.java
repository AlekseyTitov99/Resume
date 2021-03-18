package com.pagani.dungeon;

import com.pagani.dungeon.commands.ComandoCorreio;
import com.pagani.dungeon.commands.DungeonCommand;
import com.pagani.dungeon.commands.PartyCommand;
import com.pagani.dungeon.commands.SetDungeonNPCCommand;
import com.pagani.dungeon.listeners.GeneralClick;
import com.pagani.dungeon.listeners.GeneralSQL;
import com.pagani.dungeon.mysql.AtlasStorage;
import com.pagani.dungeon.mysql.MySQL;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.objetos.Party;
import com.pagani.dungeon.util.DungeonNPC;
import com.pagani.dungeon.util.Hidder;
import com.sun.jndi.dns.DnsClient;
import net.citizensnpcs.api.CitizensAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static HashMap<String,Party> cacheparty = new HashMap<>();
    public static HashMap<String, DungeonUser> cache = new HashMap<>();
    public static MySQL mySQLConnection;
    public static Economy econ;

    @Override
    public void onDisable(){
        for (Map.Entry<String, DungeonUser> stringDungeonUserEntry : cache.entrySet()) {
            AtlasStorage.savePlayer(stringDungeonUserEntry.getKey(),stringDungeonUserEntry.getValue(),false);
        }
        if (CitizensAPI.getNPCRegistry().getById(3306) != null) {
            if (CitizensAPI.getNPCRegistry().getById(3306).isSpawned()) {
                CitizensAPI.getNPCRegistry().getById(3306).despawn();
                CitizensAPI.getNPCRegistry().deregister(CitizensAPI.getNPCRegistry().getById(3306));
            }
        }
        DungeonNPC.despawn();
    }
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupEconomy();
        getCommand("party").setExecutor(new PartyCommand());
        getCommand("correio").setExecutor(new ComandoCorreio());
        getCommand("dungeon").setExecutor(new DungeonCommand());
        getCommand("setdungeonnpc").setExecutor(new SetDungeonNPCCommand());
        getServer().getPluginManager().registerEvents(new GeneralSQL(),this);
        getServer().getPluginManager().registerEvents(new GeneralClick(),this);
        mySQLConnection = new MySQL(this,"149.56.148.216", "u1108_bO9m3WmHdv"
                , "jQrxMClPI4JrbPCcKTk6DpPo", "s1108_geral", 3306);
        mySQLConnection.addTableStatement("CREATE TABLE IF NOT EXISTS `dungeon` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`jogador` VARCHAR(50) NOT NULL , `json` MEDIUMTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLConnection.addTableStatement("CREATE TABLE IF NOT EXISTS `partys` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`jogador` VARCHAR(50) NOT NULL , `json` MEDIUMTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLConnection.openConnection();
        createStorage();
        esconderTagNPC();
        DungeonNPC.spawn();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            AtlasStorage.loadUser(onlinePlayer.getName());
            if (CitizensAPI.getNPCRegistry().getById(3306) != null) {
                if (CitizensAPI.getNPCRegistry().getById(3306).isSpawned()) {
                    Entity entity = CitizensAPI.getNPCRegistry().getById(3306).getEntity();
                    Hidder.hideNPCNameTag(onlinePlayer,entity);
                }
            }
        }
        getServer().getMessenger().registerOutgoingPluginChannel( this, "DungeonMessage");
        this.saveConfig();
    }

    public void esconderTagNPC(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (CitizensAPI.getNPCRegistry().getById(3306) != null) {
                if (CitizensAPI.getNPCRegistry().getById(3306).isSpawned()) {
                    Entity entity = CitizensAPI.getNPCRegistry().getById(3306).getEntity();
                    Hidder.hideNPCNameTag(player,entity);
                }
            }
        }
    }

    private void createStorage(){
        try {
            File pasta = new File(Main.getPlugin(Main.class).getDataFolder()+ File.separator + "storage");
            if(!pasta.exists()){
                pasta.mkdirs();
            }
        } catch(SecurityException e) {
            Bukkit.getConsoleSender().sendMessage(Main.getPlugin(Main.class).getConfig().getString("Falha-Ao-Criar-Pasta").replace("&", "§").replace("%pasta%", "logs"));
        }
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>) this.getServer().getServicesManager().getRegistration((Class) Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = (Economy) rsp.getProvider();
        return this.econ != null;
    }


}