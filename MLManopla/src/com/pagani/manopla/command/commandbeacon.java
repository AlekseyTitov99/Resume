package com.pagani.manopla.command;

import com.pagani.manopla.api.OpenJoiasMenu;
import javafx.print.PageLayout;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandbeacon implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("manopla") || command.getName().equalsIgnoreCase("beacon")){
            Player p = (Player) commandSender;
            OpenJoiasMenu.GenerateJoiaMenu(p);
        }
        return false;
    }

}