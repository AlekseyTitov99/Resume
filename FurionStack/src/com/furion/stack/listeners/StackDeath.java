package com.furion.stack.listeners;

import com.furion.stack.Main;
import com.furion.stack.util.Glow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackDeath implements Listener {

    @EventHandler
    public void onDeath(final EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) return;
        if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world")) return;
        int ch = 0;
        for (final Block bl : getNearbyBlocks(e.getEntity().getLocation().subtract(0,2,0), 16)) {
            if (bl.getType().equals(Material.MOB_SPAWNER) && bl.getLocation().getY() < e.getEntity().getLocation().clone().subtract(0,2,0).getY() && Death.getTipo(bl).equals(e.getEntity().getType())) {
                ++ch;
            }
        }
        if (ch > 0) {
            if (e.getEntity().hasMetadata("qnt")) {
                final int entqnt = e.getEntity().getMetadata("qnt").get(0).asInt();
                final int cha = entqnt - 1;
                dropSuck(e.getEntity(), e.getDrops().size(), e.getEntity().getKiller(), true, 0);
                e.getDrops().clear();
                if (cha == 1) {
                    Entity dps = e.getEntity();
                    if (dps.getType().equals(EntityType.SLIME)) {
                        final Slime sl = (Slime)dps;
                        sl.setSize(1);
                    }
                    if (dps.getType().equals(EntityType.MAGMA_CUBE)) {
                        final MagmaCube sl2 = (MagmaCube)dps;
                        sl2.setSize(1);
                    }
                    dps.setMetadata("qnt", new FixedMetadataValue(Main.getPlugin(Main.class), cha));
                    if (e.getEntity().getKiller() != null) {
                        e.getEntity().getKiller().giveExp(e.getDroppedExp());
                    }
                    return;
                }
                if (cha > 1) {
                    Entity dps = e.getEntity();
                    if (dps.getType().equals(EntityType.SLIME)) {
                        final Slime sl = (Slime)dps;
                        sl.setSize(1);
                    }
                    if (dps.getType().equals(EntityType.MAGMA_CUBE)) {
                        final MagmaCube sl2 = (MagmaCube)dps;
                        sl2.setSize(1);
                    }
                    dps.setMetadata("qnt", new FixedMetadataValue(Main.getPlugin(Main.class), cha));
                    dps.setCustomName(Main.getPlugin(Main.class).getConfig().getString("nome").replace("&","§").replace("%quantia%",String.valueOf(entqnt)).replace("%tipo%",Spawn.translateMob(e.getEntityType().name())));
                    if (e.getEntity().getKiller() != null) {
                        e.getEntity().getKiller().giveExp(e.getDroppedExp());
                    }
                }
            }
            return;
        }
        if (e.getEntity().getKiller() != null && e.getEntity().getKiller() != null) {
            dropSuck(e.getEntity(), e.getDrops().size(), e.getEntity().getKiller(), false, e.getDroppedExp());
            e.setDroppedExp(0);
        }
        e.getDrops().clear();
    }

    public static int getAdd(final Entity e) {
        int i = 0;
        final int xx = 16;
        final int yy = 16;
        final int zz = 16;
        /**
         * Bad way, old project -> wouldn't do that this way.
         */
        for (int x = -xx; x <= xx; ++x) {
            for (int y = -yy; y <= yy; ++y) {
                for (int z = -zz; z <= zz; ++z) {
                    final BlockState blockstate = e.getLocation().clone().add((double)x, (double)y, (double)z).getBlock().getState();
                    if (blockstate.getType().equals((Object)Material.MOB_SPAWNER)) {
                        final CreatureSpawner spawner = (CreatureSpawner)blockstate;
                        if (spawner.getSpawnedType().equals((Object)e.getType())) {
                            ++i;
                        }
                    }
                }
            }
        }
        return i;
    }

    public static List<Block> getNearbyBlocks(final Location location, final int radius) {
        final List<Block> blocks = new ArrayList<Block>();
        /**
         * Bad way, old project -> wouldn't do that this way.
         */
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; ++x) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; ++y) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; ++z) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static void dropSuck(final Entity e, final int dro, final Player p, final boolean one, final int gxp) {
        int drop = 0;
        int i = 0;
        if (Main.getPlugin(Main.class).getConfig().get(e.getType().name()) == null) {
            return;
        }
        final Random r = new Random();
        drop = r.nextInt(2) + Main.getPlugin(Main.class).getConfig().getInt(e.getType().name().toUpperCase());
        ++i;
        final int vanilla;
        int dps = vanilla = drop;
        dps *= 2;
        int spawners = 0;
        if (!one) {
            final int add = Death.getAdd(e);
            final int curr = drop;
            dps = (spawners = curr * add);
            final int xp = 2 * add;
            final int exp = gxp * xp;
            p.giveExp(exp);
        }
        if (p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR) && p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
            final int lvl = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
            if (lvl == 1) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT1"));
            }
            if (lvl == 2) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT2"));
            }
            if (lvl == 3) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT3"));
            }
            if (lvl > 4) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOTCON"));
            }
        }
        int RoundedUp = (int) Math.ceil(dps);
        if (e.hasMetadata("qnt")){
            for (String itens : Main.getPlugin(Main.class).getConfig().getConfigurationSection("drops." + e.getType().name().toUpperCase()).getKeys(false)) {
                int ID = Main.getPlugin(Main.class).getConfig().getInt("drops." + e.getType().name().toUpperCase() + "." + itens + ".ID");
                int DATA = Main.getPlugin(Main.class).getConfig().getInt("drops." + e.getType().name().toUpperCase() + "." + itens + ".DATA");
                ItemStack dropkk = new ItemStack(Material.getMaterial(ID), RoundedUp);
                dropkk.setDurability((short) DATA);
                e.getLocation().getWorld().dropItem(e.getLocation(), dropkk);
            }
        }
    }

}