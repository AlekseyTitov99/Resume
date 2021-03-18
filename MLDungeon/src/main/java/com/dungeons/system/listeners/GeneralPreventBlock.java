package com.dungeons.system.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class GeneralPreventBlock implements Listener {

    @EventHandler
    public void a(EntityChangeBlockEvent eat) {
        if (eat.getEntity().getType() == EntityType.WITHER_SKULL) {
            eat.setCancelled(true);
        }
        if (eat.getEntity().getType() == EntityType.WITHER) {
            eat.setCancelled(true);
        }
        if (eat.getEntity().getType().equals(EntityType.ENDER_DRAGON)) {
            eat.setCancelled(true);
        }
    }

    @EventHandler
    public void a(EntityExplodeEvent eat) {
        eat.setCancelled(true);
    }


}