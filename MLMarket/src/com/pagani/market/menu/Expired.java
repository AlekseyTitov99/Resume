package com.pagani.market.menu;

import com.pagani.market.Main;
import com.pagani.market.api.BaseHelp;
import com.pagani.market.api.Item;
import com.pagani.market.api.ItemBuilder;
import com.pagani.market.holders.ExpiredHolder;
import com.pagani.market.objeto.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.LinkedList;

import static com.pagani.market.menu.Mercado.getBackFlecha;

public class Expired {

    public static void openExpiredInv(Player p) throws IOException {
        if (Main.cache2.containsKey(p.getName())) {
            User user = Main.cache2.get(p.getName());
            if (user.getExpirados() == null){
                user.setExpirados(new LinkedList<>());
            }
            if (user.getExpirados().isEmpty()) {
                ExpiredHolder vendaHolder = new ExpiredHolder();
                vendaHolder.setItems(user.getExpirados());
                vendaHolder.setPagactual(0);
                Inventory inventory = Bukkit.createInventory(vendaHolder, 54, "Itens Expirados");
                inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cVázio").setLore("§7Você não tem nenhuma venda :(").toItemStack());
                inventory.setItem(49, getBackFlecha());
                vendaHolder.setPags(new LinkedList<>());
                vendaHolder.getPags().add(inventory);
                p.openInventory(inventory);
                return;
            }
            Inventory inventory = null;
            ExpiredHolder vendaHolder = new ExpiredHolder();
            vendaHolder.setItems(user.getExpirados());
            vendaHolder.setPags(new LinkedList<>());
            int slot = 10;
            int pag = 1;
            int lastPag = (int) Math.ceil(user.getExpirados().size() / 21.0);
            vendaHolder.setPagactual(pag - 1);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(lastPag));
            for (Item item : user.getExpirados()) {
                if (slot == 10) {
                    inventory = Bukkit.createInventory(vendaHolder, 54, "Itens Expirados");
                    if (pag == 1) {
                        vendaHolder.setInventory(inventory);
                    }
                    if (pag > 1) {
                        inventory.setItem(18, new ItemBuilder(Material.ARROW).setName("§aPágina " + (pag - 1)).setLore("§7Clique para voltar", "§7de página.").toItemStack());
                    }
                    if (pag++ != lastPag) {
                        inventory.setItem(26, new ItemBuilder(Material.ARROW).setName("§aPágina " + pag).setLore("§7Clique para avançar", "§7de página.").toItemStack());
                    }
                    inventory.setItem(49, getBackFlecha());
                    vendaHolder.getPags().add(inventory);
                }
                inventory.setItem(slot, BaseHelp.fromBase64(item.getGetBackItem()));
                slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
            }
            p.openInventory(vendaHolder.getInventory());
        }
    }

}