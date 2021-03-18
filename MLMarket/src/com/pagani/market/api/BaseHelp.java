package com.pagani.market.api;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.math.BigInteger;

public class BaseHelp {
    /**
     * @param
     * @return
     * @throws IOException
     */

    public static String toBase64(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutput = new DataOutputStream(outputStream);

            NBTTagList nbtTagListItems = new NBTTagList();
            NBTTagCompound nbtTagCompoundItem = new NBTTagCompound();

            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

            nmsItem.save(nbtTagCompoundItem);

            nbtTagListItems.add(nbtTagCompoundItem);

            NBTCompressedStreamTools.a(nbtTagCompoundItem, (DataOutput) dataOutput);

            return new BigInteger(1, outputStream.toByteArray()).toString(32);
        } catch (IOException e){
            return null;
        }
    }

    /**
     * Item from Base64
     *
     * @param data
     * @return
     * @throws IOException
     */

    public static ItemStack fromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());

            NBTTagCompound nbtTagCompoundRoot = NBTCompressedStreamTools.a(new DataInputStream(inputStream));

            net.minecraft.server.v1_8_R3.ItemStack nmsItem = net.minecraft.server.v1_8_R3.ItemStack.createStack(nbtTagCompoundRoot);

            return (ItemStack) CraftItemStack.asBukkitCopy(nmsItem);
        } catch (IOException f){
            return null;
        }
    }

}