package com.pagani.market.listener;

import com.pagani.market.Main;
import com.pagani.market.api.BaseHelp;
import com.pagani.market.api.Item;
import com.pagani.market.api.ItemMetadata;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.holders.VendaHolder;
import com.pagani.market.menu.Expired;
import com.pagani.market.menu.Mercado;
import com.pagani.market.objeto.User;
import com.pagani.market.sql.AtlasStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.pagani.market.menu.Mercado.canSell;
import static com.pagani.market.menu.Mercado.openSellsMarket;

public class GeneralVendaHolder implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClickInventory(InventoryClickEvent e) throws IOException {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getHolder() instanceof VendaHolder) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.WEB)
                return;
            if (e.getClickedInventory().getHolder() instanceof VendaHolder) {
                VendaHolder vendaHolder = (VendaHolder) e.getClickedInventory().getHolder();
                if (e.getRawSlot() == 26 || e.getRawSlot() == 18 || e.getRawSlot() == 49) {
                    if (e.getRawSlot() == 18) {
                        int pag = vendaHolder.getPagactual();
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(pag));
                        player.openInventory(vendaHolder.getPags().get(pag - 1));
                        vendaHolder.setPagactual(pag - 1);
                    } else {
                        if (e.getRawSlot() == 49) {
                            Mercado.onLoad(player);
                            return;
                        }
                        int pag = vendaHolder.getPagactual();
                        Bukkit.getConsoleSender().sendMessage(String.valueOf(pag));
                        player.openInventory(vendaHolder.getPags().get(pag + 1));
                        vendaHolder.setPagactual(pag + 1);
                    }
                    return;
                }
                if (vendaHolder.getItems().isEmpty() && e.getCurrentItem().getType() != Material.WEB) {
                    player.sendMessage("§cParece que você não tem mais nenhum item à venda.");
                    openSellsMarket(player);
                    return;
                }
                if (player.getInventory().firstEmpty() == -1) {
                    player.closeInventory();
                    player.sendMessage("§cSeu inventário está lotado.");
                    return;
                }
                if (Main.cache2.containsKey(player.getName())) {
                    User user = Main.cache2.get(e.getWhoClicked().getName());
                    Iterator<Item> itemIterator = user.getVendendo().iterator();
                    while (itemIterator.hasNext()) {
                        Item item = itemIterator.next();
                        ItemStack itemStack = BaseHelp.fromBase64(item.getGetBackItem());
                        if (e.getCurrentItem().equals(itemStack)) {
                            if (user.getVendendo().contains(item) && vendaHolder.getItems().contains(item) || user.getExpirados().contains(item)) {
                                if (user.getExpirados().contains(item)) {
                                    player.closeInventory();
                                    player.sendMessage("§cEste item expirou, abrindo inventário de expirados.");
                                    if (user.getVendendo().contains(item) && user.getExpirados().contains(item)) {
                                        user.getVendendo().remove(item);
                                    }
                                    Expired.openExpiredInv(player);
                                    return;
                                }
                                Bukkit.getConsoleSender().sendMessage(item.toString());
                                itemIterator.remove();
                                e.getInventory().remove(e.getCurrentItem());
                                player.updateInventory();
                                if (item.isVendaprivada()) {
                                    String usuario = item.getVendeupraquem();
                                    if (Main.cache2.containsKey(usuario)) {
                                        User user2 = Main.cache2.get(usuario);
                                        user2.getPessoal().removeIf(s -> s.equals(item));
                                    } else {
                                        AtlasStorage.removeItemFromOtherPessoal(usuario, item);
                                    }
                                    if (!(player.getInventory().firstEmpty() == -1)) {
                                        player.sendMessage("§aItem coletado.");
                                        player.getInventory().addItem(BaseHelp.fromBase64(item.getRealstack()));
                                    } else {
                                        player.getWorld().dropItemNaturally(player.getLocation(), BaseHelp.fromBase64(item.getRealstack()));
                                    }
                                    AtlasStorage.savePlayer(player.getName(), user, true);
                                    openSellsMarket(player);
                                    return;
                                } else {
                                    CategoryType categoryType = canSell(Objects.requireNonNull(BaseHelp.fromBase64(item.getGetBackItem())));
                                    Iterator<Item> itemIterator1 = Main.cache.get(categoryType).getItemsInCategory().stream().filter(s -> s.getSender().equalsIgnoreCase(player.getName()) && ItemMetadata.hasMetadata(BaseHelp.fromBase64(item.getItemStack()), "MercadoLong")
                                            && ItemMetadata.hasMetadata(BaseHelp.fromBase64(s.getItemStack()), "MercadoLong") &&
                                            ItemMetadata.getMetadata(BaseHelp.fromBase64(s.getItemStack()), "MercadoLong").toString().equalsIgnoreCase(ItemMetadata.getMetadata(BaseHelp.fromBase64(item.getItemStack()), "MercadoLong").toString())).iterator();
                                    while (itemIterator1.hasNext()) {
                                        Item item1 = itemIterator1.next();
                                        ItemStack itemStack1 = BaseHelp.fromBase64(item1.getItemStack());
                                        ItemStack itemStack2 = BaseHelp.fromBase64(item.getItemStack());
                                        if (ItemMetadata.hasMetadata(itemStack1, "MercadoLong")) {
                                            String long1 = ItemMetadata.getMetadata(itemStack1, "MercadoLong").toString();
                                            if (ItemMetadata.hasMetadata(itemStack2, "MercadoLong")) {
                                                String long2 = ItemMetadata.getMetadata(itemStack2, "MercadoLong").toString();
                                                if (long1.equalsIgnoreCase(long2)) {
                                                    Bukkit.getConsoleSender().sendMessage("§cRetirou");
                                                    Main.cache.get(categoryType).getItemsInCategory().removeIf(s -> s.equals(item1));
                                                    assert categoryType != null;
                                                    AtlasStorage.savePlayer(player.getName(), user, true);
                                                    AtlasStorage.saveCategory(categoryType.name().toUpperCase(), Main.cache.get(categoryType), true);
                                                    player.getInventory().addItem(BaseHelp.fromBase64(item.getRealstack()));
                                                    player.sendMessage("§aItem coletado.");
                                                    openSellsMarket(player);
                                                    return;
                                                } else {
                                                    continue;
                                                }
                                            }
                                        }
                                    }
                                }
                                return;
                            } else {
                                openSellsMarket(player);
                                player.sendMessage("§cParece que este item já foi comprado ou não está disponível.");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}