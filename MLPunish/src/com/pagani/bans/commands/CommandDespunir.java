package com.pagani.bans.commands;

import com.pagani.bans.Main;
import com.pagani.bans.objetos.PunishUser;
import com.pagani.bans.storage.Storage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.CompletableFuture;

public class CommandDespunir extends Command {

    public CommandDespunir(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // /despunir [nome]
        if (!(sender instanceof ProxiedPlayer)){
            if (args.length == 1 || args.length == 2) {
                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        PunishUser punishUser = null;
                        if (Main.cache.containsKey(args[0])) {
                            punishUser = Main.cache.get(args[0]);
                        } else {
                            punishUser = Storage.importUser(args[0]);
                        }
                        if (args.length == 1) {
                            BungeeCord.getInstance().broadcast("");
                            BungeeCord.getInstance().broadcast("§c O Jogador " + args[0] + " foi despunido da punição §e#" + punishUser.getActual().getID() + "§c.");
                            BungeeCord.getInstance().broadcast("");
                            sender.sendMessage("§aUsuário despunido com sucesso.");
                            punishUser.setActual(null);
                            Storage.exportObject(punishUser);
                            return;
                        }
                        if (args.length == 2){
                            try {
                                int id = Integer.parseInt(args[1]);
                                punishUser.getPunições().removeIf(s -> s.getID() == id);
                                if (punishUser.getActual() != null && punishUser.getActual().getID() == id){
                                    punishUser.setActual(null);
                                }
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c O Jogador " + args[0] + " foi despunido da punição §e#" + args[1] + "§c.");
                                BungeeCord.getInstance().broadcast("");
                                sender.sendMessage("§aUsuário despunido com sucesso.");
                                Storage.exportObject(punishUser);
                            } catch (NumberFormatException s){
                                sender.sendMessage("§cInsira um número válido.");
                            }
                            return;
                        }
                        return;
                    }
                });
            }
            return;
        }
        if (args.length == 1 || args.length == 2) {
            if (Main.config.getSection("equipe").getKeys().contains(sender.getName())) {
                if (Integer.parseInt(Main.config.get("equipe." + sender.getName()).toString()) <= 2) {
                    sender.sendMessage("§cApenas usuários com permissão nível 3 de punição podem despunir jogadores.");
                    return;
                }
                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        PunishUser punishUser = null;
                        if (Main.cache.containsKey(args[0])) {
                            punishUser = Main.cache.get(args[0]);
                        } else {
                            punishUser = Storage.importUser(args[0]);
                        }
                        assert punishUser != null;
                        if (args.length == 2){
                            try {
                                int id = Integer.parseInt(args[1]);
                                punishUser.getPunições().removeIf(s -> s.getID() == id);
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c O Jogador " + args[0] + " foi despunido da punição §e#" + args[1] + "§c.");
                                BungeeCord.getInstance().broadcast("");
                                sender.sendMessage("§aUsuário despunido com sucesso.");
                                Storage.exportObject(punishUser);
                            } catch (NumberFormatException s){
                                sender.sendMessage("§cInsira um número válido.");
                            }
                        }
                        if (args.length == 1) {
                            BungeeCord.getInstance().broadcast("");
                            BungeeCord.getInstance().broadcast("§c O Jogador " + args[0] + " foi despunido da punição §e#" + punishUser.getActual().getID() + "§c.");
                            BungeeCord.getInstance().broadcast("");
                            sender.sendMessage("§aUsuário despunido com sucesso.");
                            punishUser.setActual(null);
                            Storage.exportObject(punishUser);
                            return;
                        }
                        return;
                    }
                });
            } else {
                sender.sendMessage("§cVocê precisa ser da equipe para despunir um jogador.");
            }
        }
    }


}