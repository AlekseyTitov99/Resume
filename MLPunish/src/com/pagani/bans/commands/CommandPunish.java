package com.pagani.bans.commands;

import com.pagani.bans.Dc.BanEvent;
import com.pagani.bans.Main;
import com.pagani.bans.Utils.TimeFormatter;
import com.pagani.bans.objetos.Ban.Punish;
import com.pagani.bans.objetos.PunishType;
import com.pagani.bans.objetos.PunishUser;
import com.pagani.bans.storage.Storage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

public class CommandPunish extends Command {

    public CommandPunish(String name) {
        super("punir");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            if (args.length == 2 || args.length == 3) {
                // punir <nick> <reason>
                if (Main.config.getSection("punicoes").getKeys().contains(args[1])) {
                    CompletableFuture.runAsync(() -> {
                        ProxiedPlayer p = BungeeCord.getInstance().getPlayer(args[0]);
                        PunishUser punishUser = null;
                        if (Main.cache.containsKey(args[0])) {
                            punishUser = Main.cache.get(args[0]);
                        } else {
                            punishUser = Storage.importUser(args[0]);
                        }
                        if (punishUser == null) {
                            throw new NullPointerException();
                        }
                        Punish punish = new Punish(args[0], sender.getName(), System.currentTimeMillis(),
                                PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")), args.length == 3 ? args[2] : "",
                                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")), System.currentTimeMillis() + Main.config.getInt("punicoes." + args[1] + ".time") * 60 * 1000L, Main.config.getString("punicoes." + args[1] + ".nome"), new Random().nextInt(100000));
                        punishUser.setActual(punish);
                        punishUser.getPunições().add(punish);
                        Storage.exportObject(punishUser);
                        BanEvent banEvent = new BanEvent(args[0],punish);
                        BungeeCord.getInstance().getPluginManager().callEvent(banEvent);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        if (p == null) {
                            if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BANIMENTO)) {
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi banido permanentemente.");
                                BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                BungeeCord.getInstance().broadcast("");
                                int banidos = Main.config.getInt("dia.banidos");
                                Main.config.set("dia.banidos",banidos + 1);
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                try {
                                    cfgProvider.save(Main.config,file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BAN_TEMP)) {
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi banido temporariamente.");
                                BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                BungeeCord.getInstance().broadcast("");
                                int banidos = Main.config.getInt("dia.banidos");
                                Main.config.set("dia.banidos",banidos + 1);
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                try {
                                    cfgProvider.save(Main.config,file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.SILENCIAMENTO)) {
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi silenciado temporariamente.");
                                BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                BungeeCord.getInstance().broadcast("");
                                int banidos = Main.config.getInt("dia.mutados");
                                Main.config.set("dia.mutados",banidos + 1);
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                try {
                                    cfgProvider.save(Main.config,file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return;
                        } else {
                            if (p.isConnected()) {
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BANIMENTO)) {
                                    p.disconnect(
                                            "§8 < §6§lMineLandia §8>\n\n§cVocê está banido permanentemente.\n" +
                                                    "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                                                    "\n§cAplicador da punição: " + punishUser.getActual().getAuthor()
                                                    + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                                                    "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                                                    "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi banido permanentemente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.banidos");
                                    Main.config.set("dia.banidos",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BAN_TEMP)) {
                                    p.disconnect(
                                            "§8 < §6§lMineLandia §8>\n\n§cVocê está banido temporariamente.\n" +
                                                    "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                                                    "\n§cAplicador da punição: " + punishUser.getActual().getAuthor()
                                                    + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                                                    "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                                                    "\n§cExpira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()) +
                                                    "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi banido temporariamente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.banidos");
                                    Main.config.set("dia.banidos",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.SILENCIAMENTO)) {
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi silenciado temporariamente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.mutados");
                                    Main.config.set("dia.mutados",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!Main.cache.containsKey(p.getName())) {
                                    Main.cache.put(p.getName(), punishUser);
                                }
                            }
                        }
                    });
                } else {
                    sender.sendMessage("§cMotivo não é válido.");
                }
            }
            return;
        }
        if (args.length == 0) {
            if (Main.config.getSection("equipe").getKeys().contains(sender.getName())) {
                sender.sendMessage("§cUtilize: /punir [nick] [motivo] [prova]");
            } else {
                sender.sendMessage("§cVocê precisa ser um membro da equipe para executar uma punição.");
            }
        }
        if (args.length == 1) {
            // /punir [nome]
            if (Main.config.getSection("equipe").getKeys().contains(sender.getName())) {
                String player = args[0];
                sender.sendMessage("");
                sender.sendMessage("§aSelecione um dos motivos abaixo:");
                sender.sendMessage("");
                for (String punicoes : Main.config.getSection("punicoes").getKeys()) {
                    String nome = Main.config.getString("punicoes." + punicoes + ".nome");
                    Integer integer = Main.config.getInt("punicoes." + punicoes + ".time");
                    PunishType punishType = PunishType.valueOf(Main.config.getString("punicoes." + punicoes + ".type"));
                    TextComponent textComponent = new TextComponent("§b ▸ §f" + nome);
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Tipo de banimento: §f" + punishType.name() + "\n" +
                            "§7Tempo de banimento: §f" + (punishType == PunishType.BANIMENTO ? "Permanente" : TimeFormatter.format(integer * 60000))
                            + "\n" + "§7Servidores: §fTodos." + "\n" + "Cargo Mínimo: " + (punishType == PunishType.BANIMENTO || punishType == PunishType.BAN_TEMP ? "§aModerador e superiores" : "§3Suporte e superiores")).create()));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/punir " + player + " " + punicoes));
                    sender.sendMessage(textComponent);
                }
                sender.sendMessage("");
            } else {
                sender.sendMessage("§cVocê precisa ser um membro da equipe para executar uma punição.");
                return;
            }
        }
        if (args.length == 2) {
            // /punir [player] [motivo]
            if (Main.config.getSection("equipe").getKeys().contains(sender.getName())) {
                if (Main.config.getSection("punicoes").getKeys().contains(args[1])) {
                    int poder = Integer.parseInt(Main.config.getString("equipe." + sender.getName()));
                    if (poder < 3) {
                        sender.sendMessage("§cVocê não pode executar um banimento sem provas.");
                        return;
                    }
                    CompletableFuture.runAsync(() -> {
                        ProxiedPlayer p = BungeeCord.getInstance().getPlayer(args[0]);
                        PunishUser punishUser = null;
                        if (Main.cache.containsKey(args[0])) {
                            punishUser = Main.cache.get(args[0]);
                        } else {
                            punishUser = Storage.importUser(args[0]);
                        }
                        if (punishUser == null) {
                            throw new NullPointerException();
                        }
                        Punish punish = new Punish(args[0], sender.getName(), System.currentTimeMillis(),
                                PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")), "",
                                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")), System.currentTimeMillis() + Main.config.getInt("punicoes." + args[1] + ".time") * 60 * 1000L, Main.config.getString("punicoes." + args[1] + ".nome"), new Random().nextInt(100000));
                        punishUser.setActual(punish);
                        punishUser.getPunições().add(punish);
                        Storage.exportObject(punishUser);
                        BanEvent banEvent = new BanEvent(args[0],punish);
                        BungeeCord.getInstance().getPluginManager().callEvent(banEvent);
                        sender.sendMessage("§aPunição aplicada.");
                        net.md_5.bungee.api.Title title = ProxyServer.getInstance().createTitle();
                        title.title(new TextComponent("§a§lPunição aplicada"));
                        title.subTitle(new TextComponent("§7Parabéns."));
                        title.fadeIn(20);
                        title.fadeOut(20);
                        title.stay(40);
                        title.send((ProxiedPlayer) sender);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        if (p == null) {
                            if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BANIMENTO)) {
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi banido permanentemente.");
                                BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                BungeeCord.getInstance().broadcast("");
                                int banidos = Main.config.getInt("dia.banidos");
                                Main.config.set("dia.banidos",banidos + 1);
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                try {
                                    cfgProvider.save(Main.config,file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BAN_TEMP)) {
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi banido temporariamente.");
                                BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                BungeeCord.getInstance().broadcast("");
                                int banidos = Main.config.getInt("dia.banidos");
                                Main.config.set("dia.banidos",banidos + 1);
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                try {
                                    cfgProvider.save(Main.config,file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.SILENCIAMENTO)) {
                                BungeeCord.getInstance().broadcast("");
                                BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi silenciado temporariamente.");
                                BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                BungeeCord.getInstance().broadcast("");
                                int banidos = Main.config.getInt("dia.mutados");
                                Main.config.set("dia.mutados",banidos + 1);
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                try {
                                    cfgProvider.save(Main.config,file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return;
                        } else {
                            if (p.isConnected()) {
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BANIMENTO)) {
                                    p.disconnect(
                                            "§8 < §6§lMineLandia §8>\n\n§cVocê está banido permanentemente.\n" +
                                                    "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                                                    "\n§cAplicador da punição: " + punishUser.getActual().getAuthor()
                                                    + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                                                    "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                                                    "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi banido permanentemente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.banidos");
                                    Main.config.set("dia.banidos",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BAN_TEMP)) {
                                    p.disconnect(
                                            "§8 < §6§lMineLandia §8>\n\n§cVocê está banido temporariamente.\n" +
                                                    "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                                                    "\n§cAplicador da punição: " + punishUser.getActual().getAuthor()
                                                    + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                                                    "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                                                    "\n§cExpira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()) +
                                                    "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi banido temporariamente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.banidos");
                                    Main.config.set("dia.banidos",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.SILENCIAMENTO)) {
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi silenciado temporariamente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.mutados");
                                    Main.config.set("dia.mutados",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!Main.cache.containsKey(p.getName())) {
                                    Main.cache.put(p.getName(), punishUser);
                                }
                            }
                        }
                    });
                } else {
                    sender.sendMessage("§cNão existe um motivo de punição igual a este: " + args[1]);
                }
            } else {
                sender.sendMessage("§cVocê precisa ser um membro da equipe para executar uma punição.");
                return;
            }
            return;
        }
        if (args.length == 3) {
            // /punir [player] [motivo] [prova]
            CompletableFuture.runAsync(() -> {
                if (Main.config.getSection("equipe").getKeys().contains(sender.getName())) {
                    if (Main.config.getSection("punicoes").getKeys().contains(args[1])) {
                        CompletableFuture.runAsync(() -> {
                            ProxiedPlayer p = BungeeCord.getInstance().getPlayer(args[0]);
                            if (!args[2].contains("https://")) {
                                sender.sendMessage("§cVocê precisa inserir provas que comecem com §7'https://'§c.");
                                return;
                            }
                            PunishUser punishUser = null;
                            if (Main.cache.containsKey(args[0])) {
                                punishUser = Main.cache.get(args[0]);
                            } else {
                                punishUser = Storage.importUser(args[0]);
                            }
                            if (punishUser == null) {
                                throw new NullPointerException();
                            }
                            PunishType punishType = PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type"));
                            if (punishType == PunishType.BANIMENTO || punishType == PunishType.BAN_TEMP){
                                int poder = Integer.parseInt(Main.config.getString("equipe." + sender.getName()));
                                if (poder < 2) {
                                    sender.sendMessage("§cVocê não pode executar um banimento.");
                                    return;
                                }
                            }
                            Punish punish = new Punish(args[0], sender.getName(), System.currentTimeMillis(),
                                    PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")), args[2],
                                    LocalDateTime.now(ZoneId.of("America/Sao_Paulo")), System.currentTimeMillis() + Main.config.getInt("punicoes." + args[1] + ".time") * 60 * 1000L, Main.config.getString("punicoes." + args[1] + ".nome"), new Random().nextInt(100000));
                            punishUser.setActual(punish);
                            punishUser.getPunições().add(punish);
                            Storage.exportObject(punishUser);
                            sender.sendMessage("§aPunição aplicada.");
                            net.md_5.bungee.api.Title title = ProxyServer.getInstance().createTitle();
                            title.title(new TextComponent("§a§lPunição aplicada"));
                            title.subTitle(new TextComponent("§7Parabéns."));
                            title.fadeIn(20);
                            title.fadeOut(20);
                            title.stay(40);
                            title.send((ProxiedPlayer) sender);
                            BanEvent banEvent = new BanEvent(args[0],punish);
                            BungeeCord.getInstance().getPluginManager().callEvent(banEvent);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                            if (p == null) {
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BANIMENTO)) {
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi banido permanentemente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.banidos");
                                    Main.config.set("dia.banidos",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BAN_TEMP)) {
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi banido temporariamente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.banidos");
                                    Main.config.set("dia.banidos",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.SILENCIAMENTO)) {
                                    BungeeCord.getInstance().broadcast("");
                                    BungeeCord.getInstance().broadcast("§c * " + args[0] + " foi silenciado temporariamente.");
                                    BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                    BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                    BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                    BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                    BungeeCord.getInstance().broadcast("");
                                    int banidos = Main.config.getInt("dia.mutados");
                                    Main.config.set("dia.mutados",banidos + 1);
                                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                    ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                    try {
                                        cfgProvider.save(Main.config,file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return;
                            } else {
                                if (p.isConnected()) {
                                    if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BANIMENTO)) {
                                        p.disconnect(
                                                "§8 < §6§lMineLandia §8>\n\n§cVocê está banido permanentemente.\n" +
                                                        "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                                                        "\n§cAplicador da punição: " + punishUser.getActual().getAuthor()
                                                        + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                                                        "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                                                        "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
                                        BungeeCord.getInstance().broadcast("");
                                        BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi banido permanentemente.");
                                        BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                        BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                        BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                        BungeeCord.getInstance().broadcast("");
                                        int banidos = Main.config.getInt("dia.banidos");
                                        Main.config.set("dia.banidos",banidos + 1);
                                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                        ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                        try {
                                            cfgProvider.save(Main.config,file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return;
                                    }
                                    if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.BAN_TEMP)) {
                                        p.disconnect(
                                                "§8 < §6§lMineLandia §8>\n\n§cVocê está banido temporariamente.\n" +
                                                        "\n§cData: " + formatter.format(punishUser.getActual().getDate()) +
                                                        "\n§cAplicador da punição: " + punishUser.getActual().getAuthor()
                                                        + "\n§cProva: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()) +
                                                        "\n§cMotivo: " + punishUser.getActual().getMotivo() +
                                                        "\n§cExpira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()) +
                                                        "\n \n§cUse o ID §e#" + punishUser.getActual().getID() + "§c para criar uma revisão em: https://discord.gg/9UMpMNb");
                                        BungeeCord.getInstance().broadcast("");
                                        BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi banido temporariamente.");
                                        BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                        BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                        BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                        BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                        BungeeCord.getInstance().broadcast("");
                                        int banidos = Main.config.getInt("dia.banidos");
                                        Main.config.set("dia.banidos",banidos + 1);
                                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                        ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                        try {
                                            cfgProvider.save(Main.config,file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        return;
                                    }
                                    if (PunishType.valueOf(Main.config.getString("punicoes." + args[1] + ".type")).equals(PunishType.SILENCIAMENTO)) {
                                        BungeeCord.getInstance().broadcast("");
                                        BungeeCord.getInstance().broadcast("§c * " + p.getName() + " foi silenciado temporariamente.");
                                        BungeeCord.getInstance().broadcast("§c * Aplicador da punição: " + sender.getName());
                                        BungeeCord.getInstance().broadcast("§c * Motivo: " + Main.config.getString("punicoes." + args[1] + ".nome"));
                                        BungeeCord.getInstance().broadcast("§c * Prova: " + (punishUser.getActual().getProva().isEmpty() ? "Sem provas" : punishUser.getActual().getProva()));
                                        BungeeCord.getInstance().broadcast("§c * Expira em: " + TimeFormatter.format(punishUser.getActual().getFinishAt() - System.currentTimeMillis()));
                                        BungeeCord.getInstance().broadcast("");
                                        int banidos = Main.config.getInt("dia.mutados");
                                        Main.config.set("dia.mutados",banidos + 1);
                                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                        ConfigurationProvider cfgProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
                                        try {
                                            cfgProvider.save(Main.config,file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            Main.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (!Main.cache.containsKey(p.getName())) {
                                        Main.cache.put(p.getName(), punishUser);
                                    }
                                }
                            }
                        });
                    }
                } else {
                    BungeeCord.getInstance().broadcast(String.valueOf(cache.size()));
                    sender.sendMessage("§cVocê precisa ser um membro da equipe para executar uma punição.");
                    return;
                }
            });
        }
    }
}