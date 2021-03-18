package br.jotaentity.geradores.listeners;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objetos.FactionGeradores;
import br.jotaentity.geradores.utils.GuiHolder;
import br.jotaentity.geradores.utils.Heads;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PlayerCommand implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final String msg = e.getMessage();
        if (msg.startsWith("/f geradores")) {
            e.setCancelled(true);
            final Player p = e.getPlayer();
            openInventory(p, false);
        }
    }

    public static void openInventory(final Player p, final boolean reopen) {
        final MPlayer mplayer = MPlayer.get((Object) p);
        if (!mplayer.hasFaction()) {
            p.sendMessage(Main.getInstance().config.getConfig().getString("Mensagens.semfaccao").replace('&', '§'));
            return;
        }
        final Faction fac = mplayer.getFaction();
        final Rel cargo = mplayer.getRole();
        if (!mplayer.isOverriding()) {
            if (cargo == Rel.RECRUIT && !Main.getInstance().config.getConfig().getBoolean("Cargos.recruta")) {
                p.sendMessage(Main.getInstance().config.getConfig().getString("Mensagens.cargo").replace('&', '§'));
                return;
            }
            if (cargo == Rel.MEMBER && !Main.getInstance().config.getConfig().getBoolean("Cargos.Membro")) {
                p.sendMessage(Main.getInstance().config.getConfig().getString("Mensagens.cargo").replace('&', '§'));
                return;
            }
            if (cargo == Rel.OFFICER && !Main.getInstance().config.getConfig().getBoolean("Cargos.Oficial")) {
                p.sendMessage(Main.getInstance().config.getConfig().getString("Mensagens.cargo").replace('&', '§'));
                return;
            }
        }
        if (Main.cache.containsKey(fac) && !Main.cache.get(fac).getName().equalsIgnoreCase(p.getName())) {
            p.sendMessage("§cO Jogador " + Main.cache
                    .get(fac).getName() + "§c j? est? com o menu de geradores aberto.");
            return;
        }
        FactionGeradores api = new FactionGeradores(fac.getId());
        if (api.checkAbriuPorUltimoOpen() && !reopen) {
            p.sendMessage(Main.getInstance().config.getConfig().getString("Mensagens.open").replace('&', '?').replace("@jogador", api.getAbriuPorUltimo()));
            return;
        }
        if (api.getQuantiaWither() == null) {
            api.importGeradores(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        api.setAbriuPorUltimo(p.getName());
        final List<String> lore = new ArrayList<String>();
        lore.add("§7Bot\u00e3o esquerdo: §fColetar 1 gerador.");
        lore.add("§7Shift + Bot\u00e3o esquerdo: §fColetar todos os geradores.");
        int slot = 19;
        final HashMap<Integer, EntityType> slots = new HashMap<Integer, EntityType>();
        final GuiHolder gui = new GuiHolder(1, fac.getId(), slots);
        final Inventory inv = Bukkit.createInventory((InventoryHolder) gui, 54, "[" + fac.getTag() + "] Geradores");
        final ItemStack gerenciar = new ItemStack(Material.DIODE);
        final ItemMeta gr = gerenciar.getItemMeta();
        gr.setDisplayName("§2Gerenciamento em massa");
        gr.setLore((List) Arrays.asList("§eClique para gerenciar", "§eos geradores em massa."));
        gerenciar.setItemMeta(gr);
        inv.setItem(13, gerenciar);
        final ItemStack ultimo = new ItemStack(Material.BOOK);
        final ItemMeta am = ultimo.getItemMeta();
        am.setDisplayName("§a\u00daltimas movimenta\u00e7\u00f5es");
        final List<String> ff = new ArrayList<String>();
        ff.add("§8* Movimenta\u00e7\u00f5es nos geradores da fac\u00e7\u00e3o.");
        final List<String> ultimos = api.getLog();
        if (ultimos.isEmpty()) {
            ff.add("§cNenhuma movimenta\u00e7\u00e3o registrada at\u00e9 o momento.");
        } else {
            Collections.reverse(ultimos);
            for (final String string : ultimos) {
                ff.add(string.replace('&', '§'));
            }
        }
        boolean vazio =  true;
        am.setLore((List) ff);
        ultimo.setItemMeta(am);
        inv.setItem(49, ultimo);
        if (api.getQuantiaPig() > 0) {
            vazio = false;
            final ItemStack pig = Heads.PIG;
            final ItemMeta pm = pig.getItemMeta();
            pm.setDisplayName("§e" + api.getQuantiaPig() + " geradores de Porco");
            pm.setLore((List) lore);
            pig.setItemMeta(pm);
            inv.setItem(slot, pig);
            slots.put(slot, EntityType.PIG);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaCow() > 0) {
            vazio = false;
            final ItemStack cow = Heads.COW;
            final ItemMeta cm = cow.getItemMeta();
            cm.setDisplayName("§e" + api.getQuantiaCow() + " geradores de Vaca");
            cm.setLore((List) lore);
            cow.setItemMeta(cm);
            inv.setItem(slot, cow);
            slots.put(slot, EntityType.COW);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaSpider() > 0) {
            vazio = false;
            final ItemStack spider = Heads.SPIDER;
            final ItemMeta sm = spider.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaSpider() + " geradores de Aranha");
            sm.setLore((List) lore);
            spider.setItemMeta(sm);
            inv.setItem(slot, spider);
            slots.put(slot, EntityType.SPIDER);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaZombie() > 0) {
            vazio = false;
            final ItemStack zombie = Heads.ZOMBIE;
            final ItemMeta zm = zombie.getItemMeta();
            zm.setDisplayName("§e" + api.getQuantiaZombie() + " geradores de Zumbi");
            zm.setLore((List) lore);
            zombie.setItemMeta(zm);
            inv.setItem(slot, zombie);
            slots.put(slot, EntityType.ZOMBIE);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaSkeleton() > 0) {
            vazio = false;
            final ItemStack skeleton = Heads.SKELETON;
            final ItemMeta sm = skeleton.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaSkeleton() + " geradores de Esqueleto");
            sm.setLore((List) lore);
            skeleton.setItemMeta(sm);
            inv.setItem(slot, skeleton);
            slots.put(slot, EntityType.SKELETON);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaBlaze() > 0) {
            vazio = false;
            final ItemStack blaze = Heads.BLAZE;
            final ItemMeta bm = blaze.getItemMeta();
            bm.setDisplayName("§e" + api.getQuantiaBlaze() + " geradores de Blaze");
            bm.setLore((List) lore);
            blaze.setItemMeta(bm);
            inv.setItem(slot, blaze);
            slots.put(slot, EntityType.BLAZE);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaSlime() > 0) {
            vazio = false;
            final ItemStack slime = Heads.SLIME;
            final ItemMeta sm = slime.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaSlime() + " geradores de Slime");
            sm.setLore((List) lore);
            slime.setItemMeta(sm);
            inv.setItem(slot, slime);
            slots.put(slot, EntityType.SLIME);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaPig_Zombie() > 0) {
            vazio = false;
            final ItemStack pig_zombie = Heads.PIG_ZOMBIE;
            final ItemMeta pm = pig_zombie.getItemMeta();
            pm.setDisplayName("§e" + api.getQuantiaPig_Zombie() + " geradores de Porco Zumbi");
            pm.setLore((List) lore);
            pig_zombie.setItemMeta(pm);
            inv.setItem(slot, pig_zombie);
            slots.put(slot, EntityType.PIG_ZOMBIE);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaIron_Golem() > 0) {
            vazio = false;
            final ItemStack iron_golem = Heads.IRONGOLEM;
            final ItemMeta im = iron_golem.getItemMeta();
            im.setDisplayName("§e" + api.getQuantiaIron_Golem() + " geradores de Golem de Ferro");
            im.setLore((List) lore);
            iron_golem.setItemMeta(im);
            inv.setItem(slot, iron_golem);
            slots.put(slot, EntityType.IRON_GOLEM);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaWither() > 0) {
            vazio = false;
            final ItemStack wither = Heads.WITHER;
            final ItemMeta wm = wither.getItemMeta();
            wm.setDisplayName("§e" + api.getQuantiaWither() + " geradores de Wither");
            wm.setLore((List) lore);
            wither.setItemMeta(wm);
            inv.setItem(slot, wither);
            slots.put(slot, EntityType.WITHER);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaCave_Spider() > 0) {
            vazio = false;
            final ItemStack cave_spider = Heads.CAVE_SPIDER;
            final ItemMeta cm = cave_spider.getItemMeta();
            cm.setDisplayName("§e" + api.getQuantiaCave_Spider() + " geradores de Aranha da Caverna");
            cm.setLore((List) lore);
            cave_spider.setItemMeta(cm);
            inv.setItem(slot, cave_spider);
            slots.put(slot, EntityType.CAVE_SPIDER);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaChicken() > 0) {
            vazio = false;
            final ItemStack chicken = Heads.CHICKEN;
            final ItemMeta cm = chicken.getItemMeta();
            cm.setDisplayName("§e" + api.getQuantiaChicken() + " geradores de Galinha");
            cm.setLore((List) lore);
            chicken.setItemMeta(cm);
            inv.setItem(slot, chicken);
            slots.put(slot, EntityType.CHICKEN);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaCreeper() > 0) {
            vazio = false;
            final ItemStack creeper = Heads.CREEPER;
            final ItemMeta cm = creeper.getItemMeta();
            cm.setDisplayName("§e" + api.getQuantiaCreeper() + " geradores de Creeper");
            cm.setLore((List) lore);
            creeper.setItemMeta(cm);
            inv.setItem(slot, creeper);
            slots.put(slot, EntityType.CREEPER);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaEnderman() > 0) {
            vazio = false;
            final ItemStack enderman = Heads.ENDERMAN;
            final ItemMeta em = enderman.getItemMeta();
            em.setDisplayName("§e" + api.getQuantiaEnderman() + " geradores de Enderman");
            em.setLore((List) lore);
            enderman.setItemMeta(em);
            inv.setItem(slot, enderman);
            slots.put(slot, EntityType.ENDERMAN);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaMagma_Cube() > 0) {
            vazio = false;
            final ItemStack magma_cube = Heads.MAGMA_CUBE;
            final ItemMeta mm = magma_cube.getItemMeta();
            mm.setDisplayName("§e" + api.getQuantiaMagma_Cube() + " geradores de Cubo de Magma");
            mm.setLore((List) lore);
            magma_cube.setItemMeta(mm);
            inv.setItem(slot, magma_cube);
            slots.put(slot, EntityType.MAGMA_CUBE);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaSheep() > 0) {
            vazio = false;
            final ItemStack sheep = Heads.SHEEP;
            final ItemMeta sm = sheep.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaSheep() + " geradores de Ovelha");
            sm.setLore((List) lore);
            sheep.setItemMeta(sm);
            inv.setItem(slot, sheep);
            slots.put(slot, EntityType.SHEEP);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaMushroom() > 0) {
            vazio = false;
            final ItemStack sheep = Heads.MUSHROOM_COW;
            final ItemMeta sm = sheep.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaMushroom() + " geradores de Vaca de Cogumelo");
            sm.setLore((List) lore);
            sheep.setItemMeta(sm);
            inv.setItem(slot, sheep);
            slots.put(slot, EntityType.MUSHROOM_COW);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaBruxa() > 0) {
            vazio = false;
            final ItemStack sheep = Heads.WITCH;
            final ItemMeta sm = sheep.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaBruxa() + " geradores de Bruxa");
            sm.setLore((List) lore);
            sheep.setItemMeta(sm);
            inv.setItem(slot, sheep);
            slots.put(slot, EntityType.WITCH);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaGhast() > 0) {
            vazio = false;
            final ItemStack sheep = Heads.GHAST;
            final ItemMeta sm = sheep.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaGhast() + " geradores de Ghast");
            sm.setLore((List) lore);
            sheep.setItemMeta(sm);
            inv.setItem(slot, sheep);
            slots.put(slot, EntityType.GHAST);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (api.getQuantiaCavalo() > 0) {
            vazio = false;
            final ItemStack sheep = Heads.HORSE;
            final ItemMeta sm = sheep.getItemMeta();
            sm.setDisplayName("§e" + api.getQuantiaCavalo() + " geradores de Cavalo");
            sm.setLore((List) lore);
            sheep.setItemMeta(sm);
            inv.setItem(slot, sheep);
            slots.put(slot, EntityType.HORSE);
            slot += ((slot == 25) ? 3 : (slot == 34) ? 3 : 1);
        }
        if (vazio){
            inv.setItem(31,new ItemBuilder(Material.WEB).setName("§cSem geradores").setLore("§7Sem geradores armazenados.").toItemStack());
        }
        p.openInventory(inv);
        Main.cache.put(fac, p);
    }
}