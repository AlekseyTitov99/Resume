package br.jotaentity.geradores.listeners;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objetos.FactionGeradores;
import br.jotaentity.geradores.utils.EntityName;
import br.jotaentity.geradores.utils.GuiHolder;
import br.jotaentity.geradores.utils.Heads;
import br.jotaentity.geradores.utils.ItemMetadata;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("all")
public class InventoryClick implements Listener {
    public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        MPlayer k = MPlayer.get(e.getPlayer());
        if (Main.cache.containsValue(e.getPlayer())) {
            Main.cache.remove(k.getFaction());
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof GuiHolder) {
            final Player p = (Player) e.getPlayer();
            final MPlayer mp = MPlayer.get((Object) p);
            if (Main.cache.containsValue(p)) {
                Main.cache.remove(mp.getFaction());
            }
            if (mp.hasFaction()) {
                final Faction fa = mp.getFaction();
                final FactionGeradores fac = new FactionGeradores(fa.getId());
                fac.setAbriuPorUltimo(null);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(final InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GuiHolder) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            final Player p = (Player) e.getWhoClicked();
            final MPlayer mplayer = MPlayer.get((Object) p);
            if (mplayer.hasFaction()) {
                final int slot = e.getRawSlot();
                final GuiHolder holder = (GuiHolder) e.getInventory().getHolder();
                final int id = holder.getId();
                final Faction fac = mplayer.getFaction();
                final FactionGeradores api = new FactionGeradores(fac.getId());
                if (mplayer.getRole() == Rel.OFFICER || mplayer.getRole() == Rel.LEADER || mplayer.getRole() == Rel.SUBLIDER) {
                    if (id == 1) {
                        if (e.getInventory().getSize() <= slot) {
                            final ItemStack item = e.getCurrentItem();
                            if (ItemMetadata.hasMetadata(item, "SpawnerType")) {
                                if (item == null || item.getType() != Material.MOB_SPAWNER) {
                                    return;
                                }
                                final boolean all = e.getClick().isShiftClick() || item.getAmount() == 1;
                                final int amount = all ? item.getAmount() : 1;
                                EntityType entity = EntityType.valueOf(ItemMetadata.getMetadata(item, "SpawnerType").toString());
                                if (entity == null) {
                                    return;
                                }
                                switch (entity) {
                                    case BLAZE: {
                                        api.setQuantiaBlaze(api.getQuantiaBlaze() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case CAVE_SPIDER: {
                                        api.setQuantiaCave_Spider(api.getQuantiaCave_Spider() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case CHICKEN: {
                                        api.setQuantiaChicken(api.getQuantiaChicken() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case COW: {
                                        api.setQuantiaCow(api.getQuantiaCow() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case CREEPER: {
                                        api.setQuantiaCreeper(api.getQuantiaCreeper() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case ENDERMAN: {
                                        api.setQuantiaEnderman(api.getQuantiaEnderman() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case IRON_GOLEM: {
                                        api.setQuantiaIron_Golem(api.getQuantiaIron_Golem() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case MAGMA_CUBE: {
                                        api.setQuantiaMagma_Cube(api.getQuantiaMagma_Cube() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case PIG: {
                                        api.setQuantiaPig(api.getQuantiaPig() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case PIG_ZOMBIE: {
                                        api.setQuantiaPig_Zombie(api.getQuantiaPig_Zombie() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SHEEP: {
                                        api.setQuantiaSheep(api.getQuantiaSheep() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SKELETON: {
                                        api.setQuantiaSkeleton(api.getQuantiaSkeleton() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SLIME: {
                                        api.setQuantiaSlime(api.getQuantiaSlime() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SPIDER: {
                                        api.setQuantiaSpider(api.getQuantiaSpider() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case WITHER: {
                                        api.setQuantiaWither(api.getQuantiaWither() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case ZOMBIE: {
                                        api.setQuantiaZombie(api.getQuantiaZombie() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case MUSHROOM_COW: {
                                        api.setQuantiaMush(api.getQuantiaMushroom() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case WITCH: {
                                        api.setQuantiaBruxa(api.getQuantiaBruxa() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case GHAST: {
                                        api.setQuantiaGhast(api.getQuantiaGhast() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case HORSE: {
                                        api.setQuantiaCavalo(api.getQuantiaCavalo() + amount);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + amount + " " + EntityName.valueOf(entity).getName();
                                        api.addLog(message);
                                        if (all) {
                                            e.setCurrentItem(new ItemStack(Material.AIR));
                                        } else {
                                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                                        }
                                        final String entityName = EntityName.valueOf(entity).getName().toLowerCase();
                                        final String msg = (amount == 1) ? (" gerador de " + entityName + " armazenado.") : (" geradores de " + entityName + " armazenados.");
                                        p.sendMessage("§e" + amount + msg);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                }
                                return;
                            }
                        } else {
                            if (slot == 10) {
                                api.setAbriuPorUltimo(null);
                                p.closeInventory();
                                return;
                            }
                            if (e.getSlot() == 13) {
                                final GuiHolder gui = new GuiHolder(2, fac.getId());
                                final Inventory inv = Bukkit.createInventory((InventoryHolder) gui, 54, "[" + fac.getTag() + "] Gerenciar");

                                ItemStack map = new ItemStack(Material.MAP);
                                ItemMeta itemMeta = map.getItemMeta();
                                itemMeta.setDisplayName("§aArmazenamento");
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add("§eAqui você pode armazenar e retirar");
                                lore.add("§eos geradores armazenados.");
                                itemMeta.setLore(lore);
                                map.setItemMeta(itemMeta);
                                inv.setItem(11, map);
                                ItemStack up = Heads.UP;
                                ItemMeta upa = up.getItemMeta();
                                upa.setDisplayName("§2Depositar todos");
                                upa.setLore((List) Arrays.asList("§eClique para armazenar todos os", "§egeradores que estão no" + "§eseu inventário."));
                                up.setItemMeta(upa);
                                inv.setItem(20, up);
                                final ItemStack down = Heads.DOWN;
                                final ItemMeta ddo = down.getItemMeta();
                                ddo.setDisplayName("§2Coletar todos");
                                ddo.setLore((List) Arrays.asList("§eClique para coletar todos", "§eos geradores que estão depositados", "§epara seu inventário."));
                                down.setItemMeta(ddo);
                                inv.setItem(29, down);
                                final ItemStack flecha = new ItemStack(Material.ARROW);
                                final ItemMeta ffa = flecha.getItemMeta();
                                ffa.setDisplayName("§cVoltar");
                                flecha.setItemMeta(ffa);
                                inv.setItem(49, flecha);
                                map = new ItemStack(Material.MAP);
                                itemMeta = map.getItemMeta();
                                itemMeta.setDisplayName("§aBase");
                                lore = new ArrayList<>();
                                lore.add("§eAqui você pode retirar automaticamente");
                                lore.add("§espawners da base, ou colocar spawners");
                                lore.add("§ejá retirados por aqui anteriormente.");
                                itemMeta.setLore(lore);
                                map.setItemMeta(itemMeta);
                                inv.setItem(15, map);
                                ItemStack down2 = Heads.DOWN;
                                ItemMeta ddo2 = down2.getItemMeta();
                                ddo2.setDisplayName("§2Colocar todos");
                                ddo2.setLore((List) Arrays.asList("§eClique para coletar todos", "§eos geradores que estão nos chunks", "§eda facção para o f geradores."));
                                down2.setItemMeta(ddo2);
                                inv.setItem(33, down2);

                                ItemStack book = new ItemStack(Material.BOOK);
                                ItemMeta itemMeta1 = book.getItemMeta();
                                itemMeta1.setDisplayName("§2Opções extras");
                                itemMeta1.setLore(Arrays.asList("§eClique para colocar e retirar", "§espawners especifícos das", "§eterras da facção."));
                                book.setItemMeta(itemMeta1);
                                inv.setItem(22, book);
                                up = Heads.UP;
                                upa = up.getItemMeta();
                                upa.setDisplayName("§2Retirar todos");
                                upa.setLore((List) Arrays.asList("§eClique para retirar todos os", "§egeradores que estão nas", "§e?lterras da facção"));
                                up.setItemMeta(upa);
                                inv.setItem(24, up);
                                p.openInventory(inv);
                                api.setAbriuPorUltimo(p.getName());
                                return;
                            }
                            if (holder.getSlots().containsKey(slot)) {
                                final ClickType click = e.getClick();
                                final EntityType entity2 = holder.getSlots().get(slot);
                                final String entityId = this.getConfigEntityId(entity2);
                                if (p.getInventory().firstEmpty() == -1) {
                                    p.sendMessage("§cVocê está com o inventário lotado.");
                                    return;
                                }
                                int amount2 = 1;
                                switch (entity2) {
                                    case BLAZE: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaBlaze();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaBlaze() > podecoletar) {
                                                api.setQuantiaBlaze(api.getQuantiaBlaze() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaBlaze(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaBlaze(api.getQuantiaBlaze() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case CAVE_SPIDER: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaCave_Spider();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaCave_Spider() > podecoletar) {
                                                api.setQuantiaCave_Spider(api.getQuantiaCave_Spider() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaCave_Spider(0);
                                            }
                                            final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                            api.addLog(message);
                                            String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                            final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                            final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                            p.sendMessage("§e" + amount2 + msg2);
                                            PlayerCommand.openInventory(p, true);
                                            return;
                                        }
                                        api.setQuantiaCave_Spider(api.getQuantiaCave_Spider() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case CHICKEN: {
                                        if (click.isShiftClick()) {
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            amount2 = api.getQuantiaChicken();
                                            if (api.getQuantiaChicken() > podecoletar) {
                                                api.setQuantiaChicken(api.getQuantiaChicken() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaChicken(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                            }
                                            return;
                                        }
                                        api.setQuantiaChicken(api.getQuantiaChicken() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case COW: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaCow();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaCow() > podecoletar) {
                                                api.setQuantiaCow(api.getQuantiaCow() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaCow(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaCow(api.getQuantiaCow() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case CREEPER: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaCreeper();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaCreeper() > podecoletar) {
                                                api.setQuantiaCreeper(api.getQuantiaCreeper() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaCreeper(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaCreeper(api.getQuantiaCreeper() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case ENDERMAN: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaEnderman();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaEnderman() > podecoletar) {
                                                api.setQuantiaEnderman(api.getQuantiaEnderman() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaEnderman(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaEnderman(api.getQuantiaEnderman() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case IRON_GOLEM: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaIron_Golem();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaIron_Golem() > podecoletar) {
                                                api.setQuantiaIron_Golem(api.getQuantiaIron_Golem() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaIron_Golem(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaIron_Golem(api.getQuantiaIron_Golem() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case MAGMA_CUBE: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaMagma_Cube();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaMagma_Cube() > podecoletar) {
                                                api.setQuantiaMagma_Cube(api.getQuantiaMagma_Cube() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaMagma_Cube(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaMagma_Cube(api.getQuantiaMagma_Cube() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case PIG: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaPig();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaPig() > podecoletar) {
                                                api.setQuantiaPig(api.getQuantiaPig() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaPig(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaPig(api.getQuantiaPig() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case PIG_ZOMBIE: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaPig_Zombie();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaPig_Zombie() > podecoletar) {
                                                api.setQuantiaPig_Zombie(api.getQuantiaPig_Zombie() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaPig_Zombie(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaPig_Zombie(api.getQuantiaPig_Zombie() - 1);

                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SHEEP: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaSheep();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaSheep() > podecoletar) {
                                                api.setQuantiaSheep(api.getQuantiaSheep() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaSheep(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaSheep(api.getQuantiaSheep() - 1);

                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SKELETON: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaSkeleton();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaSkeleton() > podecoletar) {
                                                api.setQuantiaSkeleton(api.getQuantiaSkeleton() - podecoletar);

                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaSkeleton(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaSkeleton(api.getQuantiaSkeleton() - 1);

                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SLIME: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaSlime();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaSlime() > podecoletar) {
                                                api.setQuantiaSlime(api.getQuantiaSlime() - podecoletar);

                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaSlime(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaSlime(api.getQuantiaSlime() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case SPIDER: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaSpider();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaSpider() > podecoletar) {
                                                api.setQuantiaSpider(api.getQuantiaSpider() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaSpider(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaSpider(api.getQuantiaSpider() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case WITHER: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaWither();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaWither() > podecoletar) {
                                                api.setQuantiaWither(api.getQuantiaWither() - podecoletar);

                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaWither(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaWither(api.getQuantiaWither() - 1);

                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case ZOMBIE: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaZombie();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaZombie() > podecoletar) {
                                                api.setQuantiaZombie(api.getQuantiaZombie() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaZombie(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaZombie(api.getQuantiaZombie() - 1);

                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case MUSHROOM_COW: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaMushroom();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaMushroom() > podecoletar) {
                                                api.setQuantiaMush(api.getQuantiaMushroom() - podecoletar);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);

                                                return;
                                            } else {
                                                api.setQuantiaMush(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaMush(api.getQuantiaMushroom() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case WITCH: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaBruxa();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaBruxa() > podecoletar) {
                                                api.setQuantiaBruxa(api.getQuantiaBruxa() - podecoletar);

                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaBruxa(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaBruxa(api.getQuantiaBruxa() - 1);

                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case GHAST: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaGhast();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaGhast() > podecoletar) {
                                                api.setQuantiaGhast(api.getQuantiaGhast() - podecoletar);

                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaGhast(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaGhast(api.getQuantiaGhast() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                    case HORSE: {
                                        if (click.isShiftClick()) {
                                            amount2 = api.getQuantiaCavalo();
                                            int podecoletar = 0;
                                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                                if (p.getInventory().getItem(i) == null) {
                                                    podecoletar = podecoletar + 64;
                                                }
                                            }
                                            if (api.getQuantiaCavalo() > podecoletar) {
                                                api.setQuantiaCavalo(api.getQuantiaCavalo() - podecoletar);

                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + podecoletar + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(podecoletar));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (podecoletar == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + podecoletar + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            } else {
                                                api.setQuantiaCavalo(0);
                                                final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                                api.addLog(message);
                                                String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                                final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                                final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                                p.sendMessage("§e" + amount2 + msg2);
                                                PlayerCommand.openInventory(p, true);
                                                return;
                                            }
                                        }
                                        api.setQuantiaCavalo(api.getQuantiaCavalo() - 1);
                                        final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + amount2 + " " + EntityName.valueOf(entity2).getName();
                                        api.addLog(message);
                                        String cmd = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName()).replace("@tipo", entityId).replace("@quantia", String.valueOf(amount2));
                                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
                                        final String entityName2 = EntityName.valueOf(entity2).getName().toLowerCase();
                                        final String msg2 = (amount2 == 1) ? (" gerador de " + entityName2 + " coletado.") : (" geradores de " + entityName2 + " coletados.");
                                        p.sendMessage("§e" + amount2 + msg2);
                                        PlayerCommand.openInventory(p, true);
                                        return;
                                    }
                                }
                                return;
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    private void removeInventoryItems(final Inventory inv, final Material type, int amount) {
        final ItemStack[] items = inv.getContents();
        for (int i = 0; i < items.length; ++i) {
            final ItemStack is = items[i];
            if (is != null && is.getType() == type) {
                final int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                }
                items[i] = new ItemStack(Material.AIR);
                amount = -newamount;
                if (amount == 0) {
                    break;
                }
            }
        }
        inv.setContents(items);
    }

    private String getConfigEntityId(final EntityType entity) {
        return Main.getInstance().getConfig().getString("Tipo-Do-Mob." + entity);
    }
}
