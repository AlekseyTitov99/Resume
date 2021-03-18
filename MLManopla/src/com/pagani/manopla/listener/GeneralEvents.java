package com.pagani.manopla.listener;

import com.massivecraft.factions.entity.MPlayer;
import com.pagani.manopla.Main;
import com.pagani.manopla.api.JoiaType;
import com.pagani.manopla.api.OpenJoiasMenu;
import com.pagani.manopla.api.getJoia;
import com.pagani.manopla.objeto.ManoplaUser;
import com.pagani.manopla.sql.AtlasStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;

public class GeneralEvents implements Listener {

    @EventHandler
    public void onLoad(PlayerJoinEvent e){
        AtlasStorage.loadUser(e.getPlayer().getName());
    }

    @EventHandler
    public void onUnload(PlayerQuitEvent e){
        AtlasStorage.unLoadUser(e.getPlayer().getName());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onTeleport(PlayerTeleportEvent e){
        if (e.isCancelled()) return;
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
            if (Main.cache.containsKey(e.getPlayer().getName()) && Main.cache.get(e.getPlayer().getName()).isJoia2()){
                if (Main.cache.containsKey(e.getPlayer().getName()) && Main.cache.get(e.getPlayer().getName()).isJoia3()){
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*6,2));
                } else {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*3,2));
                }
            }
        }
    }

    public static void Poder(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, ManoplaUser> stringManoplaUserEntry : Main.cache.entrySet()) {
                    if (stringManoplaUserEntry.getValue().isJoia1()){
                        MPlayer k = MPlayer.get(stringManoplaUserEntry.getKey());
                        if (k.getPowerRounded() < k.getPowerMaxRounded()) {
                            k.setPower((double) (k.getPowerRounded() + 1));
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),0L,20L*120);
    }

    public static void onTT(){
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<String,ManoplaUser>> iterator = Main.cache.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String,ManoplaUser> stringManoplaUserEntry = iterator.next();
                    if (stringManoplaUserEntry.getValue().isJoia6()){
                        Player p = Bukkit.getPlayer(stringManoplaUserEntry.getKey());
                        if (p == null){
                            continue;
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,Integer.MAX_VALUE,1));
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class),0L,20L * 4);
    }

    @EventHandler
    public void applyJoia(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equalsIgnoreCase("Manopla do Infinito")) {
            e.setCancelled(true);
            if (e.getClickedInventory() == p.getInventory()) {
                if (Main.cache.containsKey(p.getName())) {
                    ItemStack k = e.getCurrentItem();
                    if (k != null && k.isSimilar(getJoia.getJoiaItemStack(JoiaType.PODER))) {
                        ManoplaUser manoplaUser = Main.cache.get(p.getName());
                        if (!manoplaUser.isJoia1()) {
                            if (k.getAmount() == 1) {
                                manoplaUser.setJoia1(true);
                                e.setCurrentItem(null);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            } else {
                                manoplaUser.setJoia1(true);
                                k.setAmount(k.getAmount() - 1);
                                e.setCurrentItem(k);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê já possui essa joia na manopla.");
                        }
                    }
                    if (k != null && k.isSimilar(getJoia.getJoiaItemStack(JoiaType.ESPAÇO))) {
                        ManoplaUser manoplaUser = Main.cache.get(p.getName());
                        if (!manoplaUser.isJoia2()) {
                            if (k.getAmount() == 1) {
                                manoplaUser.setJoia2(true);
                                e.setCurrentItem(null);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            } else {
                                manoplaUser.setJoia2(true);
                                k.setAmount(k.getAmount() - 1);
                                e.setCurrentItem(k);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê já possui essa joia na manopla.");
                        }
                    }
                    if (k != null && k.isSimilar(getJoia.getJoiaItemStack(JoiaType.MENTE))) {
                        ManoplaUser manoplaUser = Main.cache.get(p.getName());
                        if (!manoplaUser.isJoia3()) {
                            if (k.getAmount() == 1) {
                                manoplaUser.setJoia3(true);
                                e.setCurrentItem(null);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            } else {
                                manoplaUser.setJoia3(true);
                                k.setAmount(k.getAmount() - 1);
                                e.setCurrentItem(k);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê já possui essa joia na manopla.");
                        }
                    }
                    if (k != null && k.isSimilar(getJoia.getJoiaItemStack(JoiaType.REALIDADE))) {
                        ManoplaUser manoplaUser = Main.cache.get(p.getName());
                        if (!manoplaUser.isJoia4()) {
                            if (k.getAmount() == 1) {
                                manoplaUser.setJoia4(true);
                                e.setCurrentItem(null);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            } else {
                                manoplaUser.setJoia4(true);
                                k.setAmount(k.getAmount() - 1);
                                e.setCurrentItem(k);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê já possui essa joia na manopla.");
                        }
                    }

                    if (k != null && k.isSimilar(getJoia.getJoiaItemStack(JoiaType.TEMPO))) {
                        ManoplaUser manoplaUser = Main.cache.get(p.getName());
                        if (!manoplaUser.isJoia5()) {
                            if (k.getAmount() == 1) {
                                manoplaUser.setJoia5(true);
                                e.setCurrentItem(null);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            } else {
                                manoplaUser.setJoia5(true);
                                k.setAmount(k.getAmount() - 1);
                                e.setCurrentItem(k);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê já possui essa joia na manopla.");
                        }
                    }
                    if (k != null && k.isSimilar(getJoia.getJoiaItemStack(JoiaType.FORÇA))) {
                        ManoplaUser manoplaUser = Main.cache.get(p.getName());
                        if (!manoplaUser.isJoia5()) {
                            if (k.getAmount() == 1) {
                                manoplaUser.setJoia6(true);
                                e.setCurrentItem(null);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            } else {
                                manoplaUser.setJoia6(true);
                                e.setCurrentItem(k);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                AtlasStorage.savePlayer(p.getName(), manoplaUser, true);
                                return;
                            }
                        } else {
                            p.sendMessage("§cVocê já possui essa joia na manopla.");
                        }
                    }
                }
            }
            /**
             * Now its about give the shit back
             */
                if (Main.cache.containsKey(p.getName())) {
                    ManoplaUser user = Main.cache.get(p.getName());
                    if (e.getRawSlot() == 11) {
                        if (user.isJoia1()) {
                            if (!(p.getInventory().firstEmpty() == -1)) {
                                p.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.PODER));
                                user.setJoia1(false);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                            } else {
                                p.sendMessage("§cVocê não tem espaço no inventario.");
                            }
                        } else {
                            p.sendMessage("§cVocê não tem essa joia.");
                        }
                    }
                    if (e.getRawSlot() == 12) {
                        if (user.isJoia2()) {
                            if (!(p.getInventory().firstEmpty() == -1)) {
                                p.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.ESPAÇO));
                                user.setJoia2(false);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                            } else {
                                p.sendMessage("§cVocê não tem espaço no inventario.");
                            }
                        } else {
                            p.sendMessage("§cVocê não tem essa joia.");
                        }
                    }
                    if (e.getRawSlot() == 13) {
                        if (user.isJoia3()) {
                            if (!(p.getInventory().firstEmpty() == -1)) {
                                p.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.MENTE));
                                user.setJoia3(false);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                            } else {
                                p.sendMessage("§cVocê não tem espaço no inventario.");
                            }
                        } else {
                            p.sendMessage("§cVocê não tem essa joia.");
                        }
                    }
                    if (e.getRawSlot() == 14) {
                        if (user.isJoia4()) {
                            if (!(p.getInventory().firstEmpty() == -1)) {
                                p.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.REALIDADE));
                                user.setJoia4(false);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                            } else {
                                p.sendMessage("§cVocê não tem espaço no inventario.");
                            }
                        } else {
                            p.sendMessage("§cVocê não tem essa joia.");
                        }
                    }
                    if (e.getRawSlot() == 15) {
                        if (user.isJoia5()) {
                            if (!(p.getInventory().firstEmpty() == -1)) {
                                p.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.TEMPO));
                                user.setJoia5(false);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                            } else {
                                p.sendMessage("§cVocê não tem espaço no inventário.");
                            }
                    } else {
                            p.sendMessage("§cVocê não tem essa joia.");
                    }
                }
                    if (e.getRawSlot() == 22) {
                        if (user.isJoia6()) {
                            if (!(p.getInventory().firstEmpty() == -1)) {
                                p.getInventory().addItem(getJoia.getJoiaItemStack(JoiaType.FORÇA));
                                user.setJoia6(false);
                                OpenJoiasMenu.GenerateJoiaMenu(p);
                                p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                            } else {
                                p.sendMessage("§cVocê não tem espaço no inventário.");
                            }
                        } else {
                            p.sendMessage("§cVocê não tem essa joia.");
                        }
                    }
            }
        }
    }
}