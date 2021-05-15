package com.furion.stack;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.furion.stack.listeners.Spawn;
import com.furion.stack.listeners.StackDeath;
import javafx.print.PageLayout;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutTileEntityData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ComandoAtivar implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String lbl, String[] args) {
        if (args.length == 0){
            if (Spawn.seeing.contains(s.getName())){
                s.sendMessage("§aDesativado.");
                Spawn.seeing.remove(s.getName());
                Player p = (Player) s;
                p.getLocation().getChunk().load();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (final Block bl : StackDeath.getNearbyBlocks(p.getLocation(), 32)) {
                            if (bl.getType().equals(Material.MOB_SPAWNER)) {
                                CraftCreatureSpawner spawner = (CraftCreatureSpawner) bl.getState().getBlock().getState();
                                spawner.update();
                            }
                        }
                    }
                }.runTaskAsyncronously(Main.getPlugin(Main.class));
                return true;
            } else {
                s.sendMessage("§aAtivado.");
                Spawn.seeing.add(s.getName());
                Player p = (Player) s;
                p.getLocation().getChunk().load();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (final Block bl : StackDeath.getNearbyBlocks(p.getLocation(), 32)) {
                            if (bl.getType().equals(Material.MOB_SPAWNER)) {
                                CraftCreatureSpawner spawner = (CraftCreatureSpawner) bl.getState().getBlock().getState();
                                spawner.update();
                            }
                        }
                    }
                }.runTaskAsyncronously(Main.getPlugin(Main.class));
                return true;
            }
        }
        return false;
    }
}
