package com.pagani.dungeon.commands;

import com.pagani.dungeon.Main;
import com.pagani.dungeon.util.DungeonNPC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDungeonNPCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            if (sender.hasPermission("arplex.dungeon.setnpc")){
                Location location = ((Player) sender).getLocation();
                String string = DungeonNPC.getSerializedLocation(location.add(0,2,0));
                Main.getPlugin(Main.class).getConfig().set("npcloc",string);
                Main.getPlugin(Main.class).saveConfig();
                Main.getPlugin(Main.class).reloadConfig();
                DungeonNPC.despawn();
                DungeonNPC.spawn();
            }
        }
        return false;
    }
}
