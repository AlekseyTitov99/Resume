package com.pagani.bans.commands;

import com.pagani.bans.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CommandStaff extends Command {

    public CommandStaff() {
        super("staff");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer) || sender.getName().equalsIgnoreCase("yJpAvontsz") || sender.getName().equalsIgnoreCase("Showkzera") || sender.getName().equalsIgnoreCase("Pagodines")
        || sender.getName().equalsIgnoreCase("DeathWiggleVI")){
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")){
                    for (String equipe : Main.config.getSection("equipe").getKeys()) {
                        sender.sendMessage("§e" + equipe + "§a Nível de permissão: " + Main.config.get("equipe." + equipe));
                    }
                }
                return;
            }
            if (args.length == 3){
                if (args[0].equalsIgnoreCase("adicionar")) {
                    try {
                        Integer integer = Integer.valueOf(args[2]);
                        Main.config.set("equipe." + args[1], args[2]);
                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                        ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                        try {
                            cfgProvider.save(Main.config,file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage("§aJogador adicionado na lista de staff.");
                    } catch (NumberFormatException ef){
                        sender.sendMessage("§cInsira um número valido.");
                    }
                    return;
                } else {
                    if (args[0].equalsIgnoreCase("remover")) {
                        Integer integer = Integer.valueOf(args[2]);
                        Main.config.set("equipe." + args[1],null);
                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                        ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                        try {
                            cfgProvider.save(Main.config,file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage("§cJogador removido com sucesso.");
                        return;
                    }
                }
            }
        } else {
            sender.sendMessage("§cVocê não tem permissão para isto.");
        }
    }

}