package com.pagani.dungeon.api;

import com.pagani.dungeon.Main;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class CorreioCore {

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
     *
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

    public static void openCorreio(Player p, boolean reopen, int invnumber) {
        if (Main.cache.containsKey(p.getName())) {
            if (reopen) {
                DungeonUser dungeonUser = Main.cache.get(p.getName());
                Inventory inventory = null;
                if (!dungeonUser.getCache().isEmpty()) {
                    InvHolder invHolder = new InvHolder();
                    invHolder.setInventories(new ArrayList<>());
                    int slot = 10;
                    int pag = 1;
                    boolean first = true;
                    final int lastPag = (int)Math.ceil(dungeonUser.getCache().size() / 28.0);
                    for (String s : dungeonUser.getCache()) {
                        if (slot == 10) {
                            inventory = Bukkit.createInventory(invHolder, 54, "Correio");
                            if (first){
                                invHolder.setInventory(inventory);
                                first = false;
                            }
                            if (pag > 1) {
                                inventory.setItem(45, new ItemBuilder(Material.ARROW).setName("§aP\u00e1gina " + (pag - 1)).setLore(new String[] { "§7Clique para voltar", "§7de p\u00e1gina." }).toItemStack());
                            }
                            if (pag++ != lastPag) {
                                inventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§aP\u00e1gina " + pag).setLore(new String[] { "§7Clique para avan\u00e7ar", "§7de p\u00e1gina." }).toItemStack());
                            }
                            invHolder.getInventories().add(inventory);
                        }
                        try {
                            inventory.setItem(slot, fromBase64(s));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
                    }
                    if (invHolder.getInventories().size()-1 >= invnumber) {
                        if (Arrays.stream(invHolder.getInventories().get(invnumber).getContents()).filter(Objects::nonNull).filter(i -> i != null && i.getType() != Material.AIR || i.getType() != Material.ARROW).collect(Collectors.toSet()).size() == 0) {
                            int relaly = invnumber - 1;
                            p.openInventory(invHolder.getInventories().get(relaly));
                            return;
                        }
                        if (invHolder.getInventories().size() >= invnumber) {
                            p.openInventory(invHolder.getInventories().get(invnumber));
                            return;
                        }
                    } else {
                        int relaly = invnumber - 1;
                        p.openInventory(invHolder.getInventories().get(relaly));
                    }
                }
                return;
            } else {
                DungeonUser dungeonUser = Main.cache.get(p.getName());
                Inventory inventory = null;
                if (!dungeonUser.getCache().isEmpty()) {
                    InvHolder invHolder = new InvHolder();
                    invHolder.setInventories(new ArrayList<>());
                    int slot = 10;
                    final int lastPag = (int)Math.ceil(dungeonUser.getCache().size() / 28.0);
                    int pag = 1;
                    boolean first = true;
                    for (String s : dungeonUser.getCache()) {
                        if (slot == 10) {
                            inventory = Bukkit.createInventory(invHolder, 54, "Correio");
                            if (first){
                                invHolder.setInventory(inventory);
                                first = false;
                            }
                            if (pag > 1) {
                                inventory.setItem(45, new ItemBuilder(Material.ARROW).setName("§aP\u00e1gina " + (pag - 1)).setLore(new String[] { "§7Clique para voltar", "§7de p\u00e1gina." }).toItemStack());
                            }
                            if (pag++ != lastPag) {
                                inventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§aP\u00e1gina " + pag).setLore(new String[] { "§7Clique para avan\u00e7ar", "§7de p\u00e1gina." }).toItemStack());
                            }
                            invHolder.getInventories().add(inventory);
                        }
                        try {
                            inventory.setItem(slot, fromBase64(s));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
                    }
                    p.openInventory(invHolder.getInventory());
                }
            }
        } else {

            return;
        }
    }
}