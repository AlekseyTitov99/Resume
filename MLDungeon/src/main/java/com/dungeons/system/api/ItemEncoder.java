package com.dungeons.system.api;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.math.BigInteger;

public class ItemEncoder {

    public static ItemStack getItemStackFromString(String string) {
        String[] args = string.split("-");
        String[] variaveis = args[0].split(",");
        int data = Integer.valueOf(variaveis[1]);
        ItemStack item = new ItemBuilder(Material.getMaterial(Integer.valueOf(variaveis[0]))).setDurability((short) data).toItemStack();
        String nomedoitem = variaveis[2].replace("&", "§");
        int quantidade = Integer.valueOf(variaveis[3]);

        if (!nomedoitem.equalsIgnoreCase("null")) {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(nomedoitem);
            item.setItemMeta(itemMeta);
        }
        item.setAmount(quantidade);
        if (!variaveis[4].equalsIgnoreCase("null")) {
            int encantamento1 = Integer.valueOf(variaveis[4]);
            int levelencantamento1 = Integer.valueOf(variaveis[5]);
            item.addUnsafeEnchantment(Enchantment.getById(encantamento1), levelencantamento1);
        }
        if (!variaveis[6].equalsIgnoreCase("null")) {
            int encantamento2 = Integer.valueOf(variaveis[6]);
            int levelencantamento2 = Integer.valueOf(variaveis[7]);
            item.addUnsafeEnchantment(Enchantment.getById(encantamento2), levelencantamento2);
        }
        if (!variaveis[8].equalsIgnoreCase("null")) {
            int encantamento3 = Integer.valueOf(variaveis[8]);
            int levelencantamento3 = Integer.valueOf(variaveis[9]);
            item.addUnsafeEnchantment(Enchantment.getById(encantamento3), levelencantamento3);
        }
        if (!variaveis[10].equalsIgnoreCase("null")) {
            int encantamento4 = Integer.valueOf(variaveis[10]);
            int levelencantamento4 = Integer.valueOf(variaveis[11]);
            item.addUnsafeEnchantment(Enchantment.getById(encantamento4), levelencantamento4);
        }
        return item;
    }

    public static String toBase64(ItemStack item) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        NBTTagList nbtTagListItems = new NBTTagList();
        NBTTagCompound nbtTagCompoundItem = new NBTTagCompound();

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        nmsItem.save(nbtTagCompoundItem);

        nbtTagListItems.add(nbtTagCompoundItem);

        NBTCompressedStreamTools.a(nbtTagCompoundItem, (DataOutput) dataOutput);

        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    /**
     * Item from Base64
     * @param data
     * @return
     * @throws IOException
     */
    public static ItemStack fromBase64(String data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());

        NBTTagCompound nbtTagCompoundRoot = NBTCompressedStreamTools.a(new DataInputStream(inputStream));

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = net.minecraft.server.v1_8_R3.ItemStack.createStack(nbtTagCompoundRoot);
        ItemStack item = (ItemStack) CraftItemStack.asBukkitCopy(nmsItem);

        return item;
    }

}