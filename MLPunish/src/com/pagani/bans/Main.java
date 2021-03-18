package com.pagani.bans;

import com.google.common.io.ByteStreams;
import com.pagani.bans.Dc.WarnPunish;
import com.pagani.bans.commands.CommandDespunir;
import com.pagani.bans.commands.CommandHistoric;
import com.pagani.bans.commands.CommandPunish;
import com.pagani.bans.commands.CommandStaff;
import com.pagani.bans.listeners.General;
import com.pagani.bans.objetos.Diario;
import com.pagani.bans.objetos.PunishUser;
import com.pagani.bans.storage.Storage;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Main extends Plugin {

    private static Main main;
    public static Configuration config;

    public static Main getInstance() {
        return main;
    }

    public static HashMap<String, PunishUser> cache = new HashMap<>();

    public static int ID;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Main.ID = ID;
    }


    @Override
    public void onEnable(){
        main = this;
        getProxy().getPluginManager().registerCommand(this, new CommandPunish("punir"));
        getProxy().getPluginManager().registerCommand(this, new CommandDespunir("despunir"));
        getProxy().getPluginManager().registerCommand(this, new CommandHistoric("historico"));
        getProxy().getPluginManager().registerCommand(this, new CommandStaff());
        getProxy().getPluginManager().registerListener(this,new General());
        getProxy().getPluginManager().registerListener(this,new WarnPunish(main));
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        Diario.startRunnable();
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                     ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        } else {
            try {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            File pasta = new File(Main.getInstance().getDataFolder()+ File.separator + "storage");
            if(!pasta.exists()){
                pasta.mkdirs();
            }
        } catch(SecurityException e) {
        }
    }
}