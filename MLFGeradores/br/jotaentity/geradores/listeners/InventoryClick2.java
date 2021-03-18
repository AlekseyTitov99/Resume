package br.jotaentity.geradores.listeners;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objeto.LocationsUser;
import br.jotaentity.geradores.objeto.Storage;
import br.jotaentity.geradores.objetos.FactionGeradores;
import br.jotaentity.geradores.objetos.FactionGeradoresDLL;
import br.jotaentity.geradores.utils.GuiHolder;
import br.jotaentity.geradores.utils.Heads;
import br.jotaentity.geradores.utils.LocationEncoder;
import br.jotaentity.geradores.utils.TypeInventory;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.ItemBuilder;
import com.massivecraft.factions.util.TimeFormatter;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InventoryClick2 implements Listener {

    @EventHandler
    public void onClick4(final InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GuiHolder) {
            e.setCancelled(true);
            final Player p = (Player) e.getWhoClicked();
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            final MPlayer mplayer = MPlayer.get((Object) p);
            final GuiHolder holder = (GuiHolder) e.getClickedInventory().getHolder();
            final int id = holder.getId();
            if (id == 4) {
                if (holder.getEntityType() != null) {
                    EntityType entityType = holder.getEntityType();
                    if (mplayer.hasFaction()) {
                        if (e.getSlot() == 31) {
                            // colocar na terra.
                            if (mplayer.getFaction().isInAttack()){
                                mplayer.getPlayer().sendMessage("§cVocê não pode fazer isto enquanto sua facção estiver em ataque.");
                                mplayer.getPlayer().closeInventory();
                                return;
                            }
                            p.sendMessage("§aCarregando...");
                            AtomicInteger spawners = new AtomicInteger();
                            if (Main.cacheloc.containsKey(mplayer.getFaction().getName())) {
                                LocationsUser locationsUser = Main.cacheloc.get(mplayer.getFactionName());
                                if (locationsUser.getUse() + 60 * 60 * 1000 < System.currentTimeMillis()) {
                                    locationsUser.setUse(System.currentTimeMillis());
                                    FactionGeradores factionGeradores = new FactionGeradores(mplayer.getFaction().getId());
                                    for (Map.Entry<String, LinkedHashMap<String, String>> hashmap : locationsUser.getCache().entrySet()) {
                                        for (Map.Entry<String, String> locs : hashmap.getValue().entrySet().stream().filter(s -> s.getValue().equalsIgnoreCase(entityType.name().toUpperCase())).collect(Collectors.toSet())) {
                                            Location location = LocationEncoder.getDeserializedLocation(locs.getKey());
                                            if (location.getBlock().getType() != Material.AIR) continue;
                                            PS ps2 = PS.valueOf(location);
                                            Faction faction2 = BoardColl.get().getFactionAt(ps2);
                                            if (faction2.getId().equalsIgnoreCase(mplayer.getFaction().getId())) {
                                                switch (entityType) {
                                                    case PIG_ZOMBIE: {
                                                        if (factionGeradores.getQuantiaPig_Zombie() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaPig_Zombie(factionGeradores.getQuantiaPig_Zombie() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case ZOMBIE: {
                                                        if (factionGeradores.getQuantiaZombie() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaZombie(factionGeradores.getQuantiaZombie() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case COW: {
                                                        if (factionGeradores.getQuantiaCow() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaCow(factionGeradores.getQuantiaCow() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case SPIDER: {
                                                        if (factionGeradores.getQuantiaSpider() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaSpider(factionGeradores.getQuantiaSpider() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case ENDERMAN: {
                                                        if (factionGeradores.getQuantiaEnderman() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaEnderman(factionGeradores.getQuantiaEnderman() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case CREEPER: {
                                                        if (factionGeradores.getQuantiaCreeper() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaCreeper(factionGeradores.getQuantiaCreeper() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case BLAZE: {
                                                        if (factionGeradores.getQuantiaBlaze() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaBlaze(factionGeradores.getQuantiaBlaze() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case IRON_GOLEM: {
                                                        if (factionGeradores.getQuantiaIron_Golem() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaIron_Golem(factionGeradores.getQuantiaIron_Golem() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case MUSHROOM_COW: {
                                                        if (factionGeradores.getQuantiaMushroom() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaMush(factionGeradores.getQuantiaMushroom() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case MAGMA_CUBE: {
                                                        if (factionGeradores.getQuantiaMagma_Cube() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaMagma_Cube(factionGeradores.getQuantiaMagma_Cube() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case SKELETON: {
                                                        if (factionGeradores.getQuantiaSkeleton() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaSkeleton(factionGeradores.getQuantiaSkeleton() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case WITCH: {
                                                        if (factionGeradores.getQuantiaBruxa() >= 1) {
                                                            spawners.getAndIncrement();
                                                            factionGeradores.setQuantiaBruxa(factionGeradores.getQuantiaBruxa() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                        }
                                                        break;
                                                    }
                                                    case WITHER: {
                                                        if (factionGeradores.getQuantiaWither() >= 1) {
                                                            factionGeradores.setQuantiaWither(factionGeradores.getQuantiaWither() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                            spawners.getAndIncrement();
                                                        }
                                                        break;
                                                    }
                                                    case SHEEP: {
                                                        if (factionGeradores.getQuantiaSheep() >= 1) {
                                                            factionGeradores.setQuantiaSheep(factionGeradores.getQuantiaSheep() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                            spawners.getAndIncrement();
                                                        }
                                                        break;
                                                    }
                                                    case OCELOT: {
                                                        break;
                                                    }
                                                    case GHAST: {
                                                        if (factionGeradores.getQuantiaGhast() >= 1) {
                                                            factionGeradores.setQuantiaGhast(factionGeradores.getQuantiaGhast() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                            spawners.getAndIncrement();
                                                        }
                                                        break;
                                                    }
                                                    case SLIME: {
                                                        if (factionGeradores.getQuantiaSlime() >= 1) {
                                                            factionGeradores.setQuantiaSlime(factionGeradores.getQuantiaSlime() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update(true);
                                                            spawners.getAndIncrement();
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (spawners.get() == 0) {
                                        p.sendMessage("§cNão foram colocados nenhum gerador.");
                                    } else {
                                        p.sendMessage("§aForam colocados §7" + spawners.get() + " §ageradores com sucesso.");
                                        return;
                                    }
                                    FactionGeradoresDLL factionGeradoresDLL = new FactionGeradoresDLL(mplayer.getFaction().getId());
                                    factionGeradoresDLL.exportGeradores();
                                    new Storage().exportObject(locationsUser);
                                } else {
                                    long x = locationsUser.getUse() + 60 * 60 * 1000;
                                    e.getWhoClicked().sendMessage("§cVocê está em coodlown, aguarde: " + TimeFormatter.format(System.currentTimeMillis() - x));
                                    e.getWhoClicked().closeInventory();
                                    return;
                                }
                            }
                            return;
                        }
                        if (e.getSlot() == 22) {
                            if (mplayer.getFaction().isInAttack()){
                                mplayer.getPlayer().sendMessage("§cVocê não pode fazer isto enquanto sua facção estiver em ataque.");
                                mplayer.getPlayer().closeInventory();
                                return;
                            }
                            if (Main.cacheloc.containsKey(mplayer.getFaction().getName())) {
                                LocationsUser locationsUser = Main.cacheloc.get(mplayer.getFactionName());
                                Set<PS> chunks = BoardColl.get().getChunks(mplayer.getFaction());
                                FactionGeradores factionGeradores = new FactionGeradores(mplayer.getFaction().getId());
                                final int[] sizearmazenados = {0};
                                mplayer.getPlayer().sendMessage("§aVerificando...");
                                Set<PS> chunk2 = chunks.stream().filter(s -> Arrays.stream(s.asBukkitChunk().getTileEntities()).filter(spawner -> spawner.getType() == Material.MOB_SPAWNER && getspawner(spawner).getSpawnedType() == entityType).collect(Collectors.toSet()).size() > 0).collect(Collectors.toSet());
                                if (chunk2.isEmpty()) {
                                    p.sendMessage("§cNenhum spawner deste tipo encontrado.");
                                    return;
                                }
                                if (locationsUser.getUse() + 60 * 60 * 1000 < System.currentTimeMillis()) {
                                    locationsUser.setUse(System.currentTimeMillis());
                                    for (PS chunk : chunk2) {
                                        Chunk chunk1 = chunk.asBukkitChunk();
                                        Set<BlockState> blocks = Arrays.stream(chunk1.getTileEntities()).filter(s -> s.getType() == Material.MOB_SPAWNER && getspawner(s).getSpawnedType() == entityType).collect(Collectors.toSet());
                                        blocks.forEach(s -> {
                                            sizearmazenados[0] = sizearmazenados[0] + 1;
                                            CreatureSpawner creatureSpawner = (CreatureSpawner) s;
                                            EntityType entityType2 = creatureSpawner.getSpawnedType();
                                            creatureSpawner.getBlock().setType(Material.AIR);
                                            Bukkit.getOnlinePlayers().forEach(ps -> ps.sendBlockChange(s.getBlock().getLocation(), Material.AIR, (byte) 0));
                                            String loc = LocationEncoder.getSerializedLocation(creatureSpawner.getBlock().getLocation());
                                            if (locationsUser.getCache().containsKey(FactionDisband.chunkSerializer(chunk1))) {
                                                if (!locationsUser.getCache().get(FactionDisband.chunkSerializer(chunk1)).containsKey(loc)) {
                                                    locationsUser.getCache().get(FactionDisband.chunkSerializer(chunk1)).put(loc, creatureSpawner.getSpawnedType().name());
                                                } else {
                                                    locationsUser.getCache().get(FactionDisband.chunkSerializer(chunk1)).replace(loc, creatureSpawner.getSpawnedType().name());
                                                }
                                                switch (entityType2) {
                                                    case PIG_ZOMBIE: {
                                                        factionGeradores.setQuantiaPig_Zombie(factionGeradores.getQuantiaPig_Zombie() + 1);
                                                        break;
                                                    }
                                                    case ZOMBIE: {
                                                        factionGeradores.setQuantiaZombie(factionGeradores.getQuantiaZombie() + 1);
                                                        break;
                                                    }
                                                    case COW: {
                                                        factionGeradores.setQuantiaCow(factionGeradores.getQuantiaCow() + 1);
                                                        break;
                                                    }
                                                    case SPIDER: {
                                                        factionGeradores.setQuantiaSpider(factionGeradores.getQuantiaSpider() + 1);
                                                        break;
                                                    }
                                                    case ENDERMAN: {
                                                        factionGeradores.setQuantiaEnderman(factionGeradores.getQuantiaEnderman() + 1);
                                                        break;
                                                    }
                                                    case CREEPER: {
                                                        factionGeradores.setQuantiaCreeper(factionGeradores.getQuantiaCreeper() + 1);
                                                        break;
                                                    }
                                                    case BLAZE: {
                                                        factionGeradores.setQuantiaBlaze(factionGeradores.getQuantiaBlaze() + 1);
                                                        break;
                                                    }
                                                    case IRON_GOLEM: {
                                                        factionGeradores.setQuantiaIron_Golem(factionGeradores.getQuantiaIron_Golem() + 1);
                                                        break;
                                                    }
                                                    case MUSHROOM_COW: {
                                                        factionGeradores.setQuantiaMush(factionGeradores.getQuantiaMushroom() + 1);
                                                        break;
                                                    }
                                                    case MAGMA_CUBE: {
                                                        factionGeradores.setQuantiaMagma_Cube(factionGeradores.getQuantiaMagma_Cube() + 1);
                                                        break;
                                                    }
                                                    case SKELETON: {
                                                        factionGeradores.setQuantiaSkeleton(factionGeradores.getQuantiaSkeleton() + 1);
                                                        break;
                                                    }
                                                    case WITCH: {
                                                        factionGeradores.setQuantiaBruxa(factionGeradores.getQuantiaBruxa() + 1);
                                                        break;
                                                    }
                                                    case WITHER: {
                                                        factionGeradores.setQuantiaWither(factionGeradores.getQuantiaWither() + 1);
                                                        break;
                                                    }
                                                    case SHEEP: {
                                                        factionGeradores.setQuantiaSheep(factionGeradores.getQuantiaSheep() + 1);
                                                        break;
                                                    }
                                                    case OCELOT: {
                                                        break;
                                                    }
                                                    case GHAST: {
                                                        factionGeradores.setQuantiaGhast(factionGeradores.getQuantiaGhast() + 1);
                                                        break;
                                                    }
                                                    case SLIME: {
                                                        factionGeradores.setQuantiaSlime(factionGeradores.getQuantiaSlime() + 1);
                                                        break;
                                                    }
                                                }
                                            } else {
                                                LinkedHashMap<String, String> linkedList = new LinkedHashMap<>();
                                                linkedList.put(loc, creatureSpawner.getSpawnedType().name());
                                                locationsUser.getCache().put(FactionDisband.chunkSerializer(chunk1), linkedList);
                                                Bukkit.getOnlinePlayers().forEach(ps -> {
                                                    ps.sendBlockChange(s.getBlock().getLocation(), Material.AIR, (byte) 0);
                                                });
                                                switch (entityType2) {
                                                    case PIG_ZOMBIE: {
                                                        factionGeradores.setQuantiaPig_Zombie(factionGeradores.getQuantiaPig_Zombie() + 1);
                                                        break;
                                                    }
                                                    case ZOMBIE: {
                                                        factionGeradores.setQuantiaZombie(factionGeradores.getQuantiaZombie() + 1);
                                                        break;
                                                    }
                                                    case COW: {
                                                        factionGeradores.setQuantiaCow(factionGeradores.getQuantiaCow() + 1);
                                                        break;
                                                    }
                                                    case SPIDER: {
                                                        factionGeradores.setQuantiaSpider(factionGeradores.getQuantiaSpider() + 1);
                                                        break;
                                                    }
                                                    case ENDERMAN: {
                                                        factionGeradores.setQuantiaEnderman(factionGeradores.getQuantiaEnderman() + 1);
                                                        break;
                                                    }
                                                    case CREEPER: {
                                                        factionGeradores.setQuantiaCreeper(factionGeradores.getQuantiaCreeper() + 1);
                                                        break;
                                                    }
                                                    case BLAZE: {
                                                        factionGeradores.setQuantiaBlaze(factionGeradores.getQuantiaBlaze() + 1);
                                                        break;
                                                    }
                                                    case IRON_GOLEM: {
                                                        factionGeradores.setQuantiaIron_Golem(factionGeradores.getQuantiaIron_Golem() + 1);
                                                        break;
                                                    }
                                                    case MUSHROOM_COW: {
                                                        factionGeradores.setQuantiaMush(factionGeradores.getQuantiaMushroom() + 1);
                                                        break;
                                                    }
                                                    case MAGMA_CUBE: {
                                                        factionGeradores.setQuantiaMagma_Cube(factionGeradores.getQuantiaMagma_Cube() + 1);
                                                        break;
                                                    }
                                                    case SKELETON: {
                                                        factionGeradores.setQuantiaSkeleton(factionGeradores.getQuantiaSkeleton() + 1);
                                                        break;
                                                    }
                                                    case WITCH: {
                                                        factionGeradores.setQuantiaBruxa(factionGeradores.getQuantiaBruxa() + 1);
                                                        break;
                                                    }
                                                    case WITHER: {
                                                        factionGeradores.setQuantiaWither(factionGeradores.getQuantiaWither() + 1);
                                                        break;
                                                    }
                                                    case SHEEP: {
                                                        factionGeradores.setQuantiaSheep(factionGeradores.getQuantiaSheep() + 1);
                                                        break;
                                                    }
                                                    case OCELOT: {
                                                        break;
                                                    }
                                                    case GHAST: {
                                                        factionGeradores.setQuantiaGhast(factionGeradores.getQuantiaGhast() + 1);
                                                        break;
                                                    }
                                                    case SLIME: {
                                                        factionGeradores.setQuantiaSlime(factionGeradores.getQuantiaSlime() + 1);
                                                        break;
                                                    }
                                                }
                                                return;
                                            }
                                        });
                                    }
                                    if (sizearmazenados[0] == 0) {
                                        p.sendMessage("§cNenhum spawner para retirar.");
                                        return;
                                    } else {
                                        p.sendMessage("§aForam retirados §7" + sizearmazenados[0] + "§a geradores com sucesso.");
                                        new Storage().exportObject(locationsUser);
                                        FactionGeradoresDLL factionGeradoresDLL = new FactionGeradoresDLL(mplayer.getFactionId());
                                        factionGeradoresDLL.exportGeradores();
                                        return;
                                    }
                                } else {
                                    long x = locationsUser.getUse() + 60 * 60 * 1000;
                                    e.getWhoClicked().sendMessage("§cVocê está em coodlown, aguarde: " + TimeFormatter.format(System.currentTimeMillis() - x));
                                    e.getWhoClicked().closeInventory();
                                    return;
                                }
                            }
                            return;
                        }
                    }
                }
                if (e.getSlot() == 49) {
                    TypeInventory.openinventory(holder,p);
                    return;
                }
                return;
            }
            if (id == 3) {
                if (mplayer.hasFaction()) {
                    if (e.getSlot() == 49) {
                        FactionGeradores api = new FactionGeradores(mplayer.getFaction().getId());
                        final GuiHolder gui = new GuiHolder(2, mplayer.getFaction().getId());
                        final Inventory inv = Bukkit.createInventory((InventoryHolder) gui, 54, "[" + mplayer.getFaction().getTag() + "] Gerenciar");

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
                        upa.setLore((List) Arrays.asList("§eClique para armazenar todos os", "§egeradores que estão no" + "§e§lseu inventário."));
                        up.setItemMeta(upa);
                        inv.setItem(20, up);
                        final ItemStack down = Heads.DOWN;
                        final ItemMeta ddo = down.getItemMeta();
                        ddo.setDisplayName("§2Coletar todos");
                        ddo.setLore((List) Arrays.asList("§eClique para coletar todos", "§eos geradores que estão depositados", "§e§lpara seu inventário."));
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
                        lore.add("§e§ljá retirados anteriormente.");
                        itemMeta.setLore(lore);
                        map.setItemMeta(itemMeta);
                        inv.setItem(15, map);
                        ItemStack down2 = Heads.DOWN;
                        ItemMeta ddo2 = down2.getItemMeta();
                        ddo2.setDisplayName("§2Colocar todos");
                        ddo2.setLore((List) Arrays.asList("§eClique para coletar todos", "§eos geradores que estão nos chunks", "§e§lda facção para o f geradores."));
                        down2.setItemMeta(ddo2);
                        inv.setItem(33, down2);

                        ItemStack book = new ItemStack(Material.BOOK);
                        ItemMeta itemMeta1 = book.getItemMeta();
                        itemMeta1.setDisplayName("§2Opções extras");
                        itemMeta1.setLore(Arrays.asList("§eClique para colocar e retirar","§espawners especifícos das","§eterras da facção."));
                        book.setItemMeta(itemMeta1);
                        inv.setItem(22,book);
                        up = Heads.UP;
                        upa = up.getItemMeta();
                        upa.setDisplayName("§2Retirar todos");
                        upa.setLore((List) Arrays.asList("§eClique para retirar todos os", "§egeradores que estão nas", "§e§lterras da facção"));
                        up.setItemMeta(upa);
                        inv.setItem(24, up);
                        p.openInventory(inv);
                        api.setAbriuPorUltimo(p.getName());
                        return;
                    }
                    if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                        EntityType entityType = holder.getSlots().get(e.getSlot());
                        holder.setId(4);
                        holder.setEntityType(entityType);
                        FactionGeradores api = new FactionGeradores(mplayer.getFaction().getId());
                        api.setAbriuPorUltimo(p.getName());
                        Inventory inv = Bukkit.createInventory((InventoryHolder) holder, 54, "[" + mplayer.getFaction().getTag() + "] Gerenciar Spawners");
                        ItemStack itemStack = e.getCurrentItem().clone();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setLore(Arrays.asList("§7Aqui você pode manusear", "§7e pode colocar spawners retirados", "§7anteriormente ou, retirar spawners das terras", "§7da facção atualmente."));
                        itemMeta.setDisplayName("§aGerenciamento de Spawners");
                        itemStack.setItemMeta(itemMeta);
                        inv.setItem(13, itemStack);
                        ItemStack down2 = Heads.DOWN;
                        ItemMeta ddo2 = down2.getItemMeta();
                        ddo2.setDisplayName("§2Colocar todos");
                        ddo2.setLore((List) Arrays.asList("§eClique para coletar todos", "§eos geradores que estão nos chunks", "§e§lda facção para o f geradores."));
                        down2.setItemMeta(ddo2);
                        inv.setItem(31, down2);
                        ItemStack up = Heads.UP;
                        ddo2 = up.getItemMeta();
                        ddo2.setDisplayName("§2Retirar todos");
                        ddo2.setLore((List) Arrays.asList("§eClique para retirar todos os", "§egeradores que estão nas", "§e§lterras da facção"));
                        up.setItemMeta(ddo2);
                        inv.setItem(22, up);
                        inv.setItem(49, new ItemBuilder(Material.ARROW).setName("§cVoltar").setLore("§7Clique para voltar.").toItemStack());
                        p.openInventory(inv);
                        Main.cache.put(mplayer.getFaction(), p);
                        return;
                    }
                }
                return;
            }
        }
    }

    public static CreatureSpawner getspawner (BlockState boo){
        CreatureSpawner creatureSpawner = (CreatureSpawner) boo;
        return  creatureSpawner;
    }

}