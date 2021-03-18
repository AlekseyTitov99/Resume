package com.pagani.dungeon.commands;

import com.pagani.dungeon.Main;
import com.pagani.dungeon.api.CorreioCore;
import com.pagani.dungeon.api.ScrollerInventory;
import javafx.print.PageLayout;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;

public class ComandoCorreio implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0){
            if (sender instanceof Player){
                Player player = (Player) sender;
                ArrayList<ItemStack> arrayList = new ArrayList<>();
                Main.cache.get(player.getName()).getCache().forEach(i -> {
                    try {
                        arrayList.add(CorreioCore.fromBase64(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                ScrollerInventory si = new ScrollerInventory(arrayList, "Correio", player,true,player.getName());
            }
        }
        return false;
    }

}