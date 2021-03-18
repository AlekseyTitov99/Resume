package com.pagani.market.listener;

import com.pagani.market.Main;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.holders.HistoricHolder;
import com.pagani.market.menu.Mercado;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class GeneralHistoric implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onClickNumber3(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (e.getInventory().getHolder() instanceof HistoricHolder) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);
            HistoricHolder historicholder = (HistoricHolder) e.getInventory().getHolder();
            if (e.getRawSlot() == 26 || e.getRawSlot() == 18 || e.getRawSlot() == 49 || e.getRawSlot() == 48) {
                if (e.getRawSlot() == 18) {
                    int pag = historicholder.getPagactual();
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(pag));
                    player.openInventory(historicholder.getPags().get(pag - 1));
                    historicholder.setPagactual(pag - 1);
                    return;
                } else {
                    if (e.getRawSlot() == 48){
                        historicholder.getPags().clear();
                        historicholder.getHistoricItems().clear();
                        Mercado.onLoad(player);
                        return;
                    }
                    if (e.getRawSlot() == 49) {
                        if (historicholder.getCategoryType() == null){
                            Mercado.organizeHistoric(historicholder,player, CategoryType.ARMAS);
                            historicholder.setCategoryType(CategoryType.ARMAS);
                        } else {
                            if (historicholder.getCategoryType() == CategoryType.ARMAS){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.ARMADURAS);
                                historicholder.setCategoryType(CategoryType.ARMADURAS);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.ARMADURAS){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.FERRAMENTAS);
                                historicholder.setCategoryType(CategoryType.FERRAMENTAS);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.FERRAMENTAS){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.MINERIOS);
                                historicholder.setCategoryType(CategoryType.MINERIOS);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.MINERIOS){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.REDSTONE);
                                historicholder.setCategoryType(CategoryType.REDSTONE);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.REDSTONE){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.ESPECIAIS);
                                historicholder.setCategoryType(CategoryType.ESPECIAIS);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.ESPECIAIS){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.LIVROS);
                                historicholder.setCategoryType(CategoryType.LIVROS);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.LIVROS){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.POCOES);
                                historicholder.setCategoryType(CategoryType.POCOES);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.POCOES){
                                Mercado.organizeHistoric(historicholder,player, CategoryType.PROTECAO);
                                historicholder.setCategoryType(CategoryType.PROTECAO);
                                return;
                            }
                            if (historicholder.getCategoryType() == CategoryType.PROTECAO){
                                historicholder.getPags().clear();
                                historicholder.getHistoricItems().clear();
                                historicholder = null;
                                try {
                                    Mercado.openHistoricInventory(player);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                return;
                            }
                        }
                        return;
                    }
                    int pag = historicholder.getPagactual();
                    Bukkit.getConsoleSender().sendMessage(String.valueOf(pag));
                    player.openInventory(historicholder.getPags().get(pag + 1));
                    historicholder.setPagactual(pag + 1);
                }
            }
        }
    }

}