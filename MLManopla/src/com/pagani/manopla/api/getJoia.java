package com.pagani.manopla.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class getJoia {

    public static ItemStack getJoiaItemStack(JoiaType e){
        if (e.equals(JoiaType.ESPAÇO)){
            return new ItemBuilder(Material.INK_SACK).setDurability((short) 4).setName("§eJoia do Espaço").
                    setLore("§7Receba velocidade 3 sempre que",
                            "§7teletransportar-se com uma pérola do fim.").
                    addEnchant(Enchantment.DURABILITY,1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        }
        if (e.equals(JoiaType.MENTE)){
            return new ItemBuilder(Material.GLOWSTONE_DUST).setName("§eJoia da Mente").
                    setLore("§7Amplifica o poder das outras joias",
                            "","§7 + Amplificações: ","§7 - Espaço (Duração aumentada para 6s)",
                            "§7 - Realidade (Multiplicação aumentada para 1.25x)",
                            "§7 - Tempo (Aumentada para 30s)",
                            "§7 - Poder (Regeneração 6x mais rápida)").
                    addEnchant(Enchantment.DURABILITY,1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        }
        if (e.equals(JoiaType.PODER)){
            return new ItemBuilder(Material.INK_SACK).setDurability((short) 5).setName("§eJoia do Poder").
                    setLore("§7O seu poder será restaurado 5x mais",
                            "§7rápido que o normal").
                    addEnchant(Enchantment.DURABILITY,1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        }
        if (e.equals(JoiaType.REALIDADE)){
            return new ItemBuilder(Material.REDSTONE).setName("§eJoia da Realidade").
                    setLore("§7Todo EXP de habilidades recebido terá",
                            "§7seu valor aumentado em 20%").
                    addEnchant(Enchantment.DURABILITY,1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        }
        if (e.equals(JoiaType.TEMPO)){
            return new ItemBuilder(Material.EMERALD).setName("§eJoia do Tempo").
                    setLore("§7Ao usar um Olho de Deus o tempo de duração",
                            "§7terá um acréscimo de 10s, Totalizando 20s.").
                    addEnchant(Enchantment.DURABILITY,1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        }
        if (e.equals(JoiaType.FORÇA)){
            return  new ItemBuilder(Material.SULPHUR).setName("§eJoia da Força").
                    setLore("§7Você receberá efeito de Força II infinito",
                            "§7caso mante-la na manopla.","","§eClique para retira-la da manopla").
                    addEnchant(Enchantment.DURABILITY,1).
                    addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
        }
        return null;
    }

}