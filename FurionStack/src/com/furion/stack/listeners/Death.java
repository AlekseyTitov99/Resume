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
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Death implements Listener {

    @EventHandler
    public void aoMorrer(final EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            return;
        }
        if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        int ch = 0;
        Location x = e.getEntity().getLocation().clone().add(0, 3, 0);
        for (final Block bl : StackDeath.getNearbyBlocks(x, 19)) {
            if (bl.getType().equals(Material.MOB_SPAWNER) && bl.getLocation().getY() <= e.getEntity().getLocation().clone().subtract(0,3,0).getY() && getTipo(bl).equals(e.getEntity().getType())) {
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
                        final Slime sl = (Slime) dps;
                        sl.setSize(1);
                    }
                    if (dps.getType().equals(EntityType.MAGMA_CUBE)) {
                        final MagmaCube sl2 = (MagmaCube) dps;
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
                        final Slime sl = (Slime) dps;
                        sl.setSize(1);
                    }
                    if (dps.getType().equals(EntityType.MAGMA_CUBE)) {
                        final MagmaCube sl2 = (MagmaCube) dps;
                        sl2.setSize(1);
                    }
                    dps.setMetadata("qnt", new FixedMetadataValue(Main.getPlugin(Main.class), cha));
                    dps.setCustomName(Main.getPlugin(Main.class).getConfig().getString("nome").replace("&", "§").replace("%quantia%", String.valueOf(entqnt)).replace("%tipo%", Spawn.translateMob(e.getEntityType().name())));
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

    public static EntityType getTipo(final Block b) {
        final CreatureSpawner spawner = (CreatureSpawner) b.getState();
        return spawner.getSpawnedType();
    }


    @EventHandler
    public static void aoExplodir(final EntityExplodeEvent e) {
        if (e.getEntity() instanceof WitherSkull) {
            e.setCancelled(true);
        }
        if (e.getEntity().getType().equals(EntityType.WITHER_SKULL)) {
            e.setCancelled(true);
        }
        if (e.getEntity().getType().equals(EntityType.WITHER)) {
            e.setCancelled(true);
        }
    }

    public static void dropSuck(final Entity e, final int dro, final Player p, final boolean one, final int gxp) {
        int drop = 0;
        int i = 0;
        if (Main.getPlugin(Main.class).getConfig().get(e.getType().name()) == null) {
            return;
        }
        final Random r = new Random();
        drop = r.nextInt(2) + Main.getPlugin(Main.class).getConfig().getInt(e.getType().name().toUpperCase());
        i++;
        final int vanilla;
        int dps = vanilla = drop;
        dps *= 2;
        int spawners = 0;
        if (!one) {
            final int add = getAdd(e);
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
            if (lvl == 4) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT4"));
            }
            if (lvl == 5) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT5"));
            }
            if (lvl == 6) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT6"));
            }
            if (lvl == 7) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOT7"));
            }
            if (lvl > 7) {
                dps = (int) (dps * Main.getPlugin(Main.class).getConfig().getDouble("LOOTCON"));
            }
        }
        int RoundedUp = (int) Math.ceil(dps);
        for (String itens : Main.getPlugin(Main.class).getConfig().getConfigurationSection("drops." + e.getType().name().toUpperCase()).getKeys(false)) {
            int ID = Main.getPlugin(Main.class).getConfig().getInt("drops." + e.getType().name().toUpperCase() + "." + itens + ".ID");
            int DATA = Main.getPlugin(Main.class).getConfig().getInt("drops." + e.getType().name().toUpperCase() + "." + itens + ".DATA");
            ItemStack dropkk = new ItemStack(Material.getMaterial(ID));
            dropkk.setDurability((short) DATA);
            dropkk.setAmount(RoundedUp);
            e.getLocation().getWorld().dropItem(e.getLocation(), dropkk);
        }
        if (Main.getPlugin(Main.class).getConfig().get("custom." + e.getType().name().toUpperCase()) == null) {
            return;
        }
        String chance = Main.getPlugin(Main.class).getConfig().getString("custom." + e.getType().name().toUpperCase() + ".CHANCE");
        String[] kk = chance.split("/");
        Random random = new Random();
        if (random.nextInt(Integer.parseInt(kk[1])) <= Integer.parseInt(kk[0])) {
            int ID = Main.getPlugin(Main.class).getConfig().getInt("custom." + e.getType().name().toUpperCase() + ".ID");
            int DATA = Main.getPlugin(Main.class).getConfig().getInt("custom." + e.getType().name().toUpperCase() + ".DATA");
            String nome = Main.getPlugin(Main.class).getConfig().getString("custom." + e.getType().name().toUpperCase() + ".NOME");
            String nome2 = Main.getPlugin(Main.class).getConfig().getString("custom." + e.getType().name().toUpperCase() + ".ENCANTAMENTOS");
            String comando = Main.getPlugin(Main.class).getConfig().getString("custom." + e.getType().name().toUpperCase() + ".COMANDO");
            boolean comandotrue = false;
            if (comando.equalsIgnoreCase("") || comando.isEmpty()){

            } else {
                comandotrue = true;
            }
            boolean encantamentos = false;
            if (nome2.equalsIgnoreCase("")) {
                encantamentos = false;
            } else {
                encantamentos = true;
            }
            List<String> list = new ArrayList<>();
            for (String lore : Main.getPlugin(Main.class).getConfig().getStringList("custom." + e.getType().name().toUpperCase() + ".LORE")) {
                list.add(lore.replace("&", "§"));
            }
            ItemStack dropkk = new ItemStack(Material.getMaterial(ID));
            dropkk.setDurability((short) DATA);
            int QUANTIA = Main.getPlugin(Main.class).getConfig().getInt("custom." + e.getType().name().toUpperCase() + ".QUANTIA");
            dropkk.setAmount(QUANTIA);
            if (Main.getPlugin(Main.class).getConfig().getBoolean("custom." + e.getType().name().toUpperCase() + ".BRILHO")) {
                Glow glow = new Glow(ID);
                ItemMeta itemMeta = dropkk.getItemMeta();
                itemMeta.addEnchant(glow, 0, true);
                dropkk.setItemMeta(itemMeta);
            }
            ItemMeta itemMeta = dropkk.getItemMeta();
            itemMeta.setLore(list);
            itemMeta.setDisplayName(nome.replace("&", "§"));
            dropkk.setItemMeta(itemMeta);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(encantamentos));
            if (encantamentos) {
                String[] x = nome2.split(",");
                if (nome2.length() >= 3) {
                    Bukkit.getConsoleSender().sendMessage("§c1");
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[0])), Integer.parseInt(x[1]));
                }
                // 1,2,3,4
                if (nome2.length() >= 7) {
                    Bukkit.getConsoleSender().sendMessage("§c2");
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[2])), Integer.parseInt(x[3]));
                }
                // 1,2,3,4,5,6
                if (nome2.length() >= 11) {
                    Bukkit.getConsoleSender().sendMessage("§c3");
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[4])), Integer.parseInt(x[5]));
                }
                if (nome2.length() >= 15) {
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[6])), Integer.parseInt(x[7]));
                }
                if (nome2.length() >= 19) {
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[8])), Integer.parseInt(x[9]));
                }
                if (nome2.length() >= 23) {
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[10])), Integer.parseInt(x[11]));
                }
                if (nome2.length() >= 27) {
                    dropkk.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(x[12])), Integer.parseInt(x[13]));
                }
            }
            if (comandotrue){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),comando.replace("%player%",p.getName()));
                return;
            }
            e.getLocation().getWorld().dropItem(e.getLocation(), dropkk);
        }
    }
    public static int getAdd(final Entity e) {
        int i = 0;
        final int xx = 18;
        final int yy = 18;
        final int zz = 18;
        Location location = e.getLocation().clone();
            for (int x = location.getBlockX() - xx; x <= location.getBlockX() + xx; x++) {
                for (int y = location.getBlockY() - yy; y <= location.getBlockY() + yy; y++) {
                    for (int z = location.getBlockZ() - zz; z <= location.getBlockZ() + zz; z++) {
                        BlockState blockstate = location.getWorld().getBlockAt(x, y, z).getState();
                        if (blockstate.getType().equals(Material.MOB_SPAWNER)) {
                            final CreatureSpawner spawner = (CreatureSpawner) blockstate;
                            if (spawner.getSpawnedType().equals(e.getType())) {
                                ++i;
                            }
                        }
                    }
                }
            }
            return i;
        }
    }