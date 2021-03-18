package com.pagani.bans.objetos;

import com.pagani.bans.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Diario {

    public static void startRunnable(){
        BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                long longstart = Main.config.getLong("dia.firstlong");
                if (System.currentTimeMillis() > longstart + 24 * 60 * 60 * 1000){
                    Main.config.set("dia.firstlong",System.currentTimeMillis());
                    Main.config.set("dia.banidos",0);
                    Main.config.set("dia.mutados",0);
                }
                try {
                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int banidos = Main.config.getInt("dia.banidos");
                int mutados = Main.config.getInt("dia.mutados");
                BungeeCord.getInstance().broadcast("");
                BungeeCord.getInstance().broadcast("§6§lPUNIÇÕES DO DIA");
                TextComponent ban = new TextComponent("§8 * §fForam banidos §e" + banidos + "§f jogadores nas últimas 24 horas.");
                TextComponent mute = new TextComponent("§8 * §fForam silenciados §e" + mutados + "§f jogadores nas últimas 24 horas.");
                ban.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§7Clique para mais informações em nosso discord!").create()));
                ban.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://discord.gg/KyXw3Jy"));
                mute.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§7Clique para mais informações em nosso discord!").create()));
                mute.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,"https://discord.gg/KyXw3Jy"));
                BungeeCord.getInstance().broadcast(ban);
                BungeeCord.getInstance().broadcast(mute);
                BungeeCord.getInstance().broadcast("§fContinue contribuindo para o nosso servidor reportando e");
                BungeeCord.getInstance().broadcast("§feliminando hacks!");
            }
        },300, TimeUnit.SECONDS);
    }


}