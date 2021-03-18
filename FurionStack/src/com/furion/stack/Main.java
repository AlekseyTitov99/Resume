package com.furion.stack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.furion.stack.listeners.Death;
import com.furion.stack.listeners.Spawn;
import com.furion.stack.util.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main  extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getConsoleSender().sendMessage("§e[FurionStack] > Iniciando");
        Bukkit.getConsoleSender().sendMessage("§e[FurionStack] > Iniciando");
        Bukkit.getConsoleSender().sendMessage("§e[FurionStack] > Iniciando");
        Bukkit.getConsoleSender().sendMessage("§e[FurionStack] > Iniciando");
        saveDefaultConfig();
        getCommand("ativar").setExecutor(new ComandoAtivar());
        getServer().getPluginManager().registerEvents(new Death(),this);
        getServer().getPluginManager().registerEvents(new Spawn(),this);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(Main.getPlugin(Main.class), PacketType.Play.Server.TILE_ENTITY_DATA));
        /*getServer().getPluginManager().registerEvents(new StackDeath(),this);*/
    }
}