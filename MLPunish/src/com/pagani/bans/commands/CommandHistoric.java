package com.pagani.bans.commands;

import com.pagani.bans.Main;
import com.pagani.bans.Utils.TimeFormatter;
import com.pagani.bans.objetos.Ban.Punish;
import com.pagani.bans.objetos.PunishType;
import com.pagani.bans.objetos.PunishUser;
import com.pagani.bans.storage.Storage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

public class CommandHistoric extends Command {
    public CommandHistoric(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            if (args.length == 1) {
                CompletableFuture.runAsync(() -> {
                    PunishUser punishUser = null;
                    if (Main.cache.containsKey(args[0])) {
                        punishUser = Main.cache.get(args[0]);
                    } else {
                        punishUser = Storage.importUser(args[0]);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime triggerTime =
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(punishUser.getActual().getFinishAt()),
                                    TimeZone.getTimeZone("America/Recife").toZoneId());
                    if (punishUser.getPunições().isEmpty()) {
                        sender.sendMessage("§cO jogador não tem punições.");
                        return;
                    }
                    if (punishUser.getPunições() == null) {
                        sender.sendMessage("§cOcorreu um erro.");
                        return;
                    }
                    for (Punish puniçõe : punishUser.getPunições()) {
                        long integer = puniçõe.getTime() - puniçõe.getFinishAt();
                        PunishType punishType = puniçõe.getTipo();
                        TextComponent textComponent = new TextComponent("§b ▸ §f" + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(puniçõe.getDate()));
                        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Tipo de banimento: §f" + punishType.name() + "\n" +
                                "§7Tempo de banimento: §f" + TimeFormatter.format(integer * 60000)
                                + "\n" + "§7Servidores: §fTodos." + "\n" + "Cargo Mínimo: " + (punishType == PunishType.BANIMENTO || punishType == PunishType.BAN_TEMP ? "§aModerador e superiores" : "§3Suporte e superiores") +
                                "\n" + "§7Termina em: " + formatter.format(triggerTime) + "§c às " + formatter2.format(triggerTime) + "\n" + "§7Autor da punição: " + puniçõe.getAuthor()
                                + "\n§7Prova: §f" + (puniçõe.getProva().isEmpty() ? "Sem provas " : puniçõe.getProva()) +
                                "\n§7Autor: §f" + puniçõe.getAuthor()).create()));
                        sender.sendMessage(textComponent);
                    }
                });
            }
            return;
        }
        if (Main.config.getSection("equipe").getKeys().contains(sender.getName())) {
            if (Integer.parseInt(Main.config.get("equipe." + sender.getName()).toString()) < 3) {
                sender.sendMessage("§cVocê precisa de permissão nível 3 para isto.");
                return;
            }
            if (args.length == 1) {
                CompletableFuture.runAsync(() -> {
                    PunishUser punishUser = null;
                    if (Main.cache.containsKey(args[0])) {
                        punishUser = Main.cache.get(args[0]);
                    } else {
                        punishUser = Storage.importUser(args[0]);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    if (punishUser.getPunições().isEmpty()) {
                        sender.sendMessage("§cO jogador não tem punições.");
                        return;
                    }
                    if (punishUser.getPunições() == null) {
                        sender.sendMessage("§cOcorreu um erro.");
                        return;
                    }
                    sender.sendMessage("§aHistórico do usuário: ");
                    sender.sendMessage("");
                    for (Punish puniçõe : punishUser.getPunições()) {
                        if (puniçõe == null) continue;
                        long integer = puniçõe.getFinishAt() - puniçõe.getTime();
                        PunishType punishType = puniçõe.getTipo();
                        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(puniçõe.getFinishAt()),TimeZone.getTimeZone("America/Recife").toZoneId());
                        TextComponent textComponent = new TextComponent("§b ◆ §f" + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(puniçõe.getDate()));
                        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Tipo de banimento: §f" + punishType.name() + "\n" +
                                "§7Tempo de banimento: §f" + TimeFormatter.format(integer)
                                + "\n" + "§7Servidores: §fTodos." + "\n" + "§7Cargo Mínimo: §f" + (punishType == PunishType.BANIMENTO || punishType == PunishType.BAN_TEMP ? "§aModerador e superiores" : "§3Suporte e superiores") +
                                "\n" + "§7Termina em: §f" + formatter.format(triggerTime) + " às " + formatter2.format(triggerTime) + "\n" + "§7Autor da punição: " + puniçõe.getAuthor()
                                + "\n§7Prova: §f" + (puniçõe.getProva().isEmpty() ? "Sem provas " : puniçõe.getProva()) +
                                "\n§7Autor: §f" + puniçõe.getAuthor() + "\n§7ID: §e#" + puniçõe.getID()).create()));
                        sender.sendMessage(textComponent);
                    }
                });
            }
        } else {
            sender.sendMessage("§cVocê precisa fazer parte da equipe.");
        }
    }
}