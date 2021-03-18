package com.pagani.dungeon.commands;

import com.pagani.dungeon.Main;
import com.pagani.dungeon.mysql.AtlasStorage;
import com.pagani.dungeon.objetos.Cargo;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.objetos.Party;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class PartyCommand implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage("§aComandos disponíveis: ");
                sender.sendMessage("");
                sender.sendMessage("§e/" + cmd.getName() + " criar <nome> §7Utilize este comando para criar um grupo.");
                sender.sendMessage("§e/" + cmd.getName() + " info  §7Utilize este comando para ver informações da party.");
                sender.sendMessage("§e/" + cmd.getName() + " convidar <jogador> §7Utilize este comando para convidar um jogador.");
                sender.sendMessage("§e/" + cmd.getName() + " entrar <nome> §7Utilize este comando para entrar em uma party.");
                sender.sendMessage("§e/" + cmd.getName() + " expulsar <jogador> §7Utilize este comando para expulsar um jogador.");
                sender.sendMessage("§e/" + cmd.getName() + " desfazer §7Utilize este comando para desfazer um grupo.");
                sender.sendMessage("§e/" + cmd.getName() + " sair §7Utilize este comando para sair de um grupo.");
                sender.sendMessage("§e/" + cmd.getName() + " transferir <jogador> §7Utilize este comando para transferir a liderança de um grupo.");
                return true;
            } else {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("info")) {
                        if (Main.cache.containsKey(player.getName())) {
                            DungeonUser dungeonUser = Main.cache.get(player.getName());
                            if (dungeonUser == null) {
                                player.sendMessage("§cVocê não tem party");
                                Main.cache.remove(player.getName());
                                Main.cache.put(player.getName(), new DungeonUser(player.getName()));
                                return true;
                            }
                            if (dungeonUser.getPartyname() == null) {
                                dungeonUser.setPartyname("");
                                dungeonUser.setPartyuuid("");
                                player.sendMessage("§cVocê não tem party");
                                return true;
                            }
                            String party = Main.cache.get(sender.getName()).getPartyname();
                            if (party == null) {
                                player.sendMessage("§cVocê não tem party");
                                return true;
                            }
                            if (party.equalsIgnoreCase("") || party.isEmpty()) {
                                player.sendMessage("§cVocê não tem party.");
                                return true;
                            }
                            if (Main.cacheparty.containsKey(party)) {
                                Party party1 = Main.cacheparty.get(party);
                                if (party1.getUsers().containsKey(player.getName())) {
                                    dungeonUser.setPartyuuid(party1.getPartyuuid());
                                    StringBuilder stringBuilder = new StringBuilder();
                                    player.sendMessage("§e" + party);
                                    stringBuilder.append("§eUsuários: ");
                                    for (Map.Entry<String, Cargo> stringCargoEntry : party1.getUsers().entrySet()) {
                                        Player ps = Bukkit.getPlayer(stringCargoEntry.getKey());
                                        if (ps == null) {
                                            stringBuilder.append("§c • " + stringCargoEntry.getKey());
                                            continue;
                                        } else {
                                            stringBuilder.append("§a • " + stringCargoEntry.getKey());
                                        }
                                    }
                                    player.sendMessage(stringBuilder.toString());
                                    return true;
                                } else {
                                    player.sendMessage("§cVocê não tem party.");
                                    dungeonUser.setPartyname("");
                                    dungeonUser.setPartyuuid("");
                                    AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                    return true;
                                }
                            } else {
                                AtlasStorage.importParty(party, false, dungeonUser);
                                if (party == null || Main.cacheparty.get(party) == null) {
                                    player.sendMessage("§cVocê não tem party.");
                                    dungeonUser.setPartyname("");
                                    dungeonUser.setPartyuuid("");
                                    AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                    return true;
                                }
                                if (Main.cacheparty.containsKey(party)) {
                                    Party party1 = Main.cacheparty.get(party);
                                    if (party1.getUsers().containsKey(player.getName())) {
                                        dungeonUser.setPartyuuid(party1.getPartyuuid());
                                        StringBuilder stringBuilder = new StringBuilder();
                                        player.sendMessage("§e" + party);
                                        stringBuilder.append("§eUsuários: ");
                                        for (Map.Entry<String, Cargo> stringCargoEntry : party1.getUsers().entrySet()) {
                                            Player ps = Bukkit.getPlayer(stringCargoEntry.getKey());
                                            if (ps == null) {
                                                stringBuilder.append("§c • " + stringCargoEntry.getKey());
                                                continue;
                                            } else {
                                                stringBuilder.append("§a • " + stringCargoEntry.getKey());
                                            }
                                        }
                                        player.sendMessage(stringBuilder.toString());
                                        return true;
                                    } else {
                                        player.sendMessage("§cVocê não tem party.");
                                        dungeonUser.setPartyname("");
                                        dungeonUser.setPartyuuid("");
                                        AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                        return true;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("sair")) {
                        if (Main.cache.containsKey(player.getName())){
                            DungeonUser dungeonUser = Main.cache.get(player.getName());
                            if (dungeonUser.hasParty()){
                                if (Main.cache.containsKey(dungeonUser.getPartyname())){
                                    Party party = Main.cacheparty.get(dungeonUser.getPartyname());
                                    if (party.getUsers().containsKey(player.getName())){
                                        if (party.getUsers().get(player.getName()).equals(Cargo.DONO)){
                                            player.sendMessage("§cPara sair de uma party você precisa desfaze-la, já que você é o dono.");
                                            return true;
                                        } else {
                                            party.getUsers().remove(player.getName());
                                            for (Map.Entry<String, Cargo> stringCargoEntry : party.getUsers().entrySet()) {
                                                if (Bukkit.getPlayer(stringCargoEntry.getKey()) == null) continue;
                                                Bukkit.getPlayer(stringCargoEntry.getKey()).sendMessage("§aO jogador §7" +player.getName() + "§a saiu de seu grupo.");
                                            }
                                            dungeonUser.setPartyuuid("");
                                            dungeonUser.setPartyname("");
                                            player.sendMessage("§aVocê saiu da party com sucesso.");
                                            return true;
                                        }
                                    } else {
                                        dungeonUser.setPartyuuid("");
                                        dungeonUser.setPartyname("");
                                        player.sendMessage("§cVocê não tem party.");
                                        return true;
                                    }
                                }
                            } else {
                                player.sendMessage("§cVocê não tem party!");
                            }
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("desfazer")) {
                        if (Main.cache.containsKey(player.getName())) {
                            DungeonUser dungeonUser = Main.cache.get(player.getName());
                            String party = dungeonUser.getPartyname();
                            if (party.equalsIgnoreCase("") || party.isEmpty() || !dungeonUser.hasParty()) {
                                player.sendMessage("§cVocê não tem party.");
                                return true;
                            }
                            if (Main.cacheparty.containsKey(party)) {
                                Party party1 = Main.cacheparty.get(party);
                                if (party1.getUsers().containsKey(player.getName())) {
                                    if (party1.getUsers().get(player.getName()) == Cargo.DONO) {
                                        player.sendMessage("§aDesfeita com sucesso.");
                                        dungeonUser.setPartyname("");
                                        dungeonUser.setPartyuuid("");
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                for (Map.Entry<String, Cargo> stringCargoEntry : party1.getUsers().entrySet()) {
                                                    if (Bukkit.getPlayer(stringCargoEntry.getKey()) == null) {
                                                        AtlasStorage.removeParty(stringCargoEntry.getKey());
                                                        continue;
                                                    } else {
                                                        if (Main.cache.containsKey(stringCargoEntry.getKey())) {
                                                            DungeonUser dungeonUser1 = Main.cache.get(stringCargoEntry.getKey());
                                                            dungeonUser1.setPartyname("");
                                                            dungeonUser1.setPartyuuid("");
                                                            Bukkit.getPlayer(stringCargoEntry.getKey()).sendMessage("§aSua party foi desfeita!");
                                                            AtlasStorage.savePlayer(stringCargoEntry.getKey(), dungeonUser1, true);
                                                        }
                                                    }
                                                }
                                            }
                                        }.runTaskAsynchronously(Main.getPlugin(Main.class));
                                        party1.getUsers().clear();
                                        AtlasStorage.deleteParty(party1.getName());
                                        Main.cacheparty.remove(party1.getName(), party1);
                                        AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                        return true;
                                    } else {
                                        player.sendMessage("§cVocê precisa ser o dono da party para desfaze-la.");
                                        return true;
                                    }
                                }
                            } else {
                                AtlasStorage.importParty(party, true, dungeonUser);
                                return true;
                            }
                        }
                        return true;
                    }
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("criar")) {
                        if (Main.cache.containsKey(player.getName())) {
                            String partyname = args[1];
                            if (partyname.equalsIgnoreCase("null")) {
                                player.sendMessage("§cVocê não pode utilizar este nome para uma party.");
                                return true;
                            }
                            DungeonUser dungeonUser = Main.cache.get(player.getName());
                            if (dungeonUser.getPartyname() == null) {
                                File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + partyname + ".yml");
                                if (bruteFile.exists()) {
                                    sender.sendMessage("§cJá existe uma party o nome §7´" + partyname + "`§c.");
                                    return true;
                                } else {
                                    try {
                                        bruteFile.createNewFile();
                                        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (Main.cacheparty.entrySet().stream().filter(sf -> sf.getValue().getUsers().containsKey(sender.getName())).collect(Collectors.toSet()).isEmpty()) {
                                        Party party = new Party(partyname);
                                        party.setName(partyname);
                                        party.getUsers().put(player.getName(), Cargo.DONO);
                                        Main.cacheparty.put(partyname, party);
                                        AtlasStorage.saveParty(party);
                                        sender.sendMessage("§aParty §7`" + partyname + "´ §acriada com sucesso.");
                                        dungeonUser.setPartyname(partyname);
                                        dungeonUser.setPartyuuid(party.getPartyuuid());
                                        AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                        return true;
                                    }
                                }
                                return true;
                            }
                            if (Main.cacheparty.containsKey(dungeonUser.getUsername())) {
                                player.sendMessage("§cVocê já tem uma party.");
                                return true;
                            } else {
                                if (dungeonUser.getPartyname().equalsIgnoreCase("") || dungeonUser.getPartyname().isEmpty()) {
                                    File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + partyname + ".yml");
                                    if (bruteFile.exists()) {
                                        sender.sendMessage("§cJá existe uma party o nome §7´" + partyname + "`§c.");
                                        return true;
                                    } else {
                                        try {
                                            bruteFile.createNewFile();
                                            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if (Main.cacheparty.entrySet().stream().filter(sf -> sf.getValue().getUsers().containsKey(sender.getName())).collect(Collectors.toSet()).isEmpty()) {
                                            Party party = new Party(partyname);
                                            party.setName(partyname);
                                            party.getUsers().put(player.getName(), Cargo.DONO);
                                            Main.cacheparty.put(partyname, party);
                                            AtlasStorage.saveParty(party);
                                            sender.sendMessage("§aParty §7`" + partyname + "´ §acriada com sucesso.");
                                            dungeonUser.setPartyname(partyname);
                                            dungeonUser.setPartyuuid(party.getPartyuuid());
                                            AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                            return true;
                                        }
                                    }
                                } else {
                                    AtlasStorage.importParty(dungeonUser.getPartyname(), false, dungeonUser);
                                    if (Main.cacheparty.containsKey(dungeonUser.getPartyname())) {
                                        if (Main.cacheparty.get(dungeonUser.getPartyname()).getUsers().containsKey(player.getName())) {
                                            player.sendMessage("§cVocê já tem uma party.");
                                            return true;
                                        } else {
                                            dungeonUser.setPartyname("");
                                            File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + partyname + ".yml");
                                            if (bruteFile.exists()) {
                                                sender.sendMessage("§cJá existe uma party o nome §7´" + partyname + "`§c.");
                                                return true;
                                            } else {
                                                try {
                                                    bruteFile.createNewFile();
                                                    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                if (Main.cacheparty.entrySet().stream().filter(sf -> sf.getValue().getUsers().containsKey(sender.getName())).collect(Collectors.toSet()).isEmpty()) {
                                                    Party party = new Party(partyname);
                                                    party.setName(partyname);
                                                    party.getUsers().put(player.getName(), Cargo.DONO);
                                                    Main.cacheparty.put(partyname, party);
                                                    AtlasStorage.saveParty(party);
                                                    sender.sendMessage("§aParty §7`" + partyname + "´ §acriada com sucesso.");
                                                    dungeonUser.setPartyname(partyname);
                                                    dungeonUser.setPartyuuid(party.getPartyuuid());
                                                    AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                                    return true;
                                                }
                                            }
                                        }
                                    } else {
                                        dungeonUser.setPartyname("");
                                        File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + partyname + ".yml");
                                        if (bruteFile.exists()) {
                                            sender.sendMessage("§cJá existe uma party o nome §7´" + partyname + "`§c.");
                                            return true;
                                        } else {
                                            try {
                                                bruteFile.createNewFile();
                                                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if (Main.cacheparty.entrySet().stream().filter(sf -> sf.getValue().getUsers().containsKey(sender.getName())).collect(Collectors.toSet()).isEmpty()) {
                                                Party party = new Party(partyname);
                                                party.getUsers().put(player.getName(), Cargo.DONO);
                                                Main.cacheparty.put(partyname, party);
                                                AtlasStorage.saveParty(party);
                                                sender.sendMessage("§aParty §7`" + partyname + "´ §acriada com sucesso.");
                                                dungeonUser.setPartyname(partyname);
                                                dungeonUser.setPartyuuid(party.getPartyuuid());
                                                AtlasStorage.savePlayer(player.getName(), dungeonUser, true);
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                            return true;
                        }
                    }
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("expulsar")) {
                            String jogador = args[1];
                            if (Main.cache.containsKey(player.getName())){
                                DungeonUser dungeonUser = Main.cache.get(player.getName());
                                if (dungeonUser.getPartyname().isEmpty()){
                                    player.sendMessage("§cVocê não tem party.");
                                    return true;
                                }
                                if (Main.cacheparty.containsKey(dungeonUser.getPartyname())){
                                    Party party = Main.cacheparty.get(dungeonUser.getPartyname());
                                    if (party.getUsers().containsKey(jogador)){
                                        if (party.getUsers().get(player.getName()).equals(Cargo.DONO)) {
                                            if (party.getUsers().get(jogador).equals(Cargo.NOVO) || party.getUsers().get(jogador).equals(Cargo.PROMOVIDO)) {
                                                if (Main.cache.containsKey(jogador)) {
                                                    DungeonUser dungeonUser1 = Main.cache.get(jogador);
                                                    dungeonUser1.setPartyname("");
                                                    dungeonUser1.setPartyuuid("");
                                                    party.getUsers().remove(jogador);
                                                    player.sendMessage("§aJogador expulso com sucesso.");
                                                    Bukkit.getPlayer(jogador).sendMessage("§cVocê foi expulso de sua party.");
                                                    for (Map.Entry<String, Cargo> stringCargoEntry : party.getUsers().entrySet()) {
                                                        if (Bukkit.getPlayer(stringCargoEntry.getKey())==null || Bukkit.getPlayer(stringCargoEntry.getKey()) == player) continue;
                                                        Bukkit.getPlayer(stringCargoEntry.getKey()).sendMessage("§aO Jogador §7" + jogador + "§a foi expulso da sua party!");
                                                    }
                                                    AtlasStorage.saveParty(party);
                                                } else {
                                                    party.getUsers().remove(jogador);
                                                    player.sendMessage("§aJogador expulso com sucesso.");
                                                    for (Map.Entry<String, Cargo> stringCargoEntry : party.getUsers().entrySet()) {
                                                        if (Bukkit.getPlayer(stringCargoEntry.getKey())==null || Bukkit.getPlayer(stringCargoEntry.getKey()) == player) continue;
                                                        Bukkit.getPlayer(stringCargoEntry.getKey()).sendMessage("§aO Jogador §7" + jogador + "§a foi expulso da sua party!");
                                                    }
                                                    AtlasStorage.removeParty(jogador);
                                                    AtlasStorage.saveParty(party);
                                                    return true;
                                                }
                                            } else {
                                                player.sendMessage("§cO jogador não pode ser expulso.");
                                                return true;
                                            }
                                        } else {
                                            player.sendMessage("§cSó o dono da party pode expulsar jogadores.");
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage("§cA party não contém este jogador, caso ache que isto seja um erro, verifique letras maíusculas.");
                                        return true;
                                    }
                                } else {
                                    dungeonUser.setPartyuuid("");
                                    dungeonUser.setPartyname("");
                                    player.sendMessage("§cVocê não tem party!");
                                    return true;
                                }
                            }
                        }
                        if (args[0].equalsIgnoreCase("convidar")) {
                            String jogador = args[1];
                            if (jogador.equalsIgnoreCase(player.getName())){
                                player.sendMessage("§cVocê não pode convidar a si mesmo.");
                                return true;
                            }
                            DungeonUser dungeonUser = Main.cache.get(player.getName());
                            if (dungeonUser.hasParty() && Main.cacheparty.containsKey(dungeonUser.getPartyname())) {
                                if (Bukkit.getPlayer(jogador) == null) {
                                    player.sendMessage("§cInfelizmente o jogador não está online");
                                    return true;
                                }
                                if (dungeonUser.getParty().getConvites().contains(Bukkit.getPlayer(jogador).getName())) {
                                    player.sendMessage("§cEste jogador já foi convidado.");
                                    return true;
                                }
                                dungeonUser.getParty().getConvites().add(Bukkit.getPlayer(jogador).getName());
                                Bukkit.getPlayer(jogador).sendMessage("§aVocê foi convidado para a party: " + dungeonUser.getPartyname());
                                player.sendMessage("§aJogador convidado com sucesso.");
                                AtlasStorage.saveParty(dungeonUser.getParty());
                            }
                        }
                        if (args[0].equalsIgnoreCase("transferir")) {
                            String jogador = args[1];
                            if (Bukkit.getPlayer(jogador) == null){player.sendMessage("§cO jogador está offline."); return true;}
                            if (Main.cache.containsKey(player.getName())){
                                DungeonUser dungeonUser = Main.cache.get(player.getName());
                                if (dungeonUser.hasParty() || !dungeonUser.getPartyname().isEmpty()){
                                    if (Main.cacheparty.containsKey(dungeonUser.getPartyname())){
                                        Party party = Main.cacheparty.get(dungeonUser.getPartyname());
                                        if (party.getUsers().containsKey(player.getName())){
                                            if (party.getUsers().get(player.getName()).equals(Cargo.DONO)){
                                                if (party.getUsers().containsKey(jogador)){
                                                    if (Main.cache.containsKey(jogador)){
                                                        party.getUsers().replace(player.getName(),Cargo.NOVO);
                                                        party.getUsers().replace(jogador,Cargo.DONO);
                                                        player.sendMessage("§aParty transferida com sucesso.");
                                                        Bukkit.getPlayer(jogador).sendMessage("§aA liderança da party foi transferida para você!");
                                                        return true;
                                                    } else {
                                                        player.sendMessage("§cOcorreu um erro, tente novamente.");
                                                        return true;
                                                    }
                                                } else {
                                                    player.sendMessage("§cA party não contém este jogador.");
                                                    return true;
                                                }
                                            } else {
                                                player.sendMessage("§cVocê não é o dono desta party");
                                                return true;
                                            }
                                        } else {
                                            player.sendMessage("§cVocê não tem party!");
                                            dungeonUser.setPartyname("");
                                            dungeonUser.setPartyuuid("");
                                            AtlasStorage.savePlayer(player.getName(),dungeonUser,true);
                                            return true;
                                        }
                                    } else {
                                        player.sendMessage("§cTente novamente mais tarde porfavor!");
                                        AtlasStorage.importParty(dungeonUser.getPartyname(),true,dungeonUser);
                                        return true;
                                    }
                                } else {
                                    player.sendMessage("§cVocê não possui party!");
                                    return true;
                                }
                            }
                            return true;
                        }
                        if (args[0].equalsIgnoreCase("entrar")) {
                            if (Main.cache.containsKey(player.getName())) {
                                DungeonUser dungeonUser = Main.cache.get(player.getName());
                                String party = args[1];
                                if (dungeonUser.hasParty()){
                                    player.sendMessage("§cVocê tem uma party!");
                                    return true;
                                }
                                if (Main.cacheparty.containsKey(party) && !dungeonUser.hasParty()) {
                                    Party party1 = Main.cacheparty.get(party);
                                    if (party1.getConvites().contains(player.getName())) {
                                        party1.getUsers().put(player.getName(), Cargo.NOVO);
                                        dungeonUser.setPartyuuid(party1.getPartyuuid());
                                        player.sendMessage("§aEntrou com sucesso.");
                                        dungeonUser.setPartyname(party1.getName());
                                        for (Map.Entry<String, Cargo> stringCargoEntry : party1.getUsers().entrySet()) {
                                            if (Bukkit.getPlayer(stringCargoEntry.getKey()) == null) continue;
                                            Bukkit.getPlayer(stringCargoEntry.getKey()).sendMessage("§aO Jogador §7" + player.getName() + "§a entrou na party!");
                                        }
                                        AtlasStorage.savePlayer(player.getName(),dungeonUser,true);
                                        AtlasStorage.saveParty(party1);
                                        return true;
                                    }
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}