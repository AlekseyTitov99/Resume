package com.pagani.dragonupgrades.command;

import com.creeperevents.oggehej.obsidianbreaker.ObsidianBreaker;
import com.creeperevents.oggehej.obsidianbreaker.StorageHandler;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.pagani.dragonupgrades.Main;
import com.pagani.dragonupgrades.features.Menu;
import com.pagani.dragonupgrades.object.User;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class ComandoUpgrade implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
        if (cmd.getName().equalsIgnoreCase("upgrade")) {
            if (args.length == 0){
                Player p = (Player) s;
                MPlayer mPlayer = MPlayer.get(p);
                if (Main.cache.containsKey(mPlayer.getFactionName())){
                    User user = Main.cache.get(mPlayer.getFactionName());
                    if (user.isNivel6()){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Faction faction = mPlayer.getFaction();
                                    Set<PS> ps = BoardColl.get().getChunks(faction);
                                    for (PS ps1 : ps) {
                                        Chunk c = ps1.asBukkitChunk();
                                        int cx = c.getX() << 4;
                                        int cz = c.getZ() << 4;
                                        for (int x = cx; x < cx + 16; x++) {
                                            for (int z = cz; z < cz + 16; z++) {
                                                for (int y = 0; y < 256; y++) {
                                                    if (Bukkit.getWorld("world").getBlockAt(x, y, z).getType() == Material.BEDROCK) {
                                                        StorageHandler storageHandler = ObsidianBreaker.getStorage();
                                                        if (storageHandler.isValidBlock(Bukkit.getWorld("world").getBlockAt(x, y, z))) {
                                                            if (storageHandler.getBlockStatus(Bukkit.getWorld("world").getBlockAt(x, y, z),true).getTotalDurability() >= 25f)continue;
                                                            storageHandler.getBlockStatus(Bukkit.getWorld("world").getBlockAt(x, y, z), true).setTotalDurability(25f);
                                                            storageHandler.getBlockStatus(Bukkit.getWorld("world").getBlockAt(x, y, z), true).setDamage(0f);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        }.runTaskAsynchronously(Main.getPlugin(Main.class));
                    }
                }
                if (mPlayer.hasFaction()) {
                    Menu.Open2(p);
                }
            }
        }
        return false;
    }
}