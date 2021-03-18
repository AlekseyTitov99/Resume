package com.pagani.bans.Dc;

import com.pagani.bans.Main;
import com.pagani.bans.Utils.TimeFormatter;
import com.pagani.bans.objetos.PunishType;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WarnPunish extends ListenerAdapter implements Listener {

    @EventHandler
    public void onShit(BanEvent e){
        BungeeCord.getInstance().getScheduler().runAsync(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                Color color = new Color(26, 187, 159);
                embedBuilder.setColor(color);
                embedBuilder.setDescription("```autohotkey\nJogador punido: "  +e.getPunido() + "  \nAutor da punição: " + e.getPunish().getAuthor()
                        + " \nMotivo: " + e.getPunish().getMotivo() +  "\nDuração: " + (e.getPunish().getTipo() == PunishType.BANIMENTO ? "Permanente" : TimeFormatter.format(e.getPunish().getFinishAt() - System.currentTimeMillis() + 1000)) +
                        "\nTipo: "  + e.getPunish().getTipo().name() + "\nProva: " + (e.getPunish().getProva().replace("https://","").isEmpty() ? "Sem prova" : e.getPunish().getProva().replace("https://","")) + "```");
                Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById("668591824536862745")).getTextChannelById("676550111848824842")).sendMessage(embedBuilder.build()).complete();
            }
        });
    }

    private JDA jda;
    public Main plugin;
    public WarnPunish(Main main) {
        this.plugin = main;
        startBot();
        jda.addEventListener(this);
    }

    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("NjcyMjE1ODg4MDIzNTE5MjQy.XkMgvg.Yk6xdzg2UuZSym6ZlMxICmRP5Hs").build();
            BungeeCord.getInstance().getConsole().sendMessage("§f[ML]§e Carregou o addon de punição do bot.");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}