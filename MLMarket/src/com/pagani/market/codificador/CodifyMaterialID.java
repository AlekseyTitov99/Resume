package com.pagani.market.codificador;


import com.pagani.market.enums.CategoryType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CodifyMaterialID {

    public static double toOrganize(int idd, CategoryType s, ItemStack f) {
        if (s == CategoryType.MINERIOS) {
            switch (Material.getMaterial(idd)) {
                case EMERALD_BLOCK: {
                    return 21;
                }
                case DIAMOND_BLOCK: {
                    return 20;
                }
                case GOLD_BLOCK: {
                    return 19;
                }
                case IRON_BLOCK: {
                    return 18;
                }
                case LAPIS_BLOCK: {
                    return 17;
                }
                case REDSTONE_BLOCK: {
                    return 16;
                }
                case COAL_BLOCK: {
                    return 15;
                }
                case EMERALD: {
                    return 14;
                }
                case DIAMOND: {
                    return 13;
                }
                case GOLD_INGOT: {
                    return 12;
                }
                case IRON_INGOT: {
                    return 11;
                }
                case INK_SACK: {
                    return 10;
                }
                case REDSTONE: {
                    return 9;
                }
                case COAL: {
                    return 8;
                }
                case EMERALD_ORE: {
                    return 7;
                }
                case DIAMOND_ORE: {
                    return 6;
                }
                case GOLD_ORE: {
                    return 5;
                }
                case IRON_ORE: {
                    return 4;
                }
                case LAPIS_ORE: {
                    return 3;
                }
                case REDSTONE_ORE: {
                    return 2;
                }
                case COAL_ORE: {
                    return 1;
                }
            }
        }
        //
        if (s == CategoryType.POCOES){
            if (f.getType() == Material.BEACON){
                return 2000000;
            }
            if (f.getType() == Material.GOLDEN_APPLE){
                if (f.getDurability() == 1){
                    return 12000;
                }
                if (f.getDurability() == 0){
                    return 11000;
                }
            }
            if (f.getDurability() == 8233){
                return 8000;
            }
            if (f.getDurability() == 8265){
                return 7000;
            }
            if (f.getDurability() == 8201){
                return 6000;
            }
            if (f.getDurability() == 8226){
                return 5000;
            }
            if (f.getDurability() == 8258){
                return 4000;
            }
            if (f.getDurability() == 8194){
                return 3000;
            }
            if (f.getDurability() == 16425){
                return 2500;
            }
            if (f.getDurability() == 16457){
                return 2000;
            }
            if (f.getDurability() == 16393){
                return 1750;
            }
            if (f.getDurability() == 16418){
                return 1500;
            }
            if (f.getDurability() == 16450){
                return 1250;
            }
            if (f.getDurability() == 16386){
                return 1000;
            }
            return 0;
        }
        if (s == CategoryType.ARMADURAS) {
            Material material = Material.getMaterial(idd);
            if (f.getEnchantments().isEmpty()) {
                if (material.name().toLowerCase().endsWith("_helmet")) {
                    return 4.25 + 200;
                }
                if (material.name().toLowerCase().endsWith("_chestplate")) {
                    return 3.25 + 100;
                }
                if (material.name().toLowerCase().endsWith("_leggings")) {
                    return 2.25 + 50;
                }
                if (material.name().toLowerCase().endsWith("_boots")) {
                    return 1.25 + 25;
                }
            } else {
                Bukkit.getConsoleSender().sendMessage("§cTinha encantamento");
                if (material.name().toLowerCase().endsWith("_helmet")) {
                    if (f.getEnchantments().size() == 1) {
                        return 5.25 + 200;
                    } else {
                        return 4.25 * f.getEnchantments().size() + 200;
                    }
                }
                if (material.name().toLowerCase().endsWith("_chestplate")) {
                    if (f.getEnchantments().size() == 1) {
                        return 4.25 + 100;
                    } else {
                        return 3.25 * f.getEnchantments().size() + 100;
                        // se tiver 2 enchants volta 5,
                    }
                }
                if (material.name().toLowerCase().endsWith("_leggings")) {
                    if (f.getEnchantments().size() == 1) {
                        return 3.25 + 50;
                        // 1.25
                    } else {
                        return 2.25 * f.getEnchantments().size() + 50;
                        // 2.25, se tiver 3 enchants = 7.75
                    }
                }
                if (material.name().toLowerCase().endsWith("_boots")) {
                    if (f.getEnchantments().size() == 1) {
                        return 2.25 + 25;
                        // 1.25
                    } else {
                        return 1.25 * f.getEnchantments().size() + 25;
                        // 2.25, se tiver 3 enchants = 7.75
                    }
                }
            }
        }
        //
        if (s == CategoryType.ARMAS) {
            Material material = Material.getMaterial(idd);
            if (f.getEnchantments().isEmpty()) {
                if (material.name().toLowerCase().endsWith("_sword")) {
                    return 4.25 + 150;
                }
                if (material.name().toLowerCase().endsWith("_axe")) {
                    return 3.25 + 100;
                }
                if (material.name().toLowerCase().endsWith("bow")) {
                    return 2.25 + 50;
                }
            } else {
                Bukkit.getConsoleSender().sendMessage("§cTinha encantamento");
                if (material.name().toLowerCase().endsWith("_sword")) {
                    if (f.getEnchantments().size() == 1) {
                        return 4.25 + 200;
                    } else {
                        return 3.25 * f.getEnchantments().size() + 200;
                    }
                }
                if (material.name().toLowerCase().endsWith("_axe")) {
                    if (f.getEnchantments().size() == 1) {
                        return 3.25 + 100;
                    } else {
                        return 2.25 * f.getEnchantments().size() + 100;
                        // se tiver 2 enchants volta 5,
                    }
                }
                if (material.name().toLowerCase().endsWith("bow")) {
                    if (f.getEnchantments().size() == 1) {
                        return 2 + 25;
                        // 1.25
                    } else {
                        return 2.25 * f.getEnchantments().size() + 25;
                        // 2.25, se tiver 3 enchants = 7.75
                    }
                }
            }
        }
        if (s == CategoryType.SPAWNERS){
            if (f.getType() == Material.SKULL_ITEM){
                return 2000;
            }
            if (f.getType() == Material.MONSTER_EGG && f.getDurability() == 50){
                return 500;
            }
            return 0;
        }
        if (s == CategoryType.FERRAMENTAS){
            Material material = Material.getMaterial(idd);
            if (f.getEnchantments().isEmpty()) {
                if (material.name().toLowerCase().endsWith("_pickaxe")) {
                    return 4.25 + 200;
                }
                if (material.name().toLowerCase().endsWith("_axe")) {
                    return 3.25 + 100;
                }
                if (material.name().toLowerCase().endsWith("_spade")) {
                    return 2.25 + 50;
                }
                if (material.name().toLowerCase().endsWith("_hoe")) {
                    return 1.25 + 25;
                }
            } else {
                if (material.name().toLowerCase().endsWith("_pickaxe")) {
                    if (f.getEnchantments().size() == 1) {
                        return 5.25 + 200;
                    } else {
                        return 4.25 * f.getEnchantments().size() + 200;
                    }
                }
                if (material.name().toLowerCase().endsWith("_axe")) {
                    if (f.getEnchantments().size() == 1) {
                        return 4.25 + 100;
                    } else {
                        return 3.25 * f.getEnchantments().size() + 100;
                        // se tiver 2 enchants volta 5,
                    }
                }
                if (material.name().toLowerCase().endsWith("_spade")) {
                    if (f.getEnchantments().size() == 1) {
                        return 3.25 + 50;
                        // 1.25
                    } else {
                        return 2.25 * f.getEnchantments().size() + 50;
                        // 2.25, se tiver 3 enchants = 7.75
                    }
                }
                if (material.name().toLowerCase().endsWith("_hoe")) {
                    if (f.getEnchantments().size() == 1) {
                        return 2.25 + 25;
                        // 1.25
                    } else {
                        return 1.25 * f.getEnchantments().size() + 25;
                        // 2.25, se tiver 3 enchants = 7.75
                    }
                }
            }
        }
        return 0;
    }
}