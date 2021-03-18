package com.pagani.bans.listeners;

import com.pagani.bans.Main;
import com.pagani.bans.Utils.TimeFormatter;
import com.pagani.bans.objetos.PunishType;
import com.pagani.bans.objetos.PunishUser;
import com.pagani.bans.storage.Storage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

public class General implements Listener {

    @EventHandler
    public void onChat(ChatEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args[0].equalsIgnoreCase("/.")) return;
        if (!e.isProxyCommand() && !e.isCommand()) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) e.getSender();
            if (Main.cache.containsKey(proxiedPlayer.getName())) {
                PunishUser punishUser = Main.cache.get(proxiedPlayer.getName());
                if (punishUser == null) {
                    PunishUser punishUser1 = new PunishUser(proxiedPlayer.getName());
                    Main.cache.replace(proxiedPlayer.getName(), punishUser1);
                    punishUser = punishUser1;
                }
                if (punishUser.getActual() != null && punishUser.getActual().getTipo() == PunishType.SILENCIAMENTO) {
                    if (punishUser.getActual().getFinishAt() <= System.currentTimeMillis()) {
                        punishUser.setActual(null);
                        return;
                    }
                    e.setCancelled(true);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime triggerTime =
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(punishUser.getActual().getFinishAt()),
                                    TimeZone.getTimeZone("America/Recife").toZoneId());
                    proxiedPlayer.sendMessage("§c * Você está silenciado no chat até o dia " + formatter.format(triggerTime) + "§c às " + formatter2.format(triggerTime));
                    proxiedPlayer.sendMessage("");
                    proxiedPlayer.sendMessage("§c * Motivo: " + punishUser.getActual().getMotivo() + " §c" + punishUser.getActual().getProva());
                    proxiedPlayer.sendMessage("§c * Autor: " + punishUser.getActual().getAuthor());
                    proxiedPlayer.sendMessage("§c * Use o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em");
                    proxiedPlayer.sendMessage("§c * §e§ndiscord.gg/WRdukmG");
                    proxiedPlayer.sendMessage("");
                }
            }
            return;
        }
        if (args[0].equalsIgnoreCase("/g") || args[0].equalsIgnoreCase("/l") || args[0].equalsIgnoreCase("/tell") || args[0].equalsIgnoreCase("/local") || args[0].equalsIgnoreCase("/global")) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) e.getSender();
            if (e.isCommand() || e.isProxyCommand() && Main.cache.containsKey(proxiedPlayer.getName())) {
                if (!args[0].equalsIgnoreCase("/g") && !args[0].equalsIgnoreCase("/l") && !args[0].equalsIgnoreCase("/tell") && !args[0].equalsIgnoreCase("/local") && !args[0].equalsIgnoreCase("/global"))
                    return;
                PunishUser punishUser = Main.cache.get(proxiedPlayer.getName());
                if (punishUser == null) {
                    PunishUser punishUser1 = new PunishUser(proxiedPlayer.getName());
                    Main.cache.replace(proxiedPlayer.getName(), punishUser1);
                    punishUser = punishUser1;
                }
                if (punishUser.getActual() != null && punishUser.getActual().getTipo() == PunishType.SILENCIAMENTO) {
                    if (punishUser.getActual().getFinishAt() <= System.currentTimeMillis()) {
                        punishUser.setActual(null);
                        return;
                    }
                    e.setCancelled(true);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime triggerTime =
                            LocalDateTime.ofInstant(Instant.ofEpochMilli(punishUser.getActual().getFinishAt()),
                                    TimeZone.getTimeZone("America/Recife").toZoneId());
                    proxiedPlayer.sendMessage("§c * Você está silenciado no chat até o dia " + formatter.format(triggerTime) + "§c às " + formatter2.format(triggerTime));
                    proxiedPlayer.sendMessage("");
                    proxiedPlayer.sendMessage("§c * Motivo: " + punishUser.getActual().getMotivo() + " §c" + punishUser.getActual().getProva());
                    proxiedPlayer.sendMessage("§c * Autor: " + punishUser.getActual().getAuthor());
                    proxiedPlayer.sendMessage("§c * Use o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em");
                    proxiedPlayer.sendMessage("§c * §e§ndiscord.gg/WRdukmG");
                    proxiedPlayer.sendMessage("");
                }
            }
            return;
        }
    }

    /**
     * Async event
     * @param e
     */
    @EventHandler
    public void onJoin2(PreLoginEvent e) {
        PunishUser punishUser = Storage.importUser(e.getConnection().getName());
        if (punishUser == null){
            return;
        }
        e.registerIntent(Main.getInstance());
        BungeeCord.getInstance().getConsole().sendMessage("§6[MLPunish]: §7 O Jogador " + e.getConnection().getName() + "§7 está sendo carregado.");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        assert punishUser != null;
        if (punishUser.getActual() == null) {
            e.completeIntent(Main.getInstance());
            return;
        }
        if (punishUser.getActual().getTipo().equals(PunishType.BAN_TEMP)) {
            if (punishUser.getActual().getFinishAt() - System.currentTimeMillis() <= 0) {
                punishUser.setActual(null);
                e.completeIntent(Main.getInstance());
                return;
            }
            e.setCancelled(true);
            e.setCancelReason("§8 < §c§lMineLandia §8>\n\n§cVocê está temporariamente banido\n" +
                    "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                    "\n§cAutor: " + punishUser.getActual().getAuthor()
                    + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                    "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                    "\n§cExpira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()) +
                    "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
            e.completeIntent(Main.getInstance());
            return;
        }
        if (punishUser.getActual().getTipo().equals(PunishType.BANIMENTO)) {
            e.setCancelled(true);
            e.setCancelReason("§8 < §c§lMineLandia §8>\n\n§cVocê está banido permanentemente.\n" +
                    "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                    "\n§cAutor: " + punishUser.getActual().getAuthor()
                    + "\n§cProva: " + punishUser.getActual().getProva() +
                    "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                    "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
            e.completeIntent(Main.getInstance());
            return;
        }
        e.completeIntent(Main.getInstance());
    }
}