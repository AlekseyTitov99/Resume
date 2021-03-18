package com.pagani.dungeon.listeners;

import com.pagani.dungeon.Main;
import com.pagani.dungeon.commands.DungeonCommand;
import com.pagani.dungeon.mysql.AtlasStorage;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.util.Hidder;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneralSQL implements Listener {

    @EventHandler
    public void onLoad(PlayerJoinEvent e){
        AtlasStorage.loadUser(e.getPlayer().getName());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (CitizensAPI.getNPCRegistry().getById(3306) != null) {
                    if (CitizensAPI.getNPCRegistry().getById(3306).isSpawned()) {
                        Entity entity = CitizensAPI.getNPCRegistry().getById(3306).getEntity();
                        Hidder.hideNPCNameTag(e.getPlayer(),entity);
                    }
                }
            }
        }.runTaskLater(Main.getPlugin(Main.class),2L);
    }

    @EventHandler
    public void onCLick(NPCRightClickEvent e){
        Player player = e.getClicker();
        if (e.getNPC().getId() == 3306){
            if (Main.cache.containsKey(player.getName())) {
                DungeonUser dungeonUser = Main.cache.get(player.getName());
                dungeonUser.setUsername(player.getName());
                Inventory inventory = Bukkit.createInventory(null, 27, "Dungeon");
                inventory.setItem(12, DungeonCommand.getitemdungeon(dungeonUser));
                player.openInventory(inventory);
            }
        }
    }
    @EventHandler
    public void onUnload(PlayerQuitEvent e){
        AtlasStorage.unLoadUser(e.getPlayer().getName());
    }

}