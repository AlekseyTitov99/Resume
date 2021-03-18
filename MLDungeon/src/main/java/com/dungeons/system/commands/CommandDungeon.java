package com.dungeons.system.commands;

import com.dungeons.system.Main;
import com.dungeons.system.api.LocationEncoder;
import com.dungeons.system.objeto.Gate;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CommandDungeon implements CommandExecutor {

    public static HashMap<String,String> cache = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command command, String lbl, String[] args) {
        // /dungeon setup padrao <lobby>
        // /dungeon setup padrao <fase> // setup localizações
        // /dungeon setup padrao <fase> <gate>
        // /dungeon setup padrao <fase> players
        // /dungeon setup padrao <fase> chest EAST
        if (args.length == 0){
            if (s.hasPermission("wfmoaawfaf")){
                s.sendMessage("§a/dungeon setup padrao lobby");
                s.sendMessage("§a/dungeon setup padrao <fase> localizações");
                s.sendMessage("§a/dungeon setup padrao <fase> gate");
                s.sendMessage("§a/dungeon setup padrao chest east");
                s.sendMessage("§a/dungeon setup padrao <fase> players");
                return true;
            }
        }
        if (s.hasPermission("wfmoaawfaf")) {
            if (args.length == 4) {
                if (args[0].equalsIgnoreCase("setup")) {
                    if (args[1].equalsIgnoreCase("padrao")) {
                        if (args[2].contains("Fase_")) {
                            if (args[3].equalsIgnoreCase("gate")) {
                                Integer numero = Integer.valueOf(args[2].split("_")[1]);
                                Player player = (Player) s;
                                List<String> strings = Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + numero + ".Gate");
                                strings.clear();
                                Selection selection = WorldEditPlugin.getPlugin(WorldEditPlugin.class).getSelection(player);
                                Location a = selection.getMaximumPoint();
                                Location b = selection.getMinimumPoint();
                                if(a == null) {
                                    player.sendMessage("§cPor favor selecione uma área com o machado do world edit para fazer o setup do gate.");
                                    return true;
                                }else if(b == null) {
                                    player.sendMessage("§cPor favor selecione uma área com o machado do world edit para fazer o setup do gate.");
                                    return true;
                                }
                                strings.add(LocationEncoder.getSerializedLocation(a));
                                strings.add(LocationEncoder.getSerializedLocation(b));
                                Main.getPlugin(Main.class).getConfig().set("Dungeons.padrao.Fases.Fase_" + numero + ".Gate", strings);
                                Main.getPlugin(Main.class).saveConfig();
                                Main.getPlugin(Main.class).reloadConfig();
                                player.sendMessage("§aLocalização do portão salva com sucesso.");
                                return true;
                            }

                            if (args[3].equalsIgnoreCase("chest")) {
                                Player player = (Player) s;
                                Integer numero = Integer.valueOf(args[2].split("_")[1]);
                                cache.put(player.getName(),"Dungeons.padrao.Fases.Fase_" + numero + ".ChestLocation");
                                player.sendMessage("§aQuebre um bloco onde será a localização do baú.");
                                return true;
                            }
                            if (args[3].equalsIgnoreCase("boss")) {
                                Player player = (Player) s;
                                Integer numero = Integer.valueOf(args[2].split("_")[1]);
                                Main.getPlugin(Main.class).getConfig().set("Dungeons.padrao.Fases.Fase_" + numero + ".BossLocation", LocationEncoder.getSerializedLocation(player.getLocation()));
                                Main.getPlugin(Main.class).saveConfig();
                                Main.getPlugin(Main.class).reloadConfig();
                                player.sendMessage("§aSpawn do boss salvo concluído.");
                                return true;
                            }
                            if (args[3].equalsIgnoreCase("horse")) {
                                Player player = (Player) s;
                                Integer numero = Integer.valueOf(args[2].split("_")[1]);
                                Main.getPlugin(Main.class).getConfig().set("Dungeons.padrao.Fases.Fase_" + numero + ".HorseLocation", LocationEncoder.getSerializedLocation(player.getLocation()));
                                Main.getPlugin(Main.class).saveConfig();
                                Main.getPlugin(Main.class).reloadConfig();
                                player.sendMessage("§aSpawn do vendedor salvo concluído.");
                                return true;
                            }
                            if (args[3].equalsIgnoreCase("players")) {
                                Player player = (Player) s;
                                Integer numero = Integer.valueOf(args[2].split("_")[1]);
                                Main.getPlugin(Main.class).getConfig().set("Dungeons.padrao.Fases.Fase_" + numero + ".Location", LocationEncoder.getSerializedLocation(player.getLocation()));
                                Main.getPlugin(Main.class).saveConfig();
                                Main.getPlugin(Main.class).reloadConfig();
                                player.sendMessage("§aSpawn do respawn salva concluído.");
                                return true;
                            }
                        }
                    }
                }
                return true;
            }
            if (args.length == 5) {
                if (args[0].equalsIgnoreCase("setup")) {
                    if (args[1].equalsIgnoreCase("padrao")) {
                        if (args[2].contains("Fase_")) {
                            if (args[3].equalsIgnoreCase("gate")) {
                                Integer numero = Integer.valueOf(args[2].split("_")[1]);
                                Player player = (Player) s;
                                List<String> strings = Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + numero + ".Gate");
                                Location a = null;
                                Location b = null;
                                for (String string : strings) {
                                    if (a == null){ a = LocationEncoder.getDeserializedLocation(string);continue;}
                                    if (b == null){ b = LocationEncoder.getDeserializedLocation(string);continue;}
                                }
                                assert a != null;
                                assert b != null;
                                Gate gate = new Gate(LocationEncoder.getSerializedLocation(a),LocationEncoder.getSerializedLocation(b));
                                gate.openGate();
                                player.sendMessage("§aTeste de portão sendo executado.");
                                return true;
                            }
                        }
                    }
                }
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("setup")) {
                    if (args[1].equalsIgnoreCase("padrao")) {
                        if (args[2].equalsIgnoreCase("lobby")) {
                            Player player = (Player) s;
                            Main.getPlugin(Main.class).getConfig().set("Dungeons.padrao.LobbyLocation", LocationEncoder.getSerializedLocation(player.getLocation()));
                            Main.getPlugin(Main.class).saveConfig();
                            Main.getPlugin(Main.class).reloadConfig();

                            player.sendMessage("§aLobby setado com sucesso.");
                            return true;
                        }
                        if (args[2].contains("Fase_")) {
                            Integer numero = Integer.valueOf(args[2].split("_")[1]);
                            Player player = (Player) s;
                            List<String> strings = Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + numero + ".Locations");
                            strings.add(LocationEncoder.getSerializedLocation(player.getLocation()));
                            Main.getPlugin(Main.class).getConfig().set("Dungeons.padrao.Fases.Fase_" + numero + ".Locations",strings);
                            Main.getPlugin(Main.class).saveConfig();
                            Main.getPlugin(Main.class).reloadConfig();
                            player.sendMessage("§aLocalização §e#" + strings.size() + "§a Salva c sucesso.");
                            return true;
                        }
                    }
                }
            }
            if (args.length == 4){
            }
        }
        return false;
    }
}