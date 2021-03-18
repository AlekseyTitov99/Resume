package com.dungeons.system.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;

public class GeneralBugsCorrect implements Listener {

    @EventHandler
    public void damage(EntityDamageByEntityEvent e){
        if (e.getEntityType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER){
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void damage2(EntityDamageByEntityEvent e){
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            if (e.getDamager() instanceof Projectile){
                Projectile projectile = (Projectile) e.getDamager();
                if (projectile.getShooter() instanceof Player){
                    if (e.getEntityType() == EntityType.PLAYER){
                        e.setCancelled(true);
                    }
                }
                projectile.getShooter();
            }
        }
    }

    @EventHandler
    public void onEntity(EntityCombustByEntityEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void Entity2(EntityCombustEvent ef){
        ef.setCancelled(true);
    }

    @EventHandler
    public void onKill(EntityCombustByBlockEvent ef){
        ef.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getEntityType() == EntityType.PLAYER){
            e.setDamage(e.getFinalDamage() * 12);
            Player player = (Player) e.getEntity();
            if (player.getInventory().getBoots() == null){

            } else {
                player.getInventory().getBoots().setDurability((short) (player.getInventory().getBoots().getDurability()+4));
                player.updateInventory();
                if (player.getInventory().getBoots().getDurability() >= player.getInventory().getBoots().getType().getMaxDurability()) {
                    player.getInventory().setBoots(null);
                }
            }
            if (player.getInventory().getHelmet() == null){

            } else {
                player.getInventory().getHelmet().setDurability((short) (player.getInventory().getHelmet().getDurability()+4));
                player.updateInventory();
                if (player.getInventory().getHelmet().getDurability() >= player.getInventory().getHelmet().getType().getMaxDurability()) {
                    player.getInventory().setHelmet(null);
                }
            }
            if (player.getInventory().getChestplate() == null){

            } else {
                player.getInventory().getChestplate().setDurability((short) (player.getInventory().getChestplate().getDurability()+4));
                player.updateInventory();
                if (player.getInventory().getChestplate().getDurability() >= player.getInventory().getChestplate().getType().getMaxDurability()) {
                    player.getInventory().setChestplate(null);
                }
            }
            if (player.getInventory().getLeggings() == null){

            } else {
                player.getInventory().getLeggings().setDurability((short) (player.getInventory().getLeggings().getDurability()+4));
                player.updateInventory();
                if (player.getInventory().getLeggings().getDurability() >= player.getInventory().getLeggings().getType().getMaxDurability()) {
                    player.getInventory().setLeggings(null);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent e){
        e.setCancelled(true);
    }
}