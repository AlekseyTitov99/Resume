package com.pagani.manopla;

import com.pagani.manopla.command.commandbeacon;
import com.pagani.manopla.command.commandjoia;
import com.pagani.manopla.listener.GeneralEvents;
import com.pagani.manopla.objeto.ManoplaUser;
import com.pagani.manopla.sql.AtlasStorage;
import com.pagani.manopla.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {
    public static MySQL mySQLconnection;

    public static HashMap<String, ManoplaUser> cache = new HashMap<>();

    @Override
    public void onEnable(){
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("§6[ML] §eJoias on");
        Bukkit.getConsoleSender().sendMessage("§6[ML] §eJoias sql:");
        mySQLconnection = new MySQL(Main.getPlugin(Main.class),this.getConfig().getString("host"), this.getConfig().getString("username")
                , this.getConfig().getString("password"), this.getConfig().getString("database"), 3306);
        mySQLconnection.addTableStatement("CREATE TABLE IF NOT EXISTS `manopla` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`jogador` VARCHAR(50) NOT NULL , `json` MEDIUMTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLconnection.openConnection();
        this.saveDefaultConfig();
        for (Player p : Bukkit.getOnlinePlayers()){
            AtlasStorage.loadUser(p.getName());
        }
        getCommand("manopla").setExecutor(new commandbeacon());
        getCommand("joia").setExecutor(new commandjoia());
        getServer().getPluginManager().registerEvents(new GeneralEvents(),this);
        GeneralEvents.onTT();
        GeneralEvents.Poder();
    }

    @Override
    public void onDisable(){
        mySQLconnection.closeConnection();
    }

}