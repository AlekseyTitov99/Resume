package com.furion.stack.listeners;

import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.furion.stack.Main;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.techcable.tacospigot.event.entity.SpawnerPreSpawnEvent;
import org.bukkit.block.Biome;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftCreatureSpawner;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Spawn implements Listener  {

    @EventHandler
    public void onShit(McMMOPlayerXpGainEvent e){
        for (String k : Main.getPlugin(Main.class).getConfig().getConfigurationSection("xp").getKeys(false)){
            if (e.getPlayer().hasPermission(k)) {
                int level = Main.getPlugin(Main.class).getConfig().getInt("xp." + k);
                e.setXpGained(e.getXpGained() * level);
            }
        }
    }

    @EventHandler
    public static void aoTomar(final EntityDamageEvent e) {
        if (e.getEntity().getType().equals((Object)EntityType.SLIME) && e.getCause().equals((Object)EntityDamageEvent.DamageCause.FALL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTarget(final EntityTargetEvent e) {
        if (e.getEntity().getWorld().getName().equalsIgnoreCase("world")){
            if (e.getEntity() instanceof Creature) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void ins(SpawnerSpawnEvent event){
        CraftCreatureSpawner spawner = (CraftCreatureSpawner) event.getSpawner().getBlock().getState();
        NBTTagCompound tag = new NBTTagCompound();
        spawner.getTileEntity().b(tag);
        tag.setInt("MinSpawnDelay", Main.getPlugin(Main.class).getConfig().getInt("MinSpawnDelay"));
        tag.setInt("MaxSpawnDelay"a,  Main.getPlugin(Main.class).getConfig().getInt("MaxSpawnDelay"));
        spawner.getTileEntity().a(tag);
        spawner.getTileEntity().update();
        spawner.update();
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void ins2(SpawnerSpawnEvent event){
        CraftCreatureSpawner spawner = (CraftCreatureSpawner) event.getSpawner().getBlock().getState();
        spawner.getTileEntity().update();
        spawner.update(true);
        CreatureSpawner creatureSpawner = (CreatureSpawner) event.getSpawner();
        creatureSpawner.update(true);
    }

    public static ArrayList<String> seeing = new ArrayList<>();

    @EventHandler
    public void onTP(CreatureSpawnEvent e) {
        Entity spawned = e.getEntity();
        LivingEntity entidade = e.getEntity();
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity().getType() == EntityType.ARMOR_STAND) {
            return;
        }
        if (e.getEntity().getType() == EntityType.CREEPER) {
            return;
        }
        if (e.getEntity().getType() == EntityType.ARMOR_STAND) {
            return;
        }
        if (e.getEntity().getType().equals(EntityType.MINECART_TNT)) {
            return;
        }
        if (e.getEntity().getType().equals(EntityType.PRIMED_TNT)) {
            return;
        }
        if (e.getEntity().hasMetadata("CanhaoTNT")) {
            return;
        }
        if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        PS kfg = PS.valueOf(entidade);
        Faction faction = BoardColl.get().getFactionAt(kfg);
        if (faction.isNone()) {
            if (e.getEntity().getType() == EntityType.CREEPER) {
                return;
            }
            e.setCancelled(true);
            return;
        }
        for (Entity entity : spawned.getNearbyEntities(15D, 15D, 15D)) {
            if (entity.getType() == e.getEntityType() && !entity.isDead()) {
                e.setCancelled(true);
                int amount = 1;
                LivingEntity living = (LivingEntity) entity;
                if (entity.hasMetadata("qnt"))
                    amount += entity.getMetadata("qnt").isEmpty() ? 0 : entity.getMetadata("qnt").get(0).asInt();
                if (amount > Main.getPlugin(Main.class).getConfig().getInt("LimiteMobs")) {
                    e.setCancelled(true);
                    return;
                }
                String name = Main.getPlugin(Main.class).getConfig().getString("nome").replace("&", "§").replace("%tipo%", translateMob(entity.getType().getName())).replace("%quantia%", String.valueOf(++amount) + "x");
                living.setCustomName(name);
                living.setCustomNameVisible(true);
                living.setMetadata("qnt", new FixedMetadataValue(Main.getPlugin(Main.class), amount));
                entidade.setMaximumAir(1);
                entidade.setRemainingAir(1);
                return;
            }
        }
        if (!spawned.hasMetadata("qnt")) {
            String name = Main.getPlugin(Main.class).getConfig().getString("nome").replace("&", "§").replace("%tipo%", translateMob(spawned.getType().getName())).replace("%quantia%", String.valueOf(1) + "x");
            spawned.setCustomName(name);
            spawned.setCustomNameVisible(true);
            spawned.setMetadata("qnt", new FixedMetadataValue(Main.getPlugin(Main.class), 1));
            if (entidade.getType().equals(EntityType.MUSHROOM_COW)) {
                e.getLocation().getWorld().setBiome(e.getLocation().getBlockX(), e.getLocation().getBlockY(), Biome.MUSHROOM_ISLAND);
                MushroomCow mushroomCow = (MushroomCow) entidade;
                mushroomCow.setAdult();
            }
            if (entidade.getType().equals(EntityType.MAGMA_CUBE)) {
                MagmaCube mushroomCow = (MagmaCube) entidade;
                mushroomCow.setSize(1);
            }
            return;
        }
    }

    @EventHandler
    public void onTeleport(EntityTeleportEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            for (Entity entity2 : e.getTo().getChunk().getEntities()) {
                if (entity2.getType() == e.getEntityType() && !entity2.isDead()) {
                    e.getEntity().remove();
                    e.setCancelled(true);
                    int amount = 1;
                    LivingEntity living = (LivingEntity) entity2;
                    if (entity2.hasMetadata("qnt"))
                        amount += entity2.getMetadata("qnt").isEmpty() ? 0 : entity2.getMetadata("qnt").get(0).asInt();
                    if (amount > Main.getPlugin(Main.class).getConfig().getInt("LimiteMobs")) {
                        e.setCancelled(true);
                        return;
                    }
                    String name = Main.getPlugin(Main.class).getConfig().getString("nome").replace("&", "§").replace("%tipo%", translateMob(entity2.getType().getName())).replace("%quantia%", String.valueOf(++amount) + "x");
                    living.setCustomName(name);
                    living.setCustomNameVisible(true);
                    living.setMetadata("qnt", new FixedMetadataValue(Main.getPlugin(Main.class), amount));
                    return;
                }
            }
        }
    }

    public static String translateMob(final String nome) {
        String ret = "";
        if (nome.equalsIgnoreCase("SPIDER")) {
            ret = "Aranha";
        }
        if (nome.equalsIgnoreCase("BLAZE")) {
            ret = "Blaze";
        }
        if (nome.equalsIgnoreCase("PIG_ZOMBIE")) {
            ret = "Porco Zumbi";
        }
        if (nome.equalsIgnoreCase("Iron_Golem")) {
            ret = "Golem";
        }
        if (nome.equalsIgnoreCase("Pig")) {
            ret = "Porco";
        }
        if (nome.equalsIgnoreCase("Sheep")) {
            ret = "Ovelha";
        }
        if (nome.equalsIgnoreCase("Zombie")) {
            ret = "Zumbi";
        }
        if (nome.equalsIgnoreCase("Cow")) {
            ret = "Vaca";
        }
        if (nome.equalsIgnoreCase("Skeleton")) {
            ret = "Esqueleto";
        }
        if (nome.equalsIgnoreCase("Slime")) {
            ret = "Slime";
        }
        if (nome.equalsIgnoreCase("MushroomCow")) {
            ret = "Vaca Cogumelo";
        }
        if (nome.equalsIgnoreCase("Enderman")) {
            ret = "Enderman";
        }
        if (nome.equalsIgnoreCase("Chicken")) {
            ret = "Galinha";
        }
        if (nome.equalsIgnoreCase("Creeper")) {
            ret = "Creeper";
        }
        if (nome.equalsIgnoreCase("WITHER")) {
            ret = "Wither";
        }
        if (nome.equalsIgnoreCase("Witch")) {
            ret = "Bruxa";
        }
        if (nome.equalsIgnoreCase("pig")){
            ret = "Porco";
        }
        if (nome.equalsIgnoreCase("Magma_cube")) {
            ret = "Magma";
        }
        if (nome.equalsIgnoreCase("cave_spider")){
            ret = "Aranha da Caverna";
        }
        if (ret.equals("")) {
            ret = nome;
        }
        return ret;
    }
}