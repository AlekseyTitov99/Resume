package com.dungeons.system.listeners;

import com.dungeons.system.Main;
import com.dungeons.system.SQL.AtlasStorage;
import com.dungeons.system.api.ItemBuilder;
import com.dungeons.system.api.ItemEncoder;
import com.dungeons.system.api.ItemMetadata;
import com.dungeons.system.api.getSpawner;
import com.dungeons.system.dungeon.Dungeon;
import com.dungeons.system.objeto.DungeonUser;
import com.dungeons.system.util.TitleAPI;
import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GeneralEntitys implements Listener
{

    @EventHandler
    public void onDead(EntityCombustEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onDead(EntityCombustByBlockEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onDead(EntityCombustByEntityEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onCollect(PlayerPickupItemEvent e){
        if (e.getItem().hasMetadata("CustomItem")){
            e.setCancelled(true);
            e.getItem().remove();
            Player p = e.getPlayer();
            if (Main.cacheuser.containsKey(p.getName())) {
                DungeonUser dungeonUser = Main.cacheuser.get(p.getName());
                try {
                    dungeonUser.getCache().add(ItemEncoder.toBase64(e.getItem().getItemStack()));
                    if (Main.cache.containsKey(dungeonUser.getPartyname())) {
                        Dungeon dungeon = Main.cache.get(dungeonUser.getPartyname());
                        dungeon.getCurrentfase().getColetados();
                        if (dungeon.getCurrentfase().getColetados().containsKey(p.getName())){
                            dungeon.getCurrentfase().getColetados().get(p.getName()).add(ItemEncoder.toBase64(e.getItem().getItemStack()));
                        } else {
                            LinkedList<String> items = new LinkedList<>();
                            items.add(ItemEncoder.toBase64(e.getItem().getItemStack()));
                            dungeon.getCurrentfase().getColetados().put(p.getName(),items);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onDead(EntityDeathEvent e){
        if (e.getEntity().getKiller() == null) return;
        if (e.getEntity().getKiller() != null)
        {
            Player p = e.getEntity().getKiller();
            if (Main.cacheuser.containsKey(p.getName())){
                DungeonUser dungeonUser = Main.cacheuser.get(p.getName());
                if (Main.cache.containsKey(dungeonUser.getPartyname())){
                    Dungeon dungeon = Main.cache.get(dungeonUser.getPartyname());
                    if (dungeon.getEntities().contains(e.getEntity())) {
                        e.getDrops().clear();
                        if (new Random().nextInt(100) < 7){
                            int randomitem = new Random().nextInt(dungeon.getCurrentfase().getItems().size());
                            String item = dungeon.getCurrentfase().getItems().get(randomitem);
                            if (e.getEntity().getType() == EntityType.GIANT) {
                                if (new Random().nextInt(100) < 20) {
                                    ItemStack x = new ItemBuilder(Material.EYE_OF_ENDER).setName("§bGema de Tempestade de Raios").toItemStack();
                                    x.setAmount(2);
                                    ItemStack itemStack = ItemMetadata.setMetadata(x, "GemaType", "RAIO").clone();
                                    Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                                    item1.setMetadata("CustomItem", new FixedMetadataValue(Main.getPlugin(Main.class), ""));
                                    item1.setCustomName("§b1x Gerador de Monstros");
                                    item1.setCustomNameVisible(true);
                                }
                                if (new Random().nextInt(100) < 20) {
                                    ItemStack x = new ItemBuilder(Material.EYE_OF_ENDER).setName("§bGema da Prosperidade").toItemStack();
                                    x.setAmount(2);
                                    ItemStack itemStack = ItemMetadata.setMetadata(x, "GemaType", "PROSPERIDADE").clone();
                                    Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                                    item1.setMetadata("CustomItem", new FixedMetadataValue(Main.getPlugin(Main.class), ""));
                                    item1.setCustomName("§b1x Gerador de Monstros");
                                    item1.setCustomNameVisible(true);
                                }
                                if (new Random().nextInt(100) < 20) {
                                    ItemStack itemStack = getSpawner.Pegar("ZOMBIE");
                                    itemStack.setAmount(new Random().nextInt(10) + 1);
                                    Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                                    item1.setMetadata("CustomItem", new FixedMetadataValue(Main.getPlugin(Main.class), ""));
                                    item1.setCustomName("§b1x Gerador de Monstros");
                                    item1.setCustomNameVisible(true);
                                }
                            }
                                if (e.getEntity().getType() == EntityType.WITHER) {
                                    if (new Random().nextInt(100) < 20) {
                                        ItemStack x = new ItemBuilder(Material.EYE_OF_ENDER).setName("§bGema da Proteção Divina").toItemStack();
                                        x.setAmount(2);
                                        ItemStack itemStack = ItemMetadata.setMetadata(x, "GemaType", "DIVINA").clone();
                                        Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                                        item1.setMetadata("CustomItem", new FixedMetadataValue(Main.getPlugin(Main.class), ""));
                                        item1.setCustomName("§b1x Gerador de Monstros");
                                        item1.setCustomNameVisible(true);
                                    }
                                    if (new Random().nextInt(100) < 20) {
                                        ItemStack x = new ItemBuilder(Material.EYE_OF_ENDER).setName("§bGema de Pulso Eletromagnético").toItemStack();
                                        x.setAmount(2);
                                        ItemStack itemStack = ItemMetadata.setMetadata(x, "GemaType", "PULSO").clone();
                                        Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                                        item1.setMetadata("CustomItem", new FixedMetadataValue(Main.getPlugin(Main.class), ""));
                                        item1.setCustomName("§b1x Gerador de Monstros");
                                        item1.setCustomNameVisible(true);
                                    }
                                    ItemStack itemStack = getSpawner.Pegar("IRON_GOLEM");
                                    itemStack.setAmount(new Random().nextInt(5) + 1);
                                    Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), itemStack);
                                    item1.setMetadata("CustomItem", new FixedMetadataValue(Main.getPlugin(Main.class), ""));
                                    item1.setCustomName("§b1x Gerador de Monstros");
                                    item1.setCustomNameVisible(true);
                                }
                            try {
                                ItemStack itemStack = ItemEncoder.fromBase64(item);
                                Item item1 = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(),itemStack);
                                item1.setMetadata("CustomItem",new FixedMetadataValue(Main.getPlugin(Main.class),""));
                                item1.setCustomName("§b1x " + itemStack.getType().name());
                                item1.setCustomNameVisible(true);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        if (dungeon.getEntityAlive() == 0) {
                            if (dungeon.getCurrentfase().isIsfinal()){
                                if (Main.sala == 0){
                                    AtlasStorage.saveNumber(0);
                                } else {
                                    Main.setSala(Main.sala - 1);
                                    AtlasStorage.saveNumber(Main.getSala());
                                }
                                for (String jogadore : dungeon.getJogadores()) {
                                    if (Bukkit.getPlayer(jogadore) == null) continue;
                                    Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                    TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 80, 20, "§aVitória!","§7Todas as fases foram concluídas");
                                }
                                new BukkitRunnable() {
                                    int x1 = 0;
                                    @Override
                                    public void run() {
                                        x1++;
                                        for (String jogadore : dungeon.getJogadores()) {
                                            if (Main.cacheuser.containsKey(jogadore)){
                                                AtlasStorage.savePlayer(jogadore,Main.cacheuser.get(jogadore),true);
                                            }
                                            if (Bukkit.getPlayer(jogadore) == null) continue;
                                            launchFirework(Bukkit.getPlayer(jogadore));
                                        }
                                        if (x1 == 10){
                                            for (String jogadore : dungeon.getJogadores()) {
                                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                                launchFirework(Bukkit.getPlayer(jogadore));
                                                ByteArrayOutputStream b = new ByteArrayOutputStream();
                                                DataOutputStream out = new DataOutputStream(b);
                                                try {
                                                    out.writeUTF("Connect");
                                                    out.writeUTF("Factions");
                                                } catch (Exception e){

                                                }
                                                Bukkit.getPlayer(jogadore).getInventory().clear();
                                                Bukkit.getPlayer(jogadore).updateInventory();
                                                Bukkit.getPlayer(jogadore).sendPluginMessage(Main.getPlugin(Main.class), "BungeeCord", b.toByteArray());
                                            }
                                            this.cancel();
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    Dungeon dungeon = Main.cache.get(dungeonUser.getPartyname());
                                                    MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().deleteWorld(dungeon.getParty().getSala(),true);
                                                    MultiverseCore.getPlugin(MultiverseCore.class).getMVWorldManager().cloneWorld("wl",dungeon.getParty().getSala());
                                                    Main.cache.remove(dungeonUser.getPartyname());
                                                }
                                            }.runTaskLater(Main.getPlugin(Main.class),20L*2);
                                            return;
                                        }
                                    }
                                }.runTaskTimer(Main.getPlugin(Main.class),0L,20L);
                                return;
                            }
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    dungeon.passarFase();
                                }
                            }.runTask(Main.getPlugin(Main.class));
                            return;
                        }
                    }
                }
            }
        }
    }

    private Color getColor(int i) {
        Color c = null;
        if(i==1){
            c=Color.AQUA;
        }
        if(i==2){
            c=Color.BLACK;
        }
        if(i==3){
            c=Color.BLUE;
        }
        if(i==4){
            c=Color.FUCHSIA;
        }
        if(i==5){
            c=Color.GRAY;
        }
        if(i==6){
            c=Color.GREEN;
        }
        if(i==7){
            c=Color.LIME;
        }
        if(i==8){
            c=Color.MAROON;
        }
        if(i==9){
            c=Color.NAVY;
        }
        if(i==10){
            c=Color.OLIVE;
        }
        if(i==11){
            c=Color.ORANGE;
        }
        if(i==12){
            c=Color.PURPLE;
        }
        if(i==13){
            c=Color.RED;
        }
        if(i==14){
            c=Color.SILVER;
        }
        if(i==15){
            c=Color.TEAL;
        }
        if(i==16){
            c=Color.WHITE;
        }
        if(i==17){
            c=Color.YELLOW;
        }

        return c;
    }

    public void launchFirework(Player p) {
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        //Our random generator
        Random r = new Random();

        //Get the type
        int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) type = FireworkEffect.Type.BALL;
        if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3) type = FireworkEffect.Type.BURST;
        if (rt == 4) type = FireworkEffect.Type.CREEPER;
        if (rt == 5) type = FireworkEffect.Type.STAR;

        //Get our random colours
        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);

        //Create our effect with this
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        //Then apply the effect to the meta
        fwm.addEffect(effect);

        //Generate some random power and set it
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        //Then apply this to our rocket
        fw.setFireworkMeta(fwm);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e){
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM){
            return;
        } else {
            e.setCancelled(true);
        }
    }
}