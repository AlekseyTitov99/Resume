package com.dungeons.system.socket;

import com.dungeons.system.Main;
import com.dungeons.system.SQL.AtlasStorage;
import com.dungeons.system.dungeon.Dungeon;
import com.dungeons.system.enums.Cargo;
import com.dungeons.system.objeto.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class SocketThread extends Thread {

    
    /**
    * System for one server that didnt have jedis.
    */
    
    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(29545);
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                String text = in.readLine().replaceFirst("n","~").split("~")[1];
                Bukkit.getConsoleSender().sendMessage(text);
                Party party = AtlasStorage.decode(text, Party.class);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ArrayList<String> jogadores = new ArrayList<>();
                        party.getUsers().entrySet().forEach(s -> jogadores.add(s.getKey()));
                        int numerosala = Main.sala++;
                        party.setSala("sala" + numerosala);
                        AtlasStorage.saveNumber(numerosala);
                        Dungeon dungeon = new Dungeon(party, jogadores, Bukkit.getWorld(party.getSala()));
                        dungeon.setPartyName(party.getName());
                        if (Main.cache.containsKey(dungeon.getPartyname())) {
                            Main.cache.remove(party.getName());
                        }
                        Main.cache.put(party.getName(), dungeon);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                dungeon.joinDungeon();
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        for (Map.Entry<String, Cargo> stringCargoEntry : party.getUsers().entrySet()) {
                                            if (Bukkit.getPlayer(stringCargoEntry.getKey()) == null) continue;
                                            Player j = Bukkit.getPlayer(stringCargoEntry.getKey());
                                            dungeon.joinDungeon(j);
                                        }
                                    }
                                }.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 40L);
                            }
                        }.runTaskAsynchronously(Main.getPlugin(Main.class));
                    }
                }.runTaskAsynchronously(Main.getPlugin(Main.class));
                writer.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
