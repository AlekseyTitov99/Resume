package com.pagani.dragonupgrades;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.pagani.dragonupgrades.command.ComandoUpgrade;
import com.pagani.dragonupgrades.listener.General;
import com.pagani.dragonupgrades.object.User;
import com.pagani.dragonupgrades.sql.AtlasStorage;
import com.pagani.dragonupgrades.sql.MySQL;
import com.pagani.dragonupgrades.utils.Banner;
import net.minecraft.server.v1_8_R3.Chunk;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main extends JavaPlugin {

    public static HashMap<String, User> cache = new HashMap<>();
    public static MySQL mySQLconnection;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§e[Dragon] §cIniciando DragonUpdates");
        getCommand("upgrade").setExecutor(new ComandoUpgrade());
        getServer().getPluginManager().registerEvents(new General(),this);
        saveDefaultConfig();
        mySQLconnection = new MySQL(Main.getPlugin(Main.class), this.getConfig().getString("host"), this.getConfig().getString("username")
                , this.getConfig().getString("password"), this.getConfig().getString("database"), 3306);
        mySQLconnection.addTableStatement("CREATE TABLE IF NOT EXISTS `upgrades` ( `id` INT NOT NULL AUTO_INCREMENT ," +
                " `facs` VARCHAR(50) NOT NULL , `json` MEDIUMTEXT NOT NULL , PRIMARY KEY (`id`));");
        mySQLconnection.openConnection();
        this.saveDefaultConfig();
        for (Player p : Bukkit.getOnlinePlayers()){
            MPlayer mPlayer = MPlayer.get(p);
            if (mPlayer.hasFaction() && !cache.containsKey(mPlayer.getFactionName())) {
                AtlasStorage.loadFac(mPlayer.getFactionName());
            }
        }
        Banner banner = new Banner();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, User> stringUserEntry : cache.entrySet()) {
                    if (stringUserEntry.getValue().isNivel6()){
                        Faction faction = FactionColl.get().getByName(stringUserEntry.getKey());
                        Set<PS> chunks = BoardColl.get().getChunks(faction);
                        for (PS chunk : chunks) {
                            if (chunk.asBukkitChunk().isLoaded()) {
                                for (Entity entity : chunk.asBukkitChunk().getEntities()) {
                                    if (entity.getType() == EntityType.PLAYER) {
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                if (chunk.asBukkitChunk().isLoaded()) {
                                                    Player player = (Player) entity;
                                                    if (player.isOnline()) {
                                                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 8, 2), true);
                                                    }
                                                }
                                            }
                                        }.runTask(Main.getPlugin(Main.class));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),0L,20L * 4L);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§e[Dragon] §cDesligando DragonUpdates");
        for (Map.Entry<String, User> stringUserEntry : cache.entrySet()) {
            AtlasStorage.saveFac(stringUserEntry.getKey(),stringUserEntry.getValue(),false);
        }
        mySQLconnection.closeConnection();
    }

}