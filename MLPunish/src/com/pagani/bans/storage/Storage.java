package com.pagani.bans.storage;

import com.google.gson.Gson;
import com.pagani.bans.Main;
import com.pagani.bans.objetos.PunishUser;
import net.md_5.bungee.BungeeCord;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Storage {

    public static void exportObject(PunishUser upgradeObject) {
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), () -> {
            String object = encode(upgradeObject);
            File bruteFile = new File(Main.getInstance().getDataFolder(), "/storage/" + upgradeObject.getPlayername().toLowerCase() + ".JSON");
            try {
                DiskUtil.write(bruteFile,object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void delete(String fac){
        File bruteFile = new File(Main.getInstance().getDataFolder(), "/storage/" + fac.toLowerCase() + ".JSON");
        bruteFile.delete();
    }


    public static PunishUser importUser(String fac) {
        File bruteFile = new File(Main.getInstance().getDataFolder(), "/storage/" + fac.toLowerCase() + ".JSON");
        if (!bruteFile.exists()) {
            try {
                bruteFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String reader = DiskUtil.read(bruteFile);
            PunishUser upgradeObject = null;
            if (reader == null || reader.isEmpty() || reader.equalsIgnoreCase("")) {
                upgradeObject = new PunishUser(fac);
                upgradeObject.setPlayername(fac);
                exportObject(upgradeObject);
            } else {
                upgradeObject = decode(reader, PunishUser.class);
            }
            if (upgradeObject.getPunições() == null) upgradeObject.setPunições(new LinkedList<>());
            Main.cache.put(upgradeObject.getPlayername(), upgradeObject);
            return upgradeObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
