package br.jotaentity.geradores.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class LocationEncoder {

    public static String getSerializedLocation(Location loc, EntityType type) {
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch()
                + ";" + loc.getWorld().getUID() + ":" + type.name().toUpperCase();
    }

    public static String getSerializedLocation(Location loc) {
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch()
                + ";" + loc.getWorld().getUID();
    }

    public static Location getDeserializedLocation(String s) {
        if (s.equalsIgnoreCase("null")) return null;
        String[] parts = s.split(";");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        float yaw = Float.parseFloat(parts[3]);
        float pitch = Float.parseFloat(parts[4]);
        UUID u = UUID.fromString(parts[5].split(":")[0]);
        World w = Bukkit.getServer().getWorld(u);
        return new Location(w, x, y, z, yaw, pitch);
    }

    public static EntityType getDeserializedType(String s) {
        if (s.equalsIgnoreCase("null")) return null;
        String[] parts = s.split(":");
        return EntityType.valueOf(parts[1]);
    }

}