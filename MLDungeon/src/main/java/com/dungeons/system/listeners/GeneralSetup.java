package com.dungeons.system.listeners;

import com.dungeons.system.Main;
import com.dungeons.system.api.LocationEncoder;
import com.dungeons.system.commands.CommandDungeon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GeneralSetup implements Listener {


    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (CommandDungeon.cache.containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
            Main.getPlugin(Main.class).getConfig().set(CommandDungeon.cache.get(e.getPlayer().getName()), LocationEncoder.getSerializedLocation(e.getBlock().getLocation()));
            Main.getPlugin(Main.class).saveConfig();
            Main.getPlugin(Main.class).reloadConfig();
            CommandDungeon.cache.remove(e.getPlayer().getName());
            e.getPlayer().sendMessage("§aLocalização do chest salva com sucesso.");
        }
    }

}