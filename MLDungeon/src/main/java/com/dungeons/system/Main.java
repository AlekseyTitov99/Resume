package com.dungeons.system;

import com.dungeons.system.SQL.AtlasStorage;
import com.dungeons.system.SQL.MySQL;
import com.dungeons.system.commands.CommandDungeon;
import com.dungeons.system.commands.CommandReady;
import com.dungeons.system.dungeon.Dungeon;
import com.dungeons.system.listeners.*;
import com.dungeons.system.objeto.DungeonUser;
import com.dungeons.system.scoreboard.ScoreBoard;
import com.dungeons.system.socket.SocketThread;
import com.onarandombox.MultiverseCore.MultiverseCore;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static HashMap<String, Dungeon> cache = new HashMap<>();
    public static HashMap<String, DungeonUser> cacheuser = new HashMap<>();
    public static MySQL mySQLConnection;
    public static SocketThread socketThread;
    public static int sala = 0;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
        getCommand("pronto").setExecutor(new CommandReady());
        getCommand("dungeon").setExecutor(new CommandDungeon());
        getServer().getPluginManager().registerEvents(new GeneralJoin(),this);
        getServer().getPluginManager().registerEvents(new GeneralEntitys(),this);
        getServer().getPluginManager().registerEvents(new GeneralSetup(),this);
        getServer().getPluginManager().registerEvents(new GeneralNPC(),this);
        getServer().getPluginManager().registerEvents(new GeneralChest(),this);
        getServer().getPluginManager().registerEvents(new GeneralPreventBlock(),this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(),this);
        getServer().getPluginManager().registerEvents(new GeneralBugsCorrect(),this);
        mySQLConnection = new MySQL(this,"149.56.148.216", "u1108_bO9m3WmHdv"
                , "jQrxMClPI4JrbPCcKTk6DpPo", "s1108_geral", 3306);
        mySQLConnection.addTableStatement("CREATE TABLE IF NOT EXISTS `dungeon` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`jogador` VARCHAR(50) NOT NULL , `json` MEDIUMTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLConnection.addTableStatement("CREATE TABLE IF NOT EXISTS `partys` ( `id` INT NOT NULL AUTO_INCREMENT , " +
                "`jogador` VARCHAR(50) NOT NULL , `json` MEDIUMTEXT NOT NULL , PRIMARY KEY (`id`));");
        //number
        mySQLConnection.openConnection();
        for (int i = 0; i < 10; i++) {
            if (Bukkit.getWorld("sala" + i) != null){
                MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().deleteWorld("sala" + i);
                MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().cloneWorld("wl", "sala" + i);
            } else {
                MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().cloneWorld("wl", "sala" + i);
            }
        }
        AtlasStorage.saveNumber(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, Dungeon> stringDungeonEntry : cache.entrySet()) {
                    for (String jogadore : stringDungeonEntry.getValue().getJogadores()) {
                        if (Bukkit.getPlayer(jogadore) == null) continue;
                        if (stringDungeonEntry.getValue().getFase() > 1){
                            ScoreBoard.updateScoreBoard(Bukkit.getPlayer(jogadore),stringDungeonEntry.getValue());
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),0L,20L);
        new BukkitRunnable() {
            @Override
            public void run() {
                socketThread = new SocketThread();
                socketThread.start();
            }
        }.runTaskAsynchronously(Main.getPlugin(Main.class));
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        socketThread.interrupt();
        mySQLConnection.closeConnection();
        CitizensAPI.getNPCRegistry().deregisterAll();
    }

    public static int getSala() {
        if (sala == 10){
        }
        return sala;
    }

    public static void setSala(int sala) {
        Main.sala = sala;
    }

}