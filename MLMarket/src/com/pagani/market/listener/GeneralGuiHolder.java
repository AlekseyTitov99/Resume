package com.pagani.market.listener;

import com.pagani.market.Main;
import com.pagani.market.api.*;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.enums.HistoricEnum;
import com.pagani.market.holders.GuiHolder;
import com.pagani.market.holders.VendaHolder;
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
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Objects;

import static com.pagani.market.menu.Mercado.canSell;

public class GeneralGuiHolder implements Listener {

    public static Integer getRepairCost(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack itemNms = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item);
        if (itemNms.hasTag()) {
            if (itemNms.getTag().hasKeyOfType("RepairCost", 3)) {
                return itemNms.getTag().getInt("RepairCost");
            } else return null;
        } else return null;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick23(InventoryClickEvent e) throws IOException {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (e.getInventory().getHolder() instanceof GuiHolder) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.WEB || e.getRawSlot() == 30 || e.getRawSlot() == 13)
                return;
            if (e.getClickedInventory().getHolder() instanceof GuiHolder) {
                GuiHolder guiHolder = (GuiHolder) e.getClickedInventory().getHolder();
                Item item = guiHolder.getItem();
                if (e.getRawSlot() == 31) {
                    CategoryType categoryType = Mercado.canSell(Objects.requireNonNull(BaseHelp.fromBase64(item.getRealstack())));
                    if (item.isVendaprivada()) {
                        User user = Main.cache2.get(player.getName());
                        if (user.getPessoal().contains(item)) {
                            if (Main.cache2.containsKey(item.getSender())) {
                                if (item.getValue() < 0){
                                    e.getWhoClicked().closeInventory();
                                    return;
                                }
                                Main.cache2.get(item.getSender()).getVendendo().removeIf(s -> s.equals(item));
                                player.getInventory().addItem(BaseHelp.fromBase64(item.getRealstack()));
                                Player p = Bukkit.getPlayerExact(item.getSender());
                                if (p != null) {
                                    p.sendMessage("§aO Jogador " + e.getWhoClicked().getName() + " comprou um item seu no mercado!");
                                }
                                user.getPessoal().remove(item);
                                Main.economy.withdrawPlayer(player.getName(), item.getValue());
                                Main.economy.depositPlayer(item.getSender(), item.getValue());
                                HistoricItem historicItem = new HistoricItem(ItemsAPI.getHistoricItem(item, player.getName(), HistoricEnum.COMPRA), HistoricEnum.COMPRA, canSell(Objects.requireNonNull(BaseHelp.fromBase64(item.getRealstack()))));
                                Main.cache.get(canSell(Objects.requireNonNull(BaseHelp.fromBase64(item.getRealstack())))).getHistoricItems().add(historicItem);
                                player.sendMessage("§aYay, item comprado.");
                                if (categoryType == null) return;
                                Category category = Main.cache.get(categoryType);
                                InvManager.openCategory(player,category);
                                return;
                            } else {
                                /**
                                 * Vendedor offline
                                 */
                                AtlasStorage.RemoveItemFromPessoalVenda(item.getSender(), item);
                                Main.economy.withdrawPlayer(player.getName(), item.getValue());
                                Main.economy.depositPlayer(item.getSender(), item.getValue());
                                HistoricItem historicItem = new HistoricItem(ItemsAPI.getHistoricItem(item, player.getName(), HistoricEnum.COMPRA), HistoricEnum.COMPRA, canSell(BaseHelp.fromBase64(item.getRealstack())));
                                Main.cache.get(canSell(Objects.requireNonNull(BaseHelp.fromBase64(item.getRealstack())))).getHistoricItems().add(historicItem);
                                if (user.getPessoal() == null) user.setPessoal(new LinkedList<>());
                                user.getPessoal().remove(item);
                                player.sendMessage("§aYay, item comprado.");
                                ItemStack itemStack = BaseHelp.fromBase64(item.getRealstack());
                                player.getInventory().addItem(itemStack);
                                player.closeInventory();
                                return;
                            }
                        } else {
                            player.sendMessage("§cParece que este item não está mais disponível.");
                            player.closeInventory();
                        }
                        return;
                    }
                    if (Main.cache.get(categoryType).getItemsInCategory().contains(item) && !item.isVendaprivada()) {
                        if (player.getInventory().firstEmpty() == -1) {
                            player.closeInventory();
                            player.sendMessage("§cSeu inventário está lotado.");
                            return;
                        }
                        if (item.getValue() < 0){
                            e.getWhoClicked().closeInventory();
                            return;
                        }
                        if (Main.economy.getBalance(player.getName()) >= item.getValue()) {
                            if (!item.isVendaprivada()) {
                                HistoricItem historicItem = null;
                                historicItem = new HistoricItem(ItemsAPI.getHistoricItem(item, player.getName(), HistoricEnum.COMPRA), HistoricEnum.COMPRA, canSell(Objects.requireNonNull(BaseHelp.fromBase64(item.getRealstack()))));
                                if (Main.cache2.containsKey(item.getSender())) {
                                    User user = Main.cache2.get(item.getSender());
                                    if (user.getVendendo() == null) user.setVendendo(new LinkedList<>());
                                    user.getVendendo().removeIf(s ->s.equals(item));
                                    AtlasStorage.savePlayer(item.getSender(), user, true);
                                    Player player1 = Bukkit.getPlayerExact(item.getSender());
                                    player1.sendMessage("§aSeu item no mercado foi comprado.");
                                } else {
                                    AtlasStorage.RemoveItemFromPessoalVenda(item.getSender(), item);
                                }
                                Main.cache.get(categoryType).getHistoricItems().add(historicItem);
                                Main.cache.get(categoryType).getItemsInCategory().remove(item);
                                if (Main.cache.get(categoryType).getItemsInCategory().contains(item)) {
                                    Main.cache.get(categoryType).getItemsInCategory().removeIf(s-> s.equals(item));
                                }
                                player.getInventory().addItem(BaseHelp.fromBase64(item.getRealstack()));
                                player.sendMessage("§aYay, item comprado.");
                                player.closeInventory();
                                Main.economy.withdrawPlayer(player.getName(), item.getValue());
                                Main.economy.depositPlayer(item.getSender(), item.getValue());
                                assert categoryType != null;
                                AtlasStorage.saveCategory(categoryType.name().toUpperCase(), Main.cache.get(categoryType), true);
                                return;
                            }
                        } else {
                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            player.sendMessage("§cVocê precisa de §7" + decimalFormat.format(item.getValue()) + "§c coins para realizar esta operação.");
                            player.closeInventory();
                            return;
                        }
                    } else {
                        player.sendMessage("§cParece que este item não está disponível.");
                        player.closeInventory();
                        return;
                    }
                }
                if (e.getRawSlot() == 32) {
                    player.sendMessage("§cCancelado com sucesso.");
                    player.closeInventory();
                    return;
                }
            }
        }
    }
}