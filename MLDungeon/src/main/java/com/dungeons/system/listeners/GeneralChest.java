package com.dungeons.system.listeners;

import com.dungeons.system.Main;
import com.dungeons.system.api.ItemBuilder;
import com.dungeons.system.api.ItemEncoder;
import com.dungeons.system.dungeon.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class GeneralChest implements Listener {

    @EventHandler
    public void onCLick(InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().startsWith("Coletados")) {
            e.setCancelled(true);
            if (e.getRawSlot() == 49) {
                e.getWhoClicked().closeInventory();
                return;
            }
        }
    }

    public void abrir(Player pla) {
        if (Main.cacheuser.containsKey(pla.getName())) {
            if (Main.cache.containsKey(Main.cacheuser.get(pla.getName()).getPartyname())) {
                Dungeon dungeon = Main.cache.get(Main.cacheuser.get(pla.getName()).getPartyname());
                if (dungeon.getCurrentfase().getColetados().containsKey(pla.getName())) {
                    Inventory inventory = Bukkit.createInventory(null, 54, "Coletados - Fase " + (dungeon.getFase()-1));
                    inventory.setItem(49, new ItemBuilder(Material.ARROW).setName("§cFechar").toItemStack());
                    int slot = 10;
                    for (String s : dungeon.getCurrentfase().getColetados().get(pla.getName())) {
                        try {
                            inventory.setItem(slot, ItemEncoder.fromBase64(s));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
                    }
                    pla.openInventory(inventory);
                    return;
                } else {
                    Inventory inventory = Bukkit.createInventory(null, 54, "Coletados - Fase " + (dungeon.getFase()-1));
                    inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cNenhum item coletado").toItemStack());
                    inventory.setItem(49, new ItemBuilder(Material.ARROW).setName("§cFechar").toItemStack());
                    pla.openInventory(inventory);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CHEST && e.getClickedBlock().hasMetadata("Rewards")) {
            e.setCancelled(true);
            abrir(e.getPlayer());
        }
    }

}