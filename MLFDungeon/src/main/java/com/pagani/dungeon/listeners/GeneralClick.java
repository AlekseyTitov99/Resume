package com.pagani.dungeon.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.pagani.dungeon.Main;
import com.pagani.dungeon.api.CorreioCore;
import com.pagani.dungeon.api.ScrollerInventory;
import com.pagani.dungeon.commands.DungeonCommand;
import com.pagani.dungeon.mysql.AtlasSQL;
import com.pagani.dungeon.mysql.AtlasSQLRunnable;
import com.pagani.dungeon.mysql.AtlasStorage;
import com.pagani.dungeon.objetos.Cargo;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.objetos.Party;
import com.pagani.dungeon.util.ItemEncoder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import static com.pagani.dungeon.mysql.AtlasStorage.mySQLConnection;

public class GeneralClick implements Listener {


    public static void enviarPlayer(Player p,String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        try {
            out.writeUTF("DungeonCom");
            out.writeUTF(server);
        } catch (Exception e) {
        }
        p.sendPluginMessage(Main.getPlugin(Main.class), "DungeonMessage", out.toByteArray());
    }

    @EventHandler
    public void collectItem(InventoryClickEvent event) {
        if(event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) {
            return;
        }
        if(event.getInventory().getName().contains("Correio")) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
                    if (event.getCurrentItem() != null) {
                        Player p = (Player) event.getWhoClicked();
                        if (event.getClickedInventory().equals(p.getInventory())) {
                            event.setCancelled(true);
                            return;
                        }
                        if (event.getCurrentItem().getType().equals(Material.ARROW)) {
                            if (event.getCurrentItem().hasItemMeta()) {
                                if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                                    if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§aVoltar")) {
                                        event.setCancelled(true);
                                        return;
                                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Pagina")) {
                                        event.setCancelled(true);
                                        return;
                                    }
                                }
                            }
                        }
                        if (event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR) {
                            if (event.getWhoClicked().getInventory().firstEmpty() == -1){
                                event.setCancelled(true);
                                event.getWhoClicked().closeInventory();
                                event.getWhoClicked().sendMessage("§cInventário lotado.");
                                return;
                            }
                            try {
                                event.setCancelled(true);
                                String itemstack_base64 = ItemEncoder.toBase64(event.getCurrentItem());
                                Main.cache.get(p.getName()).getCache().remove(itemstack_base64);
                                p.sendMessage("§aItem coletado com sucesso!");
                                p.getInventory().addItem(event.getCurrentItem());
                                AtlasStorage.savePlayer(event.getWhoClicked().getName(),Main.cache.get(event.getWhoClicked().getName()),true);
                                if (Main.cache.get(p.getName()).getCache().size() == 0) {
                                    Main.cache.get(p.getName()).getCache().remove(itemstack_base64);
                                    p.closeInventory();
                                } else {
                                    ArrayList<ItemStack> arrayList = new ArrayList<>();
                                    Main.cache.get(p.getName()).getCache().forEach(i -> {
                                        try {
                                            arrayList.add(CorreioCore.fromBase64(i));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    ScrollerInventory si = new ScrollerInventory(arrayList, "Correio", p,true,p.getName());
                                }
                            } catch (IOException e) {
                                p.closeInventory();
                                p.sendMessage("§cAguarde alguns segundos e tente novamente.");
                            }
                        }
                    }
                }
            }

    @EventHandler
    public void onClick3(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            Player p = (Player)event.getWhoClicked();
            if(event.getInventory().getName().contains("Correio")) {
                if(ScrollerInventory.users2.containsKey(p.getName())) {
                    ScrollerInventory inv = ScrollerInventory.users2.get(p.getName());
                    if(event.getSlot() == 26) {
                        if(inv.currpage >= inv.pages2.size()-1){
                            return;
                        }else{
                            //Next page exists, flip the page
                            inv.currpage += 1;
                            p.openInventory(inv.pages2.get(inv.currpage));
                        }
                    }
                    if(event.getSlot() == 18) {
                        event.setCancelled(true);
                        //If the page number is more than 0 (So a previous page exists)
                        if(inv.currpage > 0){
                            //Flip to previous page
                            inv.currpage -= 1;
                            p.openInventory(inv.pages2.get(inv.currpage));
                        }
                    }
                }
            }
        }
    }

    /*@EventHandler
    public void onClickCorreio(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (e.getInventory().getHolder() instanceof InvHolder){
            e.setCancelled(true);
            InvHolder invHolder = (InvHolder) e.getInventory().getHolder();
            if (e.getCurrentItem().getType() == null || e.getCurrentItem().getType() == Material.AIR) return;
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getType() == Material.ARROW) {
                if (e.getRawSlot() == 45) {
                    if (invHolder.getInventories().indexOf(e.getInventory()) > 0) {
                        e.getWhoClicked().openInventory(invHolder.getInventories().get(invHolder.getInventories().indexOf(e.getInventory()) - 1));
                    }
                }
                if (e.getRawSlot() == 53) {
                    if (invHolder.getInventories().indexOf(e.getInventory()) < invHolder.getInventories().size()) {
                        e.getWhoClicked().openInventory(invHolder.getInventories().get(invHolder.getInventories().indexOf(e.getInventory()) + 1));
                    }
                }
                return;
            }
            try {
                if (Main.cache.containsKey(player.getName())){
                    if (player.getInventory().firstEmpty() == -1){
                        player.closeInventory();
                        player.sendMessage("§cInventário lotado.");
                        return;
                    }
                    String string = CorreioCore.toBase64(e.getCurrentItem());
                    player.getInventory().addItem(e.getCurrentItem());
                    player.updateInventory();
                    DungeonUser dungeonUser = Main.cache.get(player.getName());
                    dungeonUser.getCache().remove(string);
                    e.setCurrentItem(null);
                    CorreioCore.openCorreio(player,true,invHolder.getInventories().indexOf(e.getClickedInventory()));
                    player.sendMessage("§aColetado com sucesso.");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }*/

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        Player player = (Player) e.getWhoClicked();
        if (player.getOpenInventory().getTitle().equalsIgnoreCase("Dungeon")) {
            e.setCancelled(true);
            if (Main.cache.containsKey(player.getName())) {
                DungeonUser dungeonUser = Main.cache.get(player.getName());
                if (e.getRawSlot() == 12) {
                    if (dungeonUser.getTickets() > 0) {
                        if (dungeonUser.hasParty()) {
                            Party party = dungeonUser.getParty();
                            if (party == null) {
                                player.sendMessage("§cVocê não tem party.");
                                return;
                            }
                            if (party.getUsers().isEmpty()){
                                party.getUsers().put(e.getWhoClicked().getName(),Cargo.DONO);
                                player.sendMessage("§cOcorreu um erro :3");
                                return;
                            }
                            if (party.getUsers().get(player.getName()) != Cargo.DONO){
                                player.sendMessage("§cSó o dono da party pode iniciar a dungeon.");
                                return;
                            }
                            for (Map.Entry<String, Cargo> hash : party.getUsers().entrySet()) {
                                if (Bukkit.getPlayer(hash.getKey()) == null) {
                                    player.sendMessage("§cO jogador " + hash.getKey() + "§c está offline, e não podemos leva-los a dungeon.");
                                    return;
                                }
                                if (Main.cache.containsKey(hash.getKey())){
                                    DungeonUser dungeonUser1 = Main.cache.get(hash.getKey());
                                    if (dungeonUser1.getTickets() < 1){
                                        player.sendMessage("§cO jogador " + hash.getKey() + "§c precisa ter tickets de entrada.");
                                        player.closeInventory();
                                        return;
                                    }
                                }
                            }
                            mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(), new AtlasSQL("SELECT * FROM `partys` WHERE `jogador` = ?", "number"), (result)-> {
                                DungeonUser consumerPlayer = null;
                                if (result.next()) {
                                    int sala = Integer.parseInt(result.getString("json"));
                                    if (sala >= 10){
                                        player.sendMessage("§cDesculpe, todas nossas salas estão lotadas! §c" + "10" + "/10");
                                        player.closeInventory();
                                        return;
                                    }
                                    for (Map.Entry<String, Cargo> stringCargoEntry : party.getUsers().entrySet()) {
                                        Bukkit.getPlayer(stringCargoEntry.getKey()).sendMessage("§aEnviando para dungeon -§8Sala-" + sala++);
                                    }
                                    try {
                                        Socket client = new Socket("147.135.88.239",29545);
                                        OutputStream writer = client.getOutputStream();
                                        DataOutputStream dout = new DataOutputStream(writer);
                                        String encoded = AtlasStorage.encode(party);
                                        dout.writeUTF(encoded);
                                        //Flushing the Writer to actually send the Data
                                        dout.close();
                                        writer.close();
                                        client.close();
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                for (Map.Entry<String, Cargo> stringCargoEntry : party.getUsers().entrySet()) {
                                                    enviarPlayer(Bukkit.getPlayer(stringCargoEntry.getKey()),stringCargoEntry.getKey() + "/2");
                                                }
                                            }
                                        }.runTask(Main.getPlugin(Main.class));
                                    } catch (IOException ef) {
                                        ef.printStackTrace();
                                        player.sendMessage("§cOcorreu um erro ao enviar para a dungeon, caso acredite que seja um bug, contate um staffer!");
                                        return;
                                    }
                                } else {
                                    player.sendMessage("§cOcorreu um erro ao enviar para a dungeon, caso acredite que seja um bug, contate um staffer!");
                                    return;
                                }
                            }),true);
                            return;
                        } else {
                            player.sendMessage("§cVocê precisa de uma party.");
                            return;
                        }
                    } else {
                        if (Main.econ.getBalance(player) > 250000){
                            Main.econ
                                    .withdrawPlayer(player,250000);
                            player.sendMessage("§aVocê comprou 2 tickets com sucesso.");
                            dungeonUser.setTickets(2);
                            Inventory inventory = Bukkit.createInventory(null,27,"Dungeon");
                            inventory.setItem(12, DungeonCommand.getitemdungeon(dungeonUser));
                            player.openInventory(inventory);
                            return;
                        } else {
                            player.sendMessage("§cVocê precisa de 250.000 Coins.");
                            return;
                        }
                    }
                }
            }
        }
    }
}