package com.dungeons.system.listeners;

import com.dungeons.system.Main;
import com.dungeons.system.api.ItemBuilder;
import com.dungeons.system.dungeon.Dungeon;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.spigotmc.event.entity.EntityMountEvent;

public class GeneralNPC implements Listener {

    public static ItemStack charge(Color cor) {
        ItemStack playerDrop = new ItemStack( Material.FIREWORK_CHARGE, 1);
        ItemMeta meta = playerDrop.getItemMeta();
        FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
        FireworkEffect aa = FireworkEffect.builder().withColor(cor).build();
        metaFw.setEffect(aa);
        playerDrop.setItemMeta(metaFw);
        return playerDrop;
    }

    public static void openLoja(Player p) {
        if (Main.cacheuser.containsKey(p.getName())) {
            if (Main.cache.containsKey(Main.cacheuser.get(p.getName()).getPartyname())) {
                Dungeon currentDungeon = Main.cache.get(Main.cacheuser.get(p.getName()).getPartyname());
                Inventory inv = Bukkit.createInventory(null, 36, "Loja");
                inv.setItem(31, new ItemBuilder(Material.PAPER).setName("§fPepitas: §7" + currentDungeon.getPepitas().get(p.getName())).toItemStack());
                inv.setItem(11, new ItemBuilder(charge(Color.PURPLE).clone()).setName("§aGranada de Distração").setLore("§7Distraia todos os monstros em um raio de 15", "§7blocos para onde você jogar a granada", "§7", "§7Custo: §e" +200 + " pepitas").toItemStack());
                inv.setItem(15, new ItemBuilder(charge(Color.RED).clone()).setName("§aGranada Explosiva").setLore("§7A granada de explosão causa um alto dano", "§7aos mobs que estão em volta dela, além de", "§7deixar o chão em chamas por alguns segundos.", "§7", "§7Custo: §e" + 200 + " pepitas").toItemStack());
                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onShit(EntityMountEvent e){
        if (!CitizensAPI.getNPCRegistry().isNPC(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on1(NPCRightClickEvent e){
        e.setCancelled(true);
    }

}