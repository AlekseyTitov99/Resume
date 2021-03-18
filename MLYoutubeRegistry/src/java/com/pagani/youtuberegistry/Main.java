package com.pagani.youtuberegistry;

import com.pagani.youtuberegistry.api.Storage;
import com.pagani.youtuberegistry.commands.ComandoColetar;
import com.pagani.youtuberegistry.objeto.YoutubeUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

    public static HashMap<String, YoutubeUser> cache = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("ml.youtuber")) {
            Storage storage = new Storage();
            storage.importObject(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if (Main.cache.containsKey(e.getPlayer().getName())) {
            Storage storage = new Storage();
            storage.savePlayer(Main.cache.get(e.getPlayer().getName()));
            Main.cache.remove(e.getPlayer().getName());
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("§6[ML] §7YoutubeRegistry turning on");
        getCommand("yt").setExecutor(new ComandoColetar());
        this.saveDefaultConfig();
        CreateFolder();
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.hasPermission("ml.youtuber")) {
                Storage storage = new Storage();
                storage.importObject(player.getName());
            }
        }
        getServer().getPluginManager().registerEvents(this,this);
    }

    public void CreateFolder(){
        try {
            File pasta = new File(Main.getPlugin(Main.class).getDataFolder()+ File.separator + "storage");
            if(!pasta.exists()){
                pasta.mkdirs();
            }
        } catch(SecurityException e) {
            Bukkit.getConsoleSender().sendMessage(Main.getPlugin(Main.class).getConfig().getString("Falha-Ao-Criar-Pasta").replace("&", "§").replace("%pasta%", "logs"));
        }
    }

}