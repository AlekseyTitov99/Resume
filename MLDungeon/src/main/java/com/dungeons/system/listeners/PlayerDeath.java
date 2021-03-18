package com.dungeons.system.listeners;

import com.dungeons.system.Main;
import com.dungeons.system.SQL.AtlasStorage;
import com.dungeons.system.api.LocationEncoder;
import com.dungeons.system.dungeon.Dungeon;
import com.dungeons.system.util.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        if (Main.cacheuser.containsKey(player.getName())){
            if (Main.cache.containsKey(Main.cacheuser.get(player.getName()).getPartyname())) {
                Dungeon dungeon = Main.cache.get(Main.cacheuser.get(player.getName()).getPartyname());
                e.getDrops().clear();
                e.setDeathMessage("§c" + player.getName() + "morreu em batalha contra mobs.");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.spigot().respawn();
                    }
                }.runTaskLater(Main.getPlugin(Main.class),2L);
                Location location = LocationEncoder.getDeserializedLocation(dungeon.getCurrentfase().getGetbacklocation());
                player.teleport(location);
                dungeon.getDead().add(player.getName());
                player.setGameMode(GameMode.ADVENTURE);
                player.setAllowFlight(true);
                player.setFlying(true);
                if (dungeon.getDead().containsAll(dungeon.getJogadores())) {
                    if (Main.sala == 0){
                        AtlasStorage.saveNumber(0);
                    } else {
                        Main.setSala(Main.sala - 1);
                        AtlasStorage.saveNumber(Main.getSala());
                    }
                    for (String jogadore : dungeon.getJogadores()) {
                        if (Main.cacheuser.containsKey(jogadore)) {
                            AtlasStorage.savePlayer(jogadore, Main.cacheuser.get(jogadore), true);
                        }
                        if (Bukkit.getPlayer(jogadore) == null) {
                            continue;
                        }
                        Bukkit.getPlayer(jogadore).setGameMode(GameMode.ADVENTURE);
                        Bukkit.getPlayer(jogadore).setAllowFlight(true);
                        Bukkit.getPlayer(jogadore).setFlying(true);
                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 40, 20, "§cSua equipe perdeu!", "§7Enviando para o factions...");
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(b);
                        try {
                            out.writeUTF("Connect");
                            out.writeUTF("Factions");
                        } catch (Exception ef) {

                        }
                        Bukkit.getPlayer(jogadore).sendPluginMessage(Main.getPlugin(Main.class), "BungeeCord", b.toByteArray());
                    }
                    /**
                     * acabou o jogo
                     */
                    return;
                }
                TitleAPI.sendTitle(player, 20, 40, 20, "§cVocê morreu", "");
                ItemStack[][] store = new ItemStack[2][1];
                for (String jogadore : dungeon.getJogadores()) {
                    if(Bukkit.getPlayer(jogadore) == null) continue;
                    if (!jogadore.equalsIgnoreCase(player.getName())){
                        Bukkit.getPlayer(jogadore).hidePlayer(player);
                    }
                }
                store[0] = player.getInventory().getContents();
                store[1] = player.getInventory().getArmorContents();
                if (dungeon.getItemsafterdead().containsKey(player.getName())) {
                    dungeon.getItemsafterdead().replace(player.getName(), store);
                } else {
                    dungeon.getItemsafterdead().put(player.getName(), store);
                }
            }
        }
    }
}