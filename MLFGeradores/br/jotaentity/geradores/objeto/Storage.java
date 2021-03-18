package br.jotaentity.geradores.objeto;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.utils.YamlConfig;
import com.google.gson.Gson;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class Storage {

    private String fac;

    public void exportObject(LocationsUser upgradeObject) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String object = encode(upgradeObject);
                File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + upgradeObject.getFacção() + ".yml");
                YamlConfiguration yamlConfiguration = YamlConfig.loadConfiguration(bruteFile);
                yamlConfiguration.set("fac", object);
                try {
                    yamlConfiguration.save(bruteFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Main.getPlugin(Main.class));
    }

    public void deleteObject(String fac) {
        File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
        bruteFile.delete();
    }

    public void importObject(String fac) {
        new BukkitRunnable() {
            @Override
            public void run() {
                File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
                if (!bruteFile.exists()) {
                    try {
                        bruteFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                YamlConfiguration yamlConfiguration = YamlConfig.loadConfiguration(bruteFile);
                LocationsUser upgradeObject = null;
                if (yamlConfiguration.get("fac") == null || yamlConfiguration.getString("fac").isEmpty()) {
                    upgradeObject = new LocationsUser(fac);
                } else {
                    upgradeObject = decode(yamlConfiguration.getString("fac"), LocationsUser.class);
                }
                Main.cacheloc.put(upgradeObject.getFacção(), upgradeObject);
            }
        }.runTaskAsynchronously(Main.getPlugin(Main.class));
    }

    public static String encode(Object classz) {
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