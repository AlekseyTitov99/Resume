package com.pagani.youtuberegistry.api;

import com.google.gson.Gson;
import com.pagani.youtuberegistry.Main;
import com.pagani.youtuberegistry.objeto.YoutubeUser;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Storage {

    private String fac;

    public void exportObject(YoutubeUser upgradeObject) {
        String object = encode(upgradeObject);
        File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + upgradeObject.getUser() + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfig.loadConfiguration(bruteFile);
        yamlConfiguration.set("user",object);
        try {
            yamlConfiguration.save(bruteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayer(YoutubeUser upgradeObject) {
        String object = encode(upgradeObject);
        File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + upgradeObject.getUser() + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfig.loadConfiguration(bruteFile);
        yamlConfiguration.set("user",object);
        try {
            yamlConfiguration.save(bruteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteObject(String fac){
        File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
        bruteFile.delete();
    }

    public void importObject(String fac){
        File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
        if (!bruteFile.exists()){
            try {
                bruteFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yamlConfiguration = YamlConfig.loadConfiguration(bruteFile);
        YoutubeUser upgradeObject =  null;
        if (yamlConfiguration.get("user") == null || yamlConfiguration.getString("user").isEmpty()){
            return;
        } else {
            upgradeObject = decode(yamlConfiguration.getString("user"), YoutubeUser.class);
            Main.cache.put(upgradeObject.getUser(),upgradeObject);
            return;
        }
    }

    public static  String encode(Object classz) {
        Gson gson = new Gson();
        String objectencoded = "";
        try {
            objectencoded = gson.toJson(classz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectencoded;
    }

    public static <T> T decode(String encoded, Class<T> classz) {
        Gson gson = new Gson();
        T f = null;
        try {
            f = gson.fromJson(encoded, classz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

}