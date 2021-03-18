package br.jotaentity.geradores.utils;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objetos.FactionGeradores;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("all")
public class TypeInventory {

    public static void openinventory(GuiHolder gui2, Player player) {
        MPlayer mPlayer = MPlayer.get(player);
        String tag = mPlayer.getFactionTag();
        if (mPlayer.hasFaction()) {
            HashMap<Integer, EntityType> slots = new HashMap<Integer, EntityType>();
            gui2 = new GuiHolder(3, mPlayer.getFaction().getId(), slots);
            FactionGeradores api = new FactionGeradores(mPlayer.getFaction().getId());
            api.setAbriuPorUltimo(player.getName());
            Inventory inv = Bukkit.createInventory((InventoryHolder) gui2, 54, "[" + tag + "] Gerenciar");
            int slot = 19;
            boolean vazio = true;
            final List<String> lore = new ArrayList<String>();
            lore.add("§7Aqui você pode gerenciar se vai retirar");
            lore.add("§7ou colocar este spawner específico.");
            lore.add("");
            lore.add("§aClique aqui para abrir.");
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
                inv.setItem(22,new ItemBuilder(Material.WEB).setName("§cVazio").toItemStack());
            }
            inv.setItem(49,new ItemBuilder(Material.ARROW).setName("§cVoltar").toItemStack());
            player.openInventory(inv);
            Main.cache.put(mPlayer.getFaction(), player);
            return;
        }
    }

}