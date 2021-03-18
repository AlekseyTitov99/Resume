package com.pagani.market.listener;

import com.pagani.market.Main;
import com.pagani.market.api.BaseHelp;
import com.pagani.market.api.HistoricItem;
import com.pagani.market.api.Item;
import com.pagani.market.api.ItemsAPI;
import com.pagani.market.enums.HistoricEnum;
import com.pagani.market.holders.PrivadoHolder;
import com.pagani.market.holders.VendaHolder;
import com.pagani.market.menu.Mercado;
import com.pagani.market.objeto.User;
import com.pagani.market.sql.AtlasStorage;
import net.minecraft.server.v1_8_R3.IPlayerFileData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;

import static com.pagani.market.menu.Mercado.canSell;

public class GeneralPrivadoHolder implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) throws IOException {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (e.getInventory().getHolder() instanceof PrivadoHolder) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory().getHolder() instanceof PrivadoHolder) {
                PrivadoHolder privadoHolder = (PrivadoHolder) e.getClickedInventory().getHolder();
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.WEB)
                    return;
                if (e.getRawSlot() == 26 || e.getRawSlot() == 18 || e.getRawSlot() == 49) {
                    if (e.getRawSlot() == 18) {
                        int pag = privadoHolder.getPagactual();
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(pag));
                        p.openInventory(privadoHolder.getPags().get(pag - 1));
                        privadoHolder.setPagactual(pag - 1);
                    } else {
                        if (e.getRawSlot() == 49) {
                            Mercado.onLoad(p);
                            return;
                        }
                        int pag = privadoHolder.getPagactual();
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(pag));
                        p.openInventory(privadoHolder.getPags().get(pag + 1));
                        privadoHolder.setPagactual(pag + 1);
                    }
                    return;
                }
                User user = Main.cache2.get(p.getName());
                Iterator<Item> itemIterator = user.getPessoal().iterator();
                if (!user.getPessoal().isEmpty() || !itemIterator.hasNext()) {
                    p.sendMessage("§cParece que este item não está mais disponível.");
                    p.closeInventory();
                    return;
                }
                while (itemIterator.hasNext()){
                    Item item = itemIterator.next();
                    ItemStack itemStack = BaseHelp.fromBase64(item.getItemStack());
                    if (itemStack == null) continue;
                    if (itemStack.equals(e.getCurrentItem())){
                        if (user.getPessoal().contains(item)) {
                            if (p.getInventory().firstEmpty() == -1) {
                                p.closeInventory();
                                p.sendMessage("§cSeu inventário está lotado.");
                                return;
                            }
                            double price = item.getValue();
                            if (Main.economy == null){
                                Main.setupEconomy();
                                p.sendMessage("§cOcorreu um erro, tente novamente.");
                                return;
                            }
                            if (Main.economy.getBalance(p.getName()) >= price) {
                                p.updateInventory();
                                p.sendMessage("§aGerando compra...");
                                if (item.getValue() < 0){
                                    return;
                                }
                                Mercado.loadCompra(p, BaseHelp.fromBase64(item.getItemStack()), item);
                                return;
                            } else {
                                p.closeInventory();
                                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                p.sendMessage("§cVocê precisa de §7" + decimalFormat.format(price) + "§c coins para comprar este item.");
                                return;
                            }
                        } else {
                            p.closeInventory();
                            p.sendMessage("§cParece que este item não está mais disponível.");
                            return;
                        }
                    }
                }
            }
        }
    }
}
