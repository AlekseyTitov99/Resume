package br.jotaentity.geradores.utils;

import org.bukkit.Bukkit;

public class ReflectionUtils {
    private static final String version;

    static {
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static Class<?> getNMSClass(final String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + ReflectionUtils.version + "." + name);
    }

    public static Class<?> getOBClass(final String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + ReflectionUtils.version + "." + name);
    }
}
