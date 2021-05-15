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
                if (mPlayer.hasFaction()) {
                    Menu.Open2(p);
                }
            }
        }
        return false;
    }
}
