package com.dungeons.system.listeners;

import com.dungeons.system.Main;
import com.dungeons.system.SQL.AtlasStorage;
import com.dungeons.system.api.LocationEncoder;
import com.dungeons.system.dungeon.Dungeon;
import com.dungeons.system.objeto.DungeonUser;
import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.stream.Collectors;

public class GeneralJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        AtlasStorage.loadUser(e.getPlayer().getName());
        if (Main.cache.entrySet().stream().filter(s -> s.getValue().getJogadores().contains(e.getPlayer().getName())).collect(Collectors.toSet()).size() > 0) {
            Map.Entry<String, Dungeon> ss = Main.cache.entrySet().stream().filter(s -> s.getValue().getJogadores().contains(e.getPlayer().getName())).findFirst().get();
            if (e.getPlayer().isDead()) {
                e.getPlayer().spigot().respawn();
                return;
            }
            ss.getValue().joinDungeon(e.getPlayer());
            return;
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if (Main.cacheuser.containsKey(e.getPlayer().getName())) {
            DungeonUser dungeonUser = Main.cacheuser.get(e.getPlayer().getName());
            if (Main.cache.containsKey(dungeonUser.getPartyname())) {
                Dungeon dungeon = Main.cache.get(dungeonUser.getPartyname());
                Location location = LocationEncoder.getDeserializedLocation(dungeon.getLobbylocation());
                e.setRespawnLocation(location);
            }
        }
    }

    @EventHandler
    public void onQUIT(PlayerQuitEvent e) {
        if (Main.cacheuser.containsKey(e.getPlayer().getName())) {
            DungeonUser dungeonUser = Main.cacheuser.get(e.getPlayer().getName());
            if (Main.cache.containsKey(dungeonUser.getPartyname())) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Dungeon dungeon = Main.cache.get(dungeonUser.getPartyname());
                        boolean removermundo = true;
                        if (dungeon == null) return;
                        for (String jogadore : dungeon.getJogadores()) {
                            if (Bukkit.getPlayer(jogadore
                            ) == null){ continue;}
                            Bukkit.getPlayer(jogadore).sendMessage("§cO jogador §7" + e.getPlayer().getName() + "§c abandonou a dungeon.");
                            removermundo = false;
                        }
                        if (removermundo) {
                            if (Main.sala == 0){
                                AtlasStorage.saveNumber(0);
                                return;
                            }
                            Main.setSala(Main.sala - 1);
                            AtlasStorage.saveNumber(Main.getSala());
                            MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().deleteWorld(dungeon.getParty().getSala(),true);
                            MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().cloneWorld("wl",dungeon.getParty().getSala());
                            Main.cache.remove(dungeonUser.getPartyname());
                        }
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 40L);
            }
        }
        AtlasStorage.unLoadUser(e.getPlayer().getName());
    }


}