package com.dungeons.system.commands;

import com.dungeons.system.Main;
import com.dungeons.system.dungeon.Dungeon;
import com.dungeons.system.objeto.DungeonUser;
import com.dungeons.system.util.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandReady implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (Main.cacheuser.containsKey(sender.getName())) {
                DungeonUser dungeonUser = Main.cacheuser.get(sender.getName());
                if (Main.cache.containsKey(dungeonUser.getPartyname())) {
                    Dungeon dungeon = Main.cache.get(dungeonUser.getPartyname());
                    if (dungeon.getReady().contains(sender.getName())) {
                        p.sendMessage("§cVocê já está pronto para combate.");
                        return true;
                    } else {
                        dungeon.getReady().add(sender.getName());
                        p.sendMessage("§aVocê está pronto para o combate!");
                        TitleAPI.sendTitle(p,20,100000,20,"§a§lVocê está pronto!", "§7Aguarde pelo resto da equipe");
                        boolean no = true;
                        for (String jogadore : dungeon.getJogadores()) {
                            if (Bukkit.getPlayer(jogadore) == null) continue;
                            if (!dungeon.getReady().contains(jogadore)) no = false;
                        }
                        if (!no){
                            return true;
                        }
                        if (no){
                            for (String jogadore : dungeon.getJogadores()) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "§aGrupo pronto","§7Iniciando fase.");
                            }
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String jogadore : dungeon.getJogadores()) {
                                        if (Bukkit.getPlayer(jogadore) == null) continue;
                                        Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "","§65");
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(Main.class),20L);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String jogadore : dungeon.getJogadores()) {
                                        if (Bukkit.getPlayer(jogadore) == null) continue;
                                        Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "","§64");
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(Main.class),20L*2);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String jogadore : dungeon.getJogadores()) {
                                        if (Bukkit.getPlayer(jogadore) == null) continue;
                                        Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "","§63");
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(Main.class),20L*3);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String jogadore : dungeon.getJogadores()) {
                                        if (Bukkit.getPlayer(jogadore) == null) continue;
                                        Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "","§62");
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(Main.class),20L*4);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String jogadore : dungeon.getJogadores()) {
                                        if (Bukkit.getPlayer(jogadore) == null) continue;
                                        Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "","§61");
                                    }
                                }
                            }.runTaskLater(Main.getPlugin(Main.class),20L*5);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (String jogadore : dungeon.getJogadores()) {
                                        if (Bukkit.getPlayer(jogadore) == null) continue;
                                        Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                        TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "§aFase Iniciada","§7Elimine todos os mobs!");
                                    }
                                    dungeon.startDungeon();
                                }
                            }.runTaskLater(Main.getPlugin(Main.class),20L*6);
                        }
                        return true;
                    }
                } else {

                }
            } else {
                return true;
            }
        }
        return false;
    }
}