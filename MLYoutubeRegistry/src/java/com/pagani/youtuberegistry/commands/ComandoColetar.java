package com.pagani.youtuberegistry.commands;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.com.google.common.base.Throwables;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.pagani.youtuberegistry.Main;
import com.pagani.youtuberegistry.api.Storage;
import com.pagani.youtuberegistry.api.TimeFormatter;
import com.pagani.youtuberegistry.api.TitleAPI;
import com.pagani.youtuberegistry.objeto.YoutubeUser;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ComandoColetar implements CommandExecutor {

    public Channel getChannelForUser(String userId) {
        try {
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                    request -> {
                    }).setApplicationName("video-test").build();
            YouTube.Channels.List channels = youtube.channels().list("snippet,statistics,contentDetails");
            channels.setKey("AIzaSyClQMkdascGe1KouOXV0QEEUgwnvLK6q8Q");
            channels.setId(userId);
            ChannelListResponse channelListResponse = channels.execute();

            List<Channel> items = channelListResponse.getItems();
            if (items.isEmpty()){
                return null;
            } else {
                return items.get(0);
            }
        }
        catch( Exception ex ) {
            throw Throwables.propagate(ex);
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("ml.youtuber")) {
                Player player = (Player) commandSender;
                // yt cadastrar <channel id> <player name>
                if (args.length != 3 && args.length != 2){
                    if (player.hasPermission("ml.ytcadastro")){
                        player.sendMessage("§a/yt cadastrar <id do canal> <nick do jogador>§8-§7 Utilize para cadastrar um jogador");
                        player.sendMessage("§a/yt verificar <nick do jogador>§8-§7 Utilize para verificar urls de um jogador");
                        player.sendMessage("§a/yt liberarcooldown <nick do jogador> §8-§7 Utilize para liberar jogador de cooldowns!");
                        player.sendMessage("§a/yt resetarurls <nick do jogador> §8-§7 Utilize para resetar urls de um jogador!");
                    }
                    player.sendMessage("§a/yt video <url> §8-§7 utilize para coletar cash de seu vídeo!");
                    return true;
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("cadastrar")) {
                        String channelid = args[1];
                        String playername = args[2];
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Channel channel = getChannelForUser(channelid);
                                if (channel == null) {
                                    player.sendMessage("§cUsuário não existe, ID Incorreto!");
                                    return ;
                                } else {
                                    YoutubeUser youtubeUser = new YoutubeUser();
                                    youtubeUser.setChannel_id(channelid);
                                    youtubeUser.setUser(playername);
                                    youtubeUser.setCooldown(0);
                                    Storage storage = new Storage();
                                    storage.exportObject(youtubeUser);
                                    Main.cache.put(playername, youtubeUser);
                                    player.sendMessage("§aUsuário registrado no sistema com sucesso.");
                                    player.sendMessage("§aNome do canal: §f" + channel.getSnippet().getTitle());
                                    player.sendMessage("§aChannel ID: §f" + channelid);
                                    player.sendMessage("§aNúmero de inscritos: §f" + channel.getStatistics().getSubscriberCount());
                                    player.sendMessage("§aNúmero de videos: §f" + channel.getStatistics().getVideoCount());
                                    player.sendMessage("§aNúmero de Visualizações: §f" + channel.getStatistics().getViewCount());
                                    return;
                                }
                            }
                        }.runTaskAsynchronously(Main.getPlugin(Main.class));
                    }
                    return true;
                }
                if (player.hasPermission("ml.ytcadastro")) {
                    if (args[0].equalsIgnoreCase("verificar")) {
                        String user = args[1];
                        if (Main.cache.containsKey(user)) {
                            YoutubeUser youtubeUser = Main.cache.get(user);
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy HH:mm:ss");
                            if (youtubeUser.getCache().isEmpty()) {
                                player.sendMessage("");
                                player.sendMessage("§cO Usuário nunca coletou nenhuma recompensa.");
                                player.sendMessage("");
                                return true;
                            }
                            player.sendMessage("");
                            for (Map.Entry<Long, String> longStringEntry : youtubeUser.getCache().entrySet()) {
                                LocalDateTime triggerTime =
                                        LocalDateTime.ofInstant(Instant.ofEpochMilli(longStringEntry.getKey()),
                                                TimeZone.getTimeZone("America/Recife").toZoneId());
                                TextComponent textComponent = new TextComponent("§a ◆: §f" + dateTimeFormatter.format(triggerTime));
                                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, longStringEntry.getValue()));
                                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para abrir").create()));
                                player.sendMessage(textComponent);
                            }
                            player.sendMessage("");
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("resetarurls")) {
                        String user = args[1];
                        if (Main.cache.containsKey(user)) {
                            YoutubeUser youtubeUser = Main.cache.get(user);
                            youtubeUser.getCache().clear();
                            Storage storage = new Storage();
                            storage.exportObject(youtubeUser);
                            player.sendMessage("");
                            player.sendMessage("§a Yay! Histórico de Urls resetado! :)");
                            player.sendMessage("");
                        } else {
                            player.sendMessage("");
                            player.sendMessage("§c Ops! O Usuário precisa estar online!");
                            player.sendMessage("");
                            return true;
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("liberarcooldown")) {
                        String user = args[1];
                        if (Main.cache.containsKey(user)) {
                            YoutubeUser youtubeUser = Main.cache.get(user);
                            youtubeUser.setCooldown(0);
                            Storage storage = new Storage();
                            storage.exportObject(youtubeUser);
                            player.sendMessage("");
                            player.sendMessage("§a Yay! Cooldown foi liberado, ele pode coletar novamente :)");
                            player.sendMessage("");
                        } else {
                            player.sendMessage("");
                            player.sendMessage("§c Ops! O Usuário precisa estar online!");
                            player.sendMessage("");
                            return true;
                        }
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("video")) {
                    if (Main.cache.containsKey(player.getName())) {
                        YoutubeUser youtubeUser = Main.cache.get(player.getName());
                        if (youtubeUser.getCooldown() > System.currentTimeMillis()) {
                            player.sendMessage("§cAguarde " + TimeFormatter.format(youtubeUser.getCooldown() - System.currentTimeMillis()) + " para coletar novamente");
                            return true;
                        } else {
                            commandSender.sendMessage("§aVerificando video..");
                            String videourl = args[1];
                            if (!videourl.contains("https://") || !videourl.contains("watch?v=")) {
                                commandSender.sendMessage("§cURL Inválido.");
                                return true;
                            }
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    YouTube.Videos.List videoRequest = null;
                                    assert false;
                                    YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                                            request -> {
                                            }).setApplicationName("video-test").build();
                                    try {
                                        videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    String[] id = videourl.split("v=");
                                    String videoId = id[1];
                                    if (videoId.contains("&t=1s")){
                                        videoId = videoId.replace("&t=1s","");
                                    }
                                    videoRequest.setId(videoId);
                                    videoRequest.setKey("AIzaSyClQMkdascGe1KouOXV0QEEUgwnvLK6q8Q");
                                    VideoListResponse listResponse = null;
                                    try {
                                        listResponse = videoRequest.execute();
                                        List<Video> videoList = listResponse.getItems();
                                        if (!videoList.iterator().hasNext()) {
                                            player.sendMessage(Main.getPlugin(Main.class).getConfig().getString("Mensagens.VideonExiste").replace("&", "§"));
                                            return;
                                        }
                                        Video targetVideo = videoList.iterator().next();
                                        if (!targetVideo.getSnippet().getTitle().toLowerCase().contains(Main.getPlugin(Main.class).getConfig().getString("Titulo").toLowerCase())) {
                                            player.sendMessage(Main.getPlugin(Main.class).getConfig().getString("Mensagens.SemTitulo").replace("&", "§").replace("%titlo_contain%", Main.getPlugin(Main.class).getConfig().getString("Titulo")));
                                            return;
                                        }
                                        if (youtubeUser.getCache().containsValue(videourl)){
                                            player.sendMessage("");
                                            player.sendMessage("§c Ops! Parece que você já coletou o cash deste video!");
                                            player.sendMessage("");
                                            return;
                                        }
                                        for (String string : Main.getPlugin(Main.class).getConfig().getStringList("Descricao")) {
                                            if (!targetVideo.getSnippet().getDescription().toLowerCase().contains(string.toLowerCase())) {
                                                player.sendMessage(Main.getPlugin(Main.class).getConfig().getString("Mensagens.SemDesc").replace("&", "§").replace("%string%", string));
                                                return;
                                            }
                                        }
                                        if (!targetVideo.getSnippet().getChannelId().equalsIgnoreCase(youtubeUser.getChannel_id())) {
                                            player.sendMessage("");
                                            player.sendMessage("§c Ops! Parece que você não é dono deste vídeo!");
                                            player.sendMessage("");
                                            return;
                                        }
                                        int quantia = targetVideo.getStatistics().getLikeCount().intValue() * Main.getPlugin(Main.class).getConfig().getInt("Multiplicador.Numero");
                                        player.sendMessage("");
                                        player.sendMessage(Main.getPlugin(Main.class).getConfig().getString("Mensagens.Sucesso").replace("&", "§").replace("@quantia", String.valueOf(quantia)));
                                        player.sendMessage("");
                                        TitleAPI.sendFullTitle(player, 20, 40, 20, Main.getPlugin(Main.class).getConfig().getString("TituloPrincipal.Titulo").replace("&", "§").replace("@quantia", String.valueOf(quantia)), Main.getPlugin(Main.class).getConfig().getString("TituloPrincipal.SubTitulo").replace("&", "§").replace("@quantia", String.valueOf(quantia)));
                                        youtubeUser.setCooldown(System.currentTimeMillis() * 12 * 60 * 60 * 1000);
                                        youtubeUser.getCache().put(System.currentTimeMillis(),videourl);
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.getPlugin(Main.class).getConfig().getString("ComandoGive").replace("@quantia", String.valueOf(quantia)).replace("@player", player.getName()));
                                        Storage storage = new Storage();
                                        storage.exportObject(youtubeUser);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.runTaskAsynchronously(Main.getPlugin(Main.class));
                        }
                    } else {
                        commandSender.sendMessage("§cVocê necessita ser um youtuber registrado para isto.");
                    }
                }
                return true;
            } else {
            }
        } else {
            if (args.length != 3 && args.length != 2){
                if (commandSender.hasPermission("ml.ytcadastro")){
                    commandSender.sendMessage("§a/yt cadastrar <id do canal> <nick do jogador>§8-§7 Utilize para cadastrar um jogador");
                    commandSender.sendMessage("§a/yt verificar <nick do jogador>§8-§7 Utilize para verificar urls de um jogador");
                    commandSender.sendMessage("§a/yt liberarcooldown <nick do jogador> §8-§7 Utilize para liberar jogador de cooldowns!");
                }
                commandSender.sendMessage("§a/yt video <url> §8-§7 utilize para coletar cash de seu vídeo!");
                return true;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("cadastrar")) {
                    String channelid = args[1];
                    String playername = args[2];
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Channel channel = getChannelForUser(channelid);
                            if (channel == null) {
                                commandSender.sendMessage("§cUsuário não existe, ID Incorreto!");
                                return ;
                            } else {
                                YoutubeUser youtubeUser = new YoutubeUser();
                                youtubeUser.setChannel_id(channelid);
                                youtubeUser.setUser(playername);
                                youtubeUser.setCooldown(0);
                                Storage storage = new Storage();
                                storage.exportObject(youtubeUser);
                                Main.cache.put(playername, youtubeUser);
                                commandSender.sendMessage("§aUsuário registrado no sistema com sucesso.");
                                commandSender.sendMessage("§aNome do canal: §f" + channel.getSnippet().getTitle());
                                commandSender.sendMessage("§aChannel ID: §f" + channelid);
                                commandSender.sendMessage("§aNúmero de inscritos: §f" + channel.getStatistics().getSubscriberCount());
                                commandSender.sendMessage("§aNúmero de videos: §f" + channel.getStatistics().getVideoCount());
                                commandSender.sendMessage("§aNúmero de Visualizações: §f" + channel.getStatistics().getViewCount());
                                return;
                            }
                        }
                    }.runTaskAsynchronously(Main.getPlugin(Main.class));
                }
                return true;
            }
        }
        return false;
    }
}