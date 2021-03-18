package com.pagani.market.listener;

import com.pagani.market.Main;
import com.pagani.market.api.BaseHelp;
import com.pagani.market.api.Item;
import com.pagani.market.holders.ExpiredHolder;
import com.pagani.market.menu.Expired;
import com.pagani.market.menu.Mercado;
import com.pagani.market.objeto.User;
import com.pagani.market.sql.AtlasStorage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class GeneralGetExpired implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onVoidClick(InventoryClickEvent e) throws IOException {
        if (e.getInventory().getHolder() instanceof ExpiredHolder) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.WEB)
                return;
            ExpiredHolder expiredHolder = (ExpiredHolder) e.getInventory().getHolder();
            User user = Main.cache2.get(e.getWhoClicked().getName());
            Iterator<Item> itemIterator = user.getExpirados().iterator();
            if (e.getRawSlot() == 26) {
                int pagnext = expiredHolder.getPagactual() + 1;
                expiredHolder.setPagactual(pagnext);
                player.openInventory(expiredHolder.getPags().get(pagnext));
                return;
            }
            if (e.getRawSlot() == 49) {
                Mercado.onLoad(player);
                return;
            }
            if (e.getRawSlot() == 18) {
                int pagnext = expiredHolder.getPagactual() - 1;
                expiredHolder.setPagactual(pagnext);
                player.openInventory(expiredHolder.getPags().get(pagnext));
                return;
            }
            while (itemIterator.hasNext()){
                Item item = itemIterator.next();
                if (e.getCurrentItem().equals(BaseHelp.fromBase64(item.getGetBackItem()))) {
                    if (player.getInventory().firstEmpty() == -1) {
                        player.sendMessage("§cInventário lotado.");
                        player.closeInventory();
                        return;
                    }
                    if (user.getExpirados().contains(item)) {
                        itemIterator.remove();
                        if (player.getInventory().firstEmpty() == -1) {
                            player.sendMessage("§cInventário lotado.");
                            player.closeInventory();
                            return;
                        }
                        player.getInventory().addItem(BaseHelp.fromBase64(item.getRealstack()));
                        itemIterator.remove();
                        if (user.getVendendo() == null) user.setVendendo(new LinkedList<>());
                        if (user.getVendendo().contains(item)) {
                            user.getVendendo().removeIf(s -> s.equals(item));
                        }
                        player.sendMessage("§aItem coletado.");
                        AtlasStorage.savePlayer(user.getUser(), user, true);
                        Expired.openExpiredInv(player);
                        return;
                    } else {
                        player.closeInventory();
                        player.sendMessage("§cEste item não está mais disponível.");
                    }
                }
            }
        }
    }


}