package com.pagani.dungeon.util;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.pagani.dungeon.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class DungeonNPC {

    private static Hologram DUNGEONS;
    private static Hologram CLIQUE;
    private static Location loc;
    public static ArrayList<Hologram> spawned_holograms = new ArrayList<>();

    public static Inventory inventory = Bukkit.createInventory(null,54,"Recompensas");

    public static void despawn() {
        if (loc == null) return;
        for (Entity e : loc.getWorld().getEntities()) {
            if (e.getType().equals(EntityType.ARMOR_STAND)) {
                if (e.hasMetadata("ArmorDungeon")) {
                    e.remove();
                }
            }
        }
        for (Hologram spawned_hologram : spawned_holograms) {
            spawned_hologram.delete();
        }
        DUNGEONS.delete();
        CLIQUE.delete();
    }

    public static String getSerializedLocation(Location loc) {
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch()
                + ";" + loc.getWorld().getUID();
    }

    public static Location getDeserializedLocation(String s) {
        if (s.equalsIgnoreCase("") || s.equalsIgnoreCase(" ")) {
            return null;
        }
        String[] parts = s.split(";");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        float yaw = Float.parseFloat(parts[3]);
        float pitch = Float.parseFloat(parts[4]);
        UUID u = UUID.fromString(parts[5]);
        World w = Bukkit.getServer().getWorld(u);
        return new Location(w, x, y, z, yaw, pitch);
    }

    public static void spawn() {
        String s = Main.getPlugin(Main.class).getConfig().getString("npcloc");
        if (s == null || s.equalsIgnoreCase("") || s.equalsIgnoreCase(" ")) {
            return;
        }
        loc = getDeserializedLocation(Main.getPlugin(Main.class).getConfig().getString("npcloc"));
        if (loc == null || loc.getWorld() == null) return;
        if (CitizensAPI.getNPCRegistry().getById(3306) != null) {
            if (CitizensAPI.getNPCRegistry().getById(3306).isSpawned()) {
                CitizensAPI.getNPCRegistry().getById(3306).despawn();
                CitizensAPI.getNPCRegistry().deregister(CitizensAPI.getNPCRegistry().getById(3306));
            }
        }
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, UUID.randomUUID(), 3306, "§8[NPC] Dungeon NPC");
        npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA,"0K3LV1N");
        if (!npc.hasTrait(SkinTrait.class)) {
            npc.addTrait(new SkinTrait());
        }
        npc.getTrait(SkinTrait.class).setSkinPersistent("DONTMATTER",
                "xV1WBOl+utb8mhp6ojHjEUr06XpPhhmTrmIAorZejCqOCHhf5xqn/I99hOEVJ98DY0dPPcAeQJpkSlLIAKvCD6yaDFK9pNyUDBrbMEaM9lUbPxP02yZJ5aL0mK2STs3tK8gZt55Y5Vu/tCMVf013K+BGkZlT89AgFYv9YybNIeayLymEU7lxebp0gkdI9tpJEDIuZscXFe8cgVHRYGBwJHyqNSbluPuJSmSP4t1Bbw5GSRvVZmbWsazaPpW/aBKK+vCYEuM2fpJ7RjCw8KzT4wZ5Sq8wSJV5MeM0nIsTvbL6M8k80GSHOyhx+oUxLH4samiSwm6+Nnzfzrx/HB4QJcyM/CQHzXO786xnK/GPmF3YzDPC4on1VLyvIdEYVOCfn7dX0v2mG7itZpzirwCbKYk/XVIYxHZMPcqohwhj24PTP7YgcFbd+pcYAX3idggPwBm3jpqmJ+7AWjousCMa1k2vxln/nvjdKMUezXx9RgKzvdAAa0m4MqLtrqOpS5CiKL5bTvOwv4VUKcpsDjGNmXXZl1dfrDd6bbkA2pA/S5aZil0gkqNr425ODwD0iuMFySRJ4o8RbJU+s8lWVRx+P/3ncX7mWhfJVX3qRV4hC1ddCqiz/soIHCVNCBJfse5f25IRs3DrCzJ6SPaBg5q5Lf48CigOB8p/nJKEEPH/ymU=",
                "ewogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJpZCIgOiAiZThhODIwOWFjOWE0NGE0N2I5YTE0ODNjNTdjZDRmN2IiLAogICAgICAidHlwZSIgOiAiU0tJTiIsCiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE3ZmYyMTQ4NTY5MDA0Y2VhMzgyODk1M2MxMWU5Y2Y5MTc2YjE2M2EzYTQ5NTM5OTgyODkyNmQ0OGJmZGM5NSIsCiAgICAgICJwcm9maWxlSWQiIDogIjkxZjA0ZmU5MGYzNjQzYjU4ZjIwZTMzNzVmODZkMzllIiwKICAgICAgInRleHR1cmVJZCIgOiAiNTE3ZmYyMTQ4NTY5MDA0Y2VhMzgyODk1M2MxMWU5Y2Y5MTc2YjE2M2EzYTQ5NTM5OTgyODkyNmQ0OGJmZGM5NSIKICAgIH0KICB9LAogICJza2luIiA6IHsKICAgICJpZCIgOiAiZThhODIwOWFjOWE0NGE0N2I5YTE0ODNjNTdjZDRmN2IiLAogICAgInR5cGUiIDogIlNLSU4iLAogICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81MTdmZjIxNDg1NjkwMDRjZWEzODI4OTUzYzExZTljZjkxNzZiMTYzYTNhNDk1Mzk5ODI4OTI2ZDQ4YmZkYzk1IiwKICAgICJwcm9maWxlSWQiIDogIjkxZjA0ZmU5MGYzNjQzYjU4ZjIwZTMzNzVmODZkMzllIiwKICAgICJ0ZXh0dXJlSWQiIDogIjUxN2ZmMjE0ODU2OTAwNGNlYTM4Mjg5NTNjMTFlOWNmOTE3NmIxNjNhM2E0OTUzOTk4Mjg5MjZkNDhiZmRjOTUiCiAgfSwKICAiY2FwZSIgOiBudWxsCn0=");

        npc.spawn(loc);
        Hologram h = HologramsAPI.createHologram(Main.getPlugin(Main.class), loc.clone().add(0, 0.9, 0));
        h.appendTextLine("§a§lDungeons");
        Hologram h2 = HologramsAPI.createHologram(Main.getPlugin(Main.class),loc.clone().add(0, 0.5, 0));
        h2.appendTextLine("§eClique para abrir");
        CLIQUE = h2;
        DUNGEONS = h;
        spawned_holograms.add(h);
        spawned_holograms.add(h2);
        new BukkitRunnable() {
            @Override
            public void run() {
                TextLine textLine = (TextLine) DUNGEONS.getLine(0);
                if (textLine.getText().equalsIgnoreCase("§a§lDungeons")) {
                    textLine.setText("§2§lDungeons");
                } else {
                    textLine.setText("§a§lDungeons");
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 20L);
    }




    // oque falta pra fazer na dungeon?
    /**
     * HMMMMMMMMMMMMMMMMMMMMMMMM
     * MMMMMMMMMMMM
     * Vou fazer o npc vai
     */
}