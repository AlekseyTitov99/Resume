package com.pagani.manopla.api;

import com.pagani.manopla.Main;
import com.pagani.manopla.objeto.ManoplaUser;
import com.pagani.manopla.sql.AtlasStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public class OpenJoiasMenu {

    public static void GenerateJoiaMenu(Player p){
        if (Main.cache.containsKey(p.getName())){
            ManoplaUser manoplaUser = Main.cache.get(p.getName());
            p.sendMessage("§aAbrindo menu de joias...");
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    Inventory inventory = Bukkit.createInventory(null,36,"Manopla do Infinito");
                    if (manoplaUser.isJoia1()){
                        ItemStack itemStack = new ItemBuilder(Material.INK_SACK).setDurability((short) 5).setName("§eJoia do poder").
                                setLore("§7O seu poder será restaurado 5x mais",
                                        "§7rápido que o normal","","§eClique para retira-la da manopla").
                                addEnchant(Enchantment.DURABILITY,1).
                                addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
                        inventory.setItem(11,itemStack);
                    } else {
                        ItemStack itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 2).setName("§eJoia do poder").
                                setLore("§7Você não tem essa joia.").toItemStack();
                        inventory.setItem(11,itemStack);
                    }

                    if (manoplaUser.isJoia2()){
                        ItemStack itemStack = new ItemBuilder(Material.INK_SACK).setDurability((short) 4).setName("§eJoia do espaço").
                                setLore("§7Receba velocidade 3 sempre que",
                                        "§7teletransportar-se com uma pérola do fim.","","§eClique para retira-la da manopla").
                                addEnchant(Enchantment.DURABILITY,1).
                                addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
                        inventory.setItem(12,itemStack);
                    } else {
                        ItemStack itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 11).setName("§eJoia do espaço").
                                setLore("§7Você não tem essa joia.").toItemStack();
                        inventory.setItem(12,itemStack);
                    }

                    if (manoplaUser.isJoia3()){
                        ItemStack itemStack = new ItemBuilder(Material.GLOWSTONE_DUST).setName("§eJoia do mente").
                                setLore("§7Amplifica o poder das outras joias",
                                        "","§7 + Amplificações: ","§7 - Espaço (Duração aumentada para 6s)",
                                        "§7 - Realidade (Multiplicação aumentada para 1.25x)",
                                        "§7 - Tempo (Aumentada para 30s)",
                                        "§7 - Poder (Regeneração 6x mais rápida)","","§eClique para retira-la da manopla").
                                addEnchant(Enchantment.DURABILITY,1).
                                addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
                        inventory.setItem(13,itemStack);
                    } else {
                        ItemStack itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 1).setName("§eJoia da mente").
                                setLore("§7Você não tem essa joia.").toItemStack();
                        inventory.setItem(13,itemStack);
                    }

                    if (manoplaUser.isJoia4()){
                        ItemStack itemStack = new ItemBuilder(Material.REDSTONE).setName("§eJoia do realidade").
                                setLore("§7Todo EXP de habilidades recebido terá",
                                        "§7seu valor aumentado em 20%","","§eClique para retira-la da manopla").
                                addEnchant(Enchantment.DURABILITY,1).
                                addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
                        inventory.setItem(14,itemStack);
                    } else {
                        ItemStack itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14).setName("§eJoia da realidade").
                                setLore("§7Você não tem essa joia.").toItemStack();
                        inventory.setItem(14,itemStack);
                    }

                    if (manoplaUser.isJoia5()){
                        ItemStack itemStack = new ItemBuilder(Material.EMERALD).setName("§eJoia do tempo").
                                setLore("§7Ao usar um Olho de Deus o tempo de duração",
                                        "§7terá um acréscimo de 10s, Totalizando 20s.","","§eClique para retira-la da manopla").
                                addEnchant(Enchantment.DURABILITY,1).
                                addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
                        inventory.setItem(15,itemStack);
                    } else {
                        ItemStack itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 5).setName("§eJoia do tempo").
                                setLore("§7Você não tem essa joia.").toItemStack();

                        inventory.setItem(15,itemStack);
                    }
                    if (manoplaUser.isJoia6()){
                        ItemStack itemStack = new ItemBuilder(Material.SULPHUR).setName("§eJoia da Força").
                                setLore("§7Você receberá efeito de Força II infinito",
                                        "§7caso mante-la na manopla.","","§eClique para retira-la da manopla").
                                addEnchant(Enchantment.DURABILITY,1).
                                addItemFlag(ItemFlag.HIDE_ENCHANTS).toItemStack();
                        inventory.setItem(22,itemStack);
                    } else {
                        ItemStack itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName("§eJoia do tempo").
                                setLore("§7Você não tem essa joia.").toItemStack();
                        inventory.setItem(22,itemStack);
                    }
                    p.openInventory(inventory);
                }
            });
        } else {
            AtlasStorage.loadUser(p.getName());
            return;
        }
    }

}