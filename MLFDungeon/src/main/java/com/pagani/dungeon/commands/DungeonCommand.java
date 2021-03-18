package com.pagani.dungeon.commands;

import com.pagani.dungeon.Main;
import com.pagani.dungeon.mysql.AtlasStorage;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DungeonCommand implements CommandExecutor {

    public static ItemStack getitemdungeon(DungeonUser dungeonUser) {
        if (dungeonUser.getTickets() > 0) {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("§eDungeon").setLore("§7Este é o inicio de uma jornada", "§7nas estrelas, repleta de raios", "§7tiros e fantasia", "§7venha já se aventurar.",
                    "", "§eVocê tem §7" + dungeonUser.getTickets() + "§e tickets para a dungeon.").toItemStack();
        } else {
            return new ItemBuilder(Material.DIAMOND_SWORD).setName("§eDungeon").setLore("§7Este é o inicio de uma jornada", "§7nas estrelas, repleta de raios", "§7tiros e fantasia", "§7venha já se aventurar.",
                    "", (Main.econ.getBalance(dungeonUser.getUsername()) >= 250000) ? "§aClique para comprar." : "§cVocê pricisa de §7250.000§c coins.").toItemStack();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (Main.cache.containsKey(player.getName())) {
                    DungeonUser dungeonUser = Main.cache.get(player.getName());
                    dungeonUser.setUsername(player.getName());
                    Inventory inventory = Bukkit.createInventory(null, 27, "Dungeon");
                    inventory.setItem(12, getitemdungeon(dungeonUser));
                    player.openInventory(inventory);
                } else {
                    AtlasStorage.loadUser(player.getName());
                    player.sendMessage("§cExecute o comando novamente!");
                }
                return true;
            }
            return false;
        }
        return false;
    }
}