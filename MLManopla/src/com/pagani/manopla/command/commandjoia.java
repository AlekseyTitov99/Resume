package com.pagani.manopla.command;

import com.pagani.manopla.api.JoiaType;
import com.pagani.manopla.api.getJoia;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandjoia implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("joia") || command.getName().equalsIgnoreCase("givejoia")) {
            if (commandSender.hasPermission("mage.gerente")) {
                if (args.length == 2) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("ESPACO")) {
                        player.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.ESPAÇO));
                    }
                    if (args[0].equalsIgnoreCase("MENTE")) {
                        player.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.MENTE));
                    }
                    if (args[0].equalsIgnoreCase("PODER")) {
                        player.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.PODER));
                    }
                    if (args[0].equalsIgnoreCase("REALIDADE")) {
                        player.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.REALIDADE));
                    }
                    if (args[0].equalsIgnoreCase("TEMPO")) {
                        player.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.TEMPO));
                    }
                    if (args[0].equalsIgnoreCase("FORCA")) {
                        player.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.FORÇA));
                    }
                }
            } else {
                commandSender.sendMessage("§cVocê precisa do grupo Gerente ou superior para executar este comando.");
            }
        }
        return false;
    }

}