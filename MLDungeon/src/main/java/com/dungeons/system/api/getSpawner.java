package com.dungeons.system.api;

import com.dungeons.system.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class getSpawner {

    public static ItemStack Pegar(String k){
        try {
            EntityType ko = EntityType.valueOf(k.toUpperCase());
            switch (ko) {
                case MAGMA_CUBE: {
                    ItemStack itemStack = new ItemBuilder(Skull.MAGMA_CUBE.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Cubo de Magma")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case ENDERMAN: {
                    ItemStack itemStack = new ItemBuilder(Skull.ENDERMAN.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Enderman")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case OCELOT:{//http://textures.minecraft.net/texture/51f07e3f2e5f256bfade666a8de1b5d30252c95e98f8a8ecc6e3c7b7f67095
                    ItemStack itemStack = new ItemBuilder(Skull.OCELOT.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Jaguátirica")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case COW: {
                    ItemStack itemStack = new ItemBuilder(Skull.COW.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Vaca")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case BLAZE: {
                    ItemStack itemStack = new ItemBuilder(Skull.BLAZE.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Blaze")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case HORSE: {
                    ItemStack itemStack = new ItemBuilder(Skull.HORSE.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Cavalo")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case ZOMBIE: {
                    ItemStack itemStack = new ItemBuilder(Skull.ZOMBIE.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Zombie")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case SPIDER: {
                    ItemStack itemStack = new ItemBuilder(Skull.SPIDER.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Aranha")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case SLIME: {
                    ItemStack itemStack = new ItemBuilder(Skull.SLIME.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Slime")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case GHAST: {
                    ItemStack itemStack = new ItemBuilder(Skull.GHAST.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Ghast")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case WITCH: {
                    ItemStack itemStack = new ItemBuilder(Skull.WITCH.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Bruxa")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case WITHER: {
                    ItemStack itemStack = new ItemBuilder(Skull.WITHER.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Wither")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case SKELETON: {
                    ItemStack itemStack = new ItemBuilder(Skull.SKELETON.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Esqueleto")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case IRON_GOLEM: {
                    ItemStack itemStack = new ItemBuilder(Skull.IRONGOLEM.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Golem de Ferro")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case PIG_ZOMBIE: {
                    ItemStack itemStack = new ItemBuilder(Skull.PIG_ZOMBIE.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Porco Zumbi")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case CAVE_SPIDER: {
                    ItemStack itemStack = new ItemBuilder(Skull.CAVE_SPIDER.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Aranha das Cavernas")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case CREEPER: {
                    ItemStack itemStack = new ItemBuilder(Skull.CREEPER.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Creeper")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
                case MUSHROOM_COW: {
                    ItemStack itemStack = new ItemBuilder(Skull.MUSHROOM_COW.clone()).setName("§eGerador de Monstros").setLore(Main.getPlugin(Main.class).getConfig().getString("Spawners.Lore").replace("&", "§").replace("%tipo%", "Vaca cogumelo")).toItemStack();
                    itemStack = ItemMetadata.setMetadata(itemStack,"SpawnerType",ko.name().toUpperCase());
                    return itemStack;
                }
            }
        } catch (IllegalArgumentException e){
            return null;
        }
        return null;
    }
}
