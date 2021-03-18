package br.jotaentity.geradores.listeners;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objeto.LocationsUser;
import br.jotaentity.geradores.objeto.Storage;
import br.jotaentity.geradores.objetos.FactionGeradores;
import br.jotaentity.geradores.objetos.FactionGeradoresDLL;
import br.jotaentity.geradores.utils.*;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.util.TimeFormatter;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class InventoryClickId2 implements Listener {

    private static final SimpleDateFormat SDF;

    static {
        SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof GuiHolder) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            final Player p = (Player) e.getWhoClicked();
            final MPlayer mplayer = MPlayer.get((Object) p);
            if (mplayer.hasFaction()) {
                final int slot = e.getRawSlot();
                final GuiHolder holder = (GuiHolder) e.getInventory().getHolder();
                final int id = holder.getId();
                final Faction fac = mplayer.getFaction();
                final FactionGeradores api = new FactionGeradores(fac.getId());
                if (mplayer.getRole() == Rel.OFFICER || mplayer.getRole() == Rel.LEADER || mplayer.getRole() == Rel.SUBLIDER) {
                    if (id == 2) {
                        if (e.getInventory().getSize() < slot) {
                            return;
                        }
                        if (slot == 49) {
                            PlayerCommand.openInventory(p, true);
                            return;
                        }
                        if (e.getSlot() == 22) {
                            TypeInventory.openinventory(holder, p);
                            return;
                        }
                        if (e.getSlot() == 24) {
                            if (fac.isInAttack()) {
                                p.sendMessage("§cVocê não pode fazer isto enquanto sua facção estiver sobre ataque;");
                                return;
                            }
                            if (Main.cacheloc.containsKey(mplayer.getFactionName())) {
                                LocationsUser locationsUser = Main.cacheloc.get(mplayer.getFactionName());
                                if (mplayer.getRole() == Rel.OFFICER || mplayer.getRole() == Rel.LEADER || mplayer.getRole() == Rel.SUBLIDER) {
                                    Set<PS> chunks = BoardColl.get().getChunks(mplayer.getFaction());
                                    FactionGeradores factionGeradores = new FactionGeradores(fac.getId());
                                    int vixisize = 0;
                                    final int[] sizearmazenados = {0};
                                    mplayer.getPlayer().sendMessage("§aVerificando...");
                                    if (locationsUser.getUse() + 60 * 60 * 1000 < System.currentTimeMillis()) {
                                        Set<PS> chunk2 = chunks.stream().filter(s -> Arrays.stream(s.asBukkitChunk().getTileEntities()).filter(spawner -> spawner.getType() == Material.MOB_SPAWNER).collect(Collectors.toSet()).size() > 0).collect(Collectors.toSet());
                                        for (PS chunk : chunk2) {
                                            Chunk chunk1 = chunk.asBukkitChunk();
                                            Set<BlockState> blocks = Arrays.stream(chunk1.getTileEntities()).filter(s -> s.getType() == Material.MOB_SPAWNER).collect(Collectors.toSet());
                                            if (blocks.isEmpty()) {
                                                vixisize = vixisize + 1;
                                                continue;
                                            } else {
                                                blocks.forEach(s -> {
                                                    sizearmazenados[0] = sizearmazenados[0] + 1;
                                                    CreatureSpawner creatureSpawner = (CreatureSpawner) s;
                                                    EntityType entityType = creatureSpawner.getSpawnedType();
                                                    creatureSpawner.getBlock().setType(Material.AIR);
                                                    Bukkit.getOnlinePlayers().forEach(ps -> {
                                                        ps.sendBlockChange(s.getBlock().getLocation(), Material.AIR, (byte) 0);
                                                    });
                                                    String loc = LocationEncoder.getSerializedLocation(creatureSpawner.getBlock().getLocation());
                                                    if (locationsUser.getCache().containsKey(FactionDisband.chunkSerializer(chunk1))) {
                                                        if (!locationsUser.getCache().get(FactionDisband.chunkSerializer(chunk1)).containsKey(loc)) {
                                                            locationsUser.getCache().get(FactionDisband.chunkSerializer(chunk1)).put(loc, creatureSpawner.getSpawnedType().name());
                                                        } else {
                                                            locationsUser.getCache().get(FactionDisband.chunkSerializer(chunk1)).replace(loc, creatureSpawner.getSpawnedType().name());
                                                        }
                                                        switch (entityType) {
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
                                                        switch (entityType) {
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
                                                    }
                                                });
                                            }
                                        }
                                        if (vixisize == chunks.size() || sizearmazenados[0] == 0) {
                                            mplayer.getPlayer().sendMessage("§cVocê não tem spawners nas terras.");
                                            return;
                                        } else {
                                            mplayer.getPlayer().sendMessage("§aForam armazenados §7" + sizearmazenados[0] + "§a geradores com sucesso.");
                                        }
                                        FactionGeradoresDLL factionGeradoresDLL = new FactionGeradoresDLL(mplayer.getId());
                                        factionGeradoresDLL.exportGeradores();
                                        new Storage().exportObject(locationsUser);
                                    } else{
                                        long x = locationsUser.getUse() + 60 * 60 * 1000;
                                        e.getWhoClicked().sendMessage("§cVocê está em coodlown, aguarde: " + TimeFormatter.format(System.currentTimeMillis() - x));
                                        return;
                                    }
                                }
                            } else {
                                Storage storage = new Storage();
                                storage.importObject(fac.getName());
                            }
                            return;
                        }
                        if (e.getSlot() == 33) {
                            if (fac.isInAttack()) {
                                p.sendMessage("§cVocê não pode fazer isto enquanto sua facção estiver sobre ataque;");
                                return;
                            }
                            if (Main.cacheloc.containsKey(mplayer.getFactionName())) {
                                LocationsUser locationsUser = Main.cacheloc.get(mplayer.getFactionName());
                                if (locationsUser.getUse() + 60 * 60 * 1000 < System.currentTimeMillis()) {
                                    locationsUser.setUse(System.currentTimeMillis());
                                    FactionGeradores factionGeradores = new FactionGeradores(fac.getId());
                                    int spawners = 0;
                                    for (Map.Entry<String, LinkedHashMap<String, String>> hash : locationsUser.getCache().entrySet()) {
                                        for (Map.Entry<String, String> loc : hash.getValue().entrySet()) {
                                            String locs = loc.getKey();
                                            Location location = LocationEncoder.getDeserializedLocation(locs);
                                            if (location.getBlock().getType() != Material.AIR) continue;
                                            PS ps2 = PS.valueOf(location);
                                            Faction faction2 = BoardColl.get().getFactionAt(ps2);
                                            if (faction2.getId().equalsIgnoreCase(fac.getId())) {
                                                EntityType entityType = EntityType.valueOf(loc.getValue());
                                                switch (entityType) {
                                                    case PIG_ZOMBIE: {
                                                        if (factionGeradores.getQuantiaPig_Zombie() >= 1) {
                                                            factionGeradores.setQuantiaPig_Zombie(factionGeradores.getQuantiaPig_Zombie() - 1);
                                                            spawners++;
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case ZOMBIE: {
                                                        if (factionGeradores.getQuantiaZombie() >= 1) {
                                                            factionGeradores.setQuantiaZombie(factionGeradores.getQuantiaZombie() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            spawners++;
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case COW: {
                                                        if (factionGeradores.getQuantiaCow() >= 1) {
                                                            factionGeradores.setQuantiaCow(factionGeradores.getQuantiaCow() - 1);
                                                            spawners++;
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case SPIDER: {
                                                        if (factionGeradores.getQuantiaSpider() >= 1) {
                                                            factionGeradores.setQuantiaSpider(factionGeradores.getQuantiaSpider() - 1);
                                                            spawners++;
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case ENDERMAN: {
                                                        if (factionGeradores.getQuantiaEnderman() >= 1) {
                                                            factionGeradores.setQuantiaEnderman(factionGeradores.getQuantiaEnderman() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            spawners++;
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case CREEPER: {
                                                        if (factionGeradores.getQuantiaCreeper() >= 1) {
                                                            factionGeradores.setQuantiaCreeper(factionGeradores.getQuantiaCreeper() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            spawners++;
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case BLAZE: {
                                                        if (factionGeradores.getQuantiaBlaze() >= 1) {
                                                            factionGeradores.setQuantiaBlaze(factionGeradores.getQuantiaBlaze() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            spawners++;
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case IRON_GOLEM: {
                                                        if (factionGeradores.getQuantiaIron_Golem() >= 1) {
                                                            factionGeradores.setQuantiaIron_Golem(factionGeradores.getQuantiaIron_Golem() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            spawners++;
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case MUSHROOM_COW: {
                                                        if (factionGeradores.getQuantiaMushroom() >= 1) {
                                                            factionGeradores.setQuantiaMush(factionGeradores.getQuantiaMushroom() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            spawners++;
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case MAGMA_CUBE: {
                                                        if (factionGeradores.getQuantiaMagma_Cube() >= 1) {
                                                            factionGeradores.setQuantiaMagma_Cube(factionGeradores.getQuantiaMagma_Cube() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            spawners++;
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case SKELETON: {
                                                        if (factionGeradores.getQuantiaSkeleton() >= 1) {
                                                            factionGeradores.setQuantiaSkeleton(factionGeradores.getQuantiaSkeleton() - 1);
                                                            location.getBlock().setType(Material.MOB_SPAWNER);
                                                            CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                            creatureSpawner.setSpawnedType(entityType);
                                                            spawners++;
                                                            creatureSpawner.update();
                                                        }
                                                        break;
                                                    }
                                                    case WITCH: {
                                                        if (factionGeradores.getQuantiaBruxa() >= 1)
                                                            factionGeradores.setQuantiaBruxa(factionGeradores.getQuantiaBruxa() - 1);
                                                        location.getBlock().setType(Material.MOB_SPAWNER);
                                                        CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                        creatureSpawner.setSpawnedType(entityType);
                                                        creatureSpawner.update();
                                                        spawners++;
                                                        break;
                                                    }
                                                    case WITHER: {
                                                        if (factionGeradores.getQuantiaWither() >= 1)
                                                            factionGeradores.setQuantiaWither(factionGeradores.getQuantiaWither() - 1);
                                                        location.getBlock().setType(Material.MOB_SPAWNER);
                                                        CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                        creatureSpawner.setSpawnedType(entityType);
                                                        spawners++;
                                                        creatureSpawner.update();
                                                        break;
                                                    }
                                                    case SHEEP: {
                                                        if (factionGeradores.getQuantiaSheep() >= 1)
                                                            factionGeradores.setQuantiaSheep(factionGeradores.getQuantiaSheep() - 1);
                                                        location.getBlock().setType(Material.MOB_SPAWNER);
                                                        CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                        creatureSpawner.setSpawnedType(entityType);
                                                        creatureSpawner.update();
                                                        spawners++;
                                                        break;
                                                    }
                                                    case OCELOT: {
                                                        break;
                                                    }
                                                    case GHAST: {
                                                        if (factionGeradores.getQuantiaGhast() >= 1)
                                                            factionGeradores.setQuantiaGhast(factionGeradores.getQuantiaGhast() - 1);
                                                        location.getBlock().setType(Material.MOB_SPAWNER);
                                                        CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                        creatureSpawner.setSpawnedType(entityType);
                                                        spawners++;
                                                        creatureSpawner.update();
                                                        break;
                                                    }
                                                    case SLIME: {
                                                        if (factionGeradores.getQuantiaSlime() >= 1)
                                                            factionGeradores.setQuantiaSlime(factionGeradores.getQuantiaSlime() - 1);
                                                        location.getBlock().setType(Material.MOB_SPAWNER);
                                                        CreatureSpawner creatureSpawner = (CreatureSpawner) location.getBlock().getState();
                                                        creatureSpawner.setSpawnedType(entityType);
                                                        spawners++;
                                                        creatureSpawner.update();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    FactionGeradoresDLL factionGeradoresDLL = new FactionGeradoresDLL(mplayer.getFaction().getId());
                                    factionGeradoresDLL.exportGeradores();
                                    if (spawners == 0) {
                                        p.sendMessage("§cNão foram colocados nenhum gerador.");
                                    } else {
                                        p.sendMessage("§aForam colocados §7" + spawners + " §ageradores com sucesso.");
                                        return;
                                    }
                                } else {
                                    long x = locationsUser.getUse() + 60 * 60 * 1000;
                                    e.getWhoClicked().sendMessage("§cVocê está em coodlown, aguarde: " + TimeFormatter.format(System.currentTimeMillis() - x));
                                    return;
                                }
                            }
                        }
                        if (e.getSlot() == 20) {
                            final HashMap<EntityType, Integer> entidades = new HashMap<EntityType, Integer>();
                            int totalAmount = 0;
                            ItemStack[] contents;
                            for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; ++i) {
                                final ItemStack item2 = contents[i];
                                if (item2 != null && (ItemMetadata.hasMetadata(item2, "SpawnerType"))) {
                                    final int amount3 = item2.getAmount();
                                    EntityType entity3 = EntityType.valueOf(ItemMetadata.getMetadata(item2, "SpawnerType").toString());
                                    if (entity3 != null) {
                                        if (entidades.containsKey(entity3)) {
                                            entidades.replace(entity3, entidades.get(entity3) + amount3);
                                        } else {
                                            entidades.put(entity3, amount3);
                                        }
                                        totalAmount += amount3;
                                        switch (entity3) {
                                            case BLAZE: {
                                                api.setQuantiaBlaze(api.getQuantiaBlaze() + amount3);
                                                break;
                                            }
                                            case CAVE_SPIDER: {
                                                api.setQuantiaCave_Spider(api.getQuantiaCave_Spider() + amount3);
                                                break;
                                            }
                                            case CHICKEN: {
                                                api.setQuantiaChicken(api.getQuantiaChicken() + amount3);
                                                break;
                                            }
                                            case COW: {
                                                api.setQuantiaCow(api.getQuantiaCow() + amount3);
                                                break;
                                            }
                                            case CREEPER: {
                                                api.setQuantiaCreeper(api.getQuantiaCreeper() + amount3);
                                                break;
                                            }
                                            case ENDERMAN: {
                                                api.setQuantiaEnderman(api.getQuantiaEnderman() + amount3);
                                                break;
                                            }
                                            case IRON_GOLEM: {
                                                api.setQuantiaIron_Golem(api.getQuantiaIron_Golem() + amount3);
                                                break;
                                            }
                                            case MAGMA_CUBE: {
                                                api.setQuantiaMagma_Cube(api.getQuantiaMagma_Cube() + amount3);
                                                break;
                                            }
                                            case PIG: {
                                                api.setQuantiaPig(api.getQuantiaPig() + amount3);
                                                break;
                                            }
                                            case PIG_ZOMBIE: {
                                                api.setQuantiaPig_Zombie(api.getQuantiaPig_Zombie() + amount3);
                                                break;
                                            }
                                            case SHEEP: {
                                                api.setQuantiaSheep(api.getQuantiaSheep() + amount3);
                                                break;
                                            }
                                            case SKELETON: {
                                                api.setQuantiaSkeleton(api.getQuantiaSkeleton() + amount3);
                                                break;
                                            }
                                            case SLIME: {
                                                api.setQuantiaSlime(api.getQuantiaSlime() + amount3);
                                                break;
                                            }
                                            case SPIDER: {
                                                api.setQuantiaSpider(api.getQuantiaSpider() + amount3);
                                                break;
                                            }
                                            case WITHER: {
                                                api.setQuantiaWither(api.getQuantiaWither() + amount3);
                                                break;
                                            }
                                            case ZOMBIE: {
                                                api.setQuantiaZombie(api.getQuantiaZombie() + amount3);
                                                break;
                                            }
                                            case MUSHROOM_COW: {
                                                api.setQuantiaMush(api.getQuantiaMushroom() + amount3);
                                                break;
                                            }
                                            case WITCH: {
                                                api.setQuantiaBruxa(api.getQuantiaBruxa() + amount3);
                                                break;
                                            }
                                            case GHAST: {
                                                api.setQuantiaGhast(api.getQuantiaGhast() + amount3);
                                                break;
                                            }
                                            case HORSE: {
                                                api.setQuantiaCavalo(api.getQuantiaCavalo() + amount3);
                                                break;
                                            }
                                        }
                                        this.removeInventoryItems((Inventory) p.getInventory(), Material.MOB_SPAWNER, amount3);
                                    }
                                }
                            }
                            if (!entidades.isEmpty()) {
                                for (final Map.Entry<EntityType, Integer> en : entidades.entrySet()) {
                                    final String message = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &aarmazenou &7" + en.getValue() + " " + EntityName.valueOf(en.getKey()).getName();
                                    api.addLog(message);
                                }
                                final String msg3 = (totalAmount == 1) ? " gerador armazenado." : " geradores armazenados.";
                                p.sendMessage("§e" + totalAmount + msg3);
                                PlayerCommand.openInventory(p, true);
                            } else {
                                p.sendMessage("§cVocê não possui geradores para armazenar.");
                                p.closeInventory();
                            }
                            FactionGeradoresDLL factionGeradoresDLL = new FactionGeradoresDLL(fac.getId());
                             factionGeradoresDLL.exportGeradores();
                            return;
                        }
                        if (e.getSlot() == 29) {
                            if (api.countGeradores() == 0) {
                                p.sendMessage("§cSua fac\u00e7\u00e3o n\u00e3o possui geradores armazenados.");
                                p.closeInventory();
                                return;
                            }
                            if (p.getInventory().firstEmpty() == -1) {
                                p.sendMessage("§cInventário lotado.");
                                return;
                            }
                            int totalAmount2 = 0;
                            final HashMap<EntityType, Integer> entidades2 = new HashMap<EntityType, Integer>();
                            final String cmd2 = Main.getInstance().config.getConfig().getString("Comando-Para-Devolver-Spawner").replace("@jogador", p.getName());
                            int podecoletar = 0;
                            for (ItemStack content : p.getInventory().getContents()) {
                                if (content == null) {
                                    podecoletar += 64;
                                }
                            }
                            if (api.getQuantiaBlaze() > 0) {
                                entidades2.put(EntityType.BLAZE, api.getQuantiaBlaze());
                                if (api.getQuantiaBlaze() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", EntityType.BLAZE.name()).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaBlaze();
                                    podecoletar = api.getQuantiaBlaze() - podecoletar;
                                    api.setQuantiaBlaze(api.getQuantiaBlaze() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaBlaze();
                                    Bukkit.getConsoleSender().sendMessage("§eDebug [BLAZE] > " + (quantiaatual - api.getQuantiaBlaze()));
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados. 1";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    podecoletar = podecoletar - api.getQuantiaBlaze();
                                    totalAmount2 += api.getQuantiaBlaze();
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", EntityType.BLAZE.name()).replace("@quantia", String.valueOf(api.getQuantiaBlaze())));
                                    Bukkit.getConsoleSender().sendMessage("§eDebug [BLAZE] > " + (api.getQuantiaBlaze()));
                                    api.setQuantiaBlaze(0);
                                }
                            }
                            if (api.getQuantiaMushroom() > 0) {
                                entidades2.put(EntityType.MUSHROOM_COW, api.getQuantiaMushroom());
                                if (api.getQuantiaMushroom() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", (EntityType.MUSHROOM_COW.name())).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaMushroom();
                                    podecoletar = api.getQuantiaMushroom() - podecoletar;
                                    api.setQuantiaMush(api.getQuantiaMushroom() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaMushroom();
                                    Bukkit.getConsoleSender().sendMessage("§eDebug [MUSH] > " + (quantiaatual - api.getQuantiaBlaze()));
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    podecoletar = podecoletar - api.getQuantiaMushroom();
                                    totalAmount2 += api.getQuantiaMushroom();
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", (EntityType.MUSHROOM_COW.name())).replace("@quantia", String.valueOf(api.getQuantiaMushroom())));
                                    api.setQuantiaMush(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            Bukkit.getConsoleSender().sendMessage("§c[DEBUG] > COLETAR: " + podecoletar);
                            if (api.getQuantiaBruxa() > 0) {
                                entidades2.put(EntityType.WITCH, api.getQuantiaBruxa());
                                if (api.getQuantiaBruxa() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", (EntityType.WITCH.name())).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaBruxa();
                                    podecoletar = api.getQuantiaBruxa() - podecoletar;
                                    api.setQuantiaBruxa(api.getQuantiaBruxa() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaBruxa();
                                    Bukkit.getConsoleSender().sendMessage("§eDebug [BRU] > " + (quantiaatual - api.getQuantiaBlaze()));
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    podecoletar = podecoletar - api.getQuantiaBruxa();
                                    totalAmount2 += api.getQuantiaBruxa();
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", (EntityType.MUSHROOM_COW.name())).replace("@quantia", String.valueOf(api.getQuantiaBruxa())));
                                    api.setQuantiaBruxa(0);
                                }
                            }
                            Bukkit.getConsoleSender().sendMessage("§c[DEBUG] > COLETAR: " + podecoletar);
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaGhast() > 0) {
                                entidades2.put(EntityType.GHAST, api.getQuantiaGhast());
                                if (api.getQuantiaGhast() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", (EntityType.GHAST.name())).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaGhast();
                                    podecoletar = api.getQuantiaGhast() - podecoletar;
                                    api.setQuantiaGhast(api.getQuantiaGhast() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaGhast();
                                    Bukkit.getConsoleSender().sendMessage("§eDebug [GAST] > " + (quantiaatual - api.getQuantiaBlaze()));
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    podecoletar = podecoletar - api.getQuantiaGhast();
                                    totalAmount2 += api.getQuantiaGhast();
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", (EntityType.GHAST.name())).replace("@quantia", String.valueOf(api.getQuantiaGhast())));
                                    api.setQuantiaGhast(0);
                                }
                            }
                            Bukkit.getConsoleSender().sendMessage("§c[DEBUG] > COLETAR: " + podecoletar);
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaCavalo() > 0) {
                                entidades2.put(EntityType.HORSE, api.getQuantiaCavalo());
                                if (api.getQuantiaCavalo() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.HORSE)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaCavalo();
                                    podecoletar = api.getQuantiaCavalo() - podecoletar;
                                    api.setQuantiaCavalo(api.getQuantiaCavalo() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaCavalo();
                                    Bukkit.getConsoleSender().sendMessage("§eDebug [CAVLAO] > " + (quantiaatual - api.getQuantiaBlaze()));
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.HORSE)).replace("@quantia", String.valueOf(api.getQuantiaCavalo())));
                                    podecoletar = podecoletar - api.getQuantiaCavalo();
                                    totalAmount2 += api.getQuantiaCavalo();
                                    api.setQuantiaCavalo(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaCave_Spider() > 0) {
                                entidades2.put(EntityType.CAVE_SPIDER, api.getQuantiaCave_Spider());
                                if (api.getQuantiaCave_Spider() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CAVE_SPIDER)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaSpider();
                                    podecoletar = api.getQuantiaSpider() - podecoletar;
                                    api.setQuantiaCave_Spider(api.getQuantiaSpider() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaSpider();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CAVE_SPIDER)).replace("@quantia", String.valueOf(api.getQuantiaCave_Spider())));
                                    podecoletar = podecoletar - api.getQuantiaSpider();
                                    totalAmount2 += api.getQuantiaSpider();
                                    api.setQuantiaCave_Spider(0);
                                }
                            }
                            Bukkit.getConsoleSender().sendMessage("§c[DEBUG] > COLETAR: " + podecoletar);
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaChicken() > 0) {
                                entidades2.put(EntityType.CHICKEN, api.getQuantiaChicken());
                                if (api.getQuantiaChicken() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CHICKEN)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaChicken();
                                    podecoletar = api.getQuantiaChicken() - podecoletar;
                                    api.setQuantiaChicken(api.getQuantiaChicken() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaChicken();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CHICKEN)).replace("@quantia", String.valueOf(api.getQuantiaChicken())));
                                    podecoletar = podecoletar - api.getQuantiaChicken();
                                    totalAmount2 += api.getQuantiaChicken();
                                    api.setQuantiaChicken(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaCow() > 0) {
                                entidades2.put(EntityType.COW, api.getQuantiaCow());
                                if (api.getQuantiaCow() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.COW)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaCow();
                                    podecoletar = api.getQuantiaCow() - podecoletar;
                                    api.setQuantiaCow(api.getQuantiaCow() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaCow();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CHICKEN)).replace("@quantia", String.valueOf(api.getQuantiaCow())));
                                    podecoletar = podecoletar - api.getQuantiaCow();
                                    totalAmount2 += api.getQuantiaCow();
                                    api.setQuantiaCow(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaCreeper() > 0) {
                                entidades2.put(EntityType.CREEPER, api.getQuantiaCreeper());
                                if (api.getQuantiaCreeper() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CREEPER)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaCreeper();
                                    podecoletar = api.getQuantiaCreeper() - podecoletar;
                                    api.setQuantiaCreeper(api.getQuantiaCreeper() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaCreeper();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.CHICKEN)).replace("@quantia", String.valueOf(api.getQuantiaCreeper())));
                                    podecoletar = podecoletar - api.getQuantiaCreeper();
                                    totalAmount2 += api.getQuantiaCreeper();
                                    api.setQuantiaCreeper(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaEnderman() > 0) {
                                entidades2.put(EntityType.ENDERMAN, api.getQuantiaEnderman());
                                if (api.getQuantiaEnderman() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.ENDERMAN)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaEnderman();
                                    podecoletar = api.getQuantiaEnderman() - podecoletar;
                                    api.setQuantiaEnderman(api.getQuantiaEnderman() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaEnderman();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.ENDERMAN)).replace("@quantia", String.valueOf(api.getQuantiaEnderman())));
                                    podecoletar = podecoletar - api.getQuantiaEnderman();
                                    totalAmount2 += api.getQuantiaEnderman();
                                    api.setQuantiaEnderman(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaIron_Golem() > 0) {
                                entidades2.put(EntityType.IRON_GOLEM, api.getQuantiaIron_Golem());
                                if (api.getQuantiaIron_Golem() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.IRON_GOLEM)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaIron_Golem();
                                    podecoletar = api.getQuantiaIron_Golem() - podecoletar;
                                    api.setQuantiaIron_Golem(api.getQuantiaIron_Golem() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaIron_Golem();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados. ";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.IRON_GOLEM)).replace("@quantia", String.valueOf(api.getQuantiaIron_Golem())));
                                    podecoletar = podecoletar - api.getQuantiaIron_Golem();
                                    totalAmount2 += api.getQuantiaIron_Golem();
                                    api.setQuantiaIron_Golem(0);
                                }
                            }
                            if (podecoletar <= 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaMagma_Cube() > 0) {
                                entidades2.put(EntityType.MAGMA_CUBE, api.getQuantiaMagma_Cube());
                                if (api.getQuantiaMagma_Cube() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.MAGMA_CUBE)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaMagma_Cube();
                                    podecoletar = api.getQuantiaMagma_Cube() - podecoletar;
                                    api.setQuantiaMagma_Cube(api.getQuantiaMagma_Cube() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaMagma_Cube();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.MAGMA_CUBE)).replace("@quantia", String.valueOf(api.getQuantiaMagma_Cube())));
                                    podecoletar = podecoletar - api.getQuantiaMagma_Cube();
                                    totalAmount2 += api.getQuantiaMagma_Cube();
                                    api.setQuantiaMagma_Cube(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaPig_Zombie() > 0) {
                                entidades2.put(EntityType.PIG_ZOMBIE, api.getQuantiaPig_Zombie());
                                if (api.getQuantiaPig_Zombie() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.PIG_ZOMBIE)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaPig_Zombie();
                                    podecoletar = api.getQuantiaPig_Zombie() - podecoletar;
                                    api.setQuantiaPig_Zombie(api.getQuantiaPig_Zombie() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaPig_Zombie();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados. ";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.PIG_ZOMBIE)).replace("@quantia", String.valueOf(api.getQuantiaPig_Zombie())));
                                    podecoletar = podecoletar - api.getQuantiaPig_Zombie();
                                    totalAmount2 += api.getQuantiaPig_Zombie();
                                    api.setQuantiaPig_Zombie(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaPig() > 0) {
                                entidades2.put(EntityType.PIG, api.getQuantiaPig());
                                if (api.getQuantiaPig() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.PIG)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaPig();
                                    podecoletar = api.getQuantiaPig() - podecoletar;
                                    api.setQuantiaPig(api.getQuantiaPig() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaPig();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados. ";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.PIG)).replace("@quantia", String.valueOf(api.getQuantiaPig())));
                                    podecoletar = podecoletar - api.getQuantiaPig();
                                    totalAmount2 += api.getQuantiaPig();
                                    api.setQuantiaPig(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaSheep() > 0) {
                                entidades2.put(EntityType.SHEEP, api.getQuantiaSheep());
                                if (api.getQuantiaSheep() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SHEEP)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaSheep();
                                    podecoletar = api.getQuantiaSheep() - podecoletar;
                                    api.setQuantiaSheep(api.getQuantiaSheep() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaSheep();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados. ";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SHEEP)).replace("@quantia", String.valueOf(api.getQuantiaSheep())));
                                    podecoletar = podecoletar - api.getQuantiaSheep();
                                    totalAmount2 += api.getQuantiaSheep();
                                    api.setQuantiaSheep(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaSkeleton() > 0) {
                                entidades2.put(EntityType.SKELETON, api.getQuantiaSkeleton());
                                if (api.getQuantiaSkeleton() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SKELETON)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaSkeleton();
                                    podecoletar = api.getQuantiaSkeleton() - podecoletar;
                                    api.setQuantiaSkeleton(api.getQuantiaSkeleton() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaSkeleton();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SKELETON)).replace("@quantia", String.valueOf(api.getQuantiaSkeleton())));
                                    podecoletar = podecoletar - api.getQuantiaSkeleton();
                                    totalAmount2 += api.getQuantiaSkeleton();
                                    api.setQuantiaSkeleton(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            Bukkit.getConsoleSender().sendMessage("§c[DEBUG] > COLETAR: " + podecoletar);
                            if (api.getQuantiaSlime() > 0) {
                                entidades2.put(EntityType.SLIME, api.getQuantiaSlime());
                                if (api.getQuantiaSlime() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SLIME)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaSlime();
                                    podecoletar = api.getQuantiaSlime() - podecoletar;
                                    api.setQuantiaSlime(api.getQuantiaSlime() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaSlime();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SLIME)).replace("@quantia", String.valueOf(api.getQuantiaSlime())));
                                    podecoletar = podecoletar - api.getQuantiaSlime();
                                    totalAmount2 += api.getQuantiaSlime();
                                    api.setQuantiaSlime(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaSpider() > 0) {
                                entidades2.put(EntityType.SPIDER, api.getQuantiaSpider());
                                if (api.getQuantiaSpider() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SPIDER)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaSpider();
                                    podecoletar = api.getQuantiaSpider() - podecoletar;
                                    api.setQuantiaSpider(api.getQuantiaSpider() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaSpider();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.SPIDER)).replace("@quantia", String.valueOf(api.getQuantiaSpider())));
                                    podecoletar = podecoletar - api.getQuantiaSpider();
                                    totalAmount2 += api.getQuantiaSpider();
                                    api.setQuantiaSpider(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaWither() > 0) {
                                entidades2.put(EntityType.WITHER, api.getQuantiaWither());
                                if (api.getQuantiaWither() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.WITHER)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaWither();
                                    podecoletar = api.getQuantiaWither() - podecoletar;
                                    api.setQuantiaWither(api.getQuantiaWither() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaWither();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        // 4608, por?m, eu quero q d? apenas oq puder dar, ent a msg vai com o pode coletar
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados.";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.WITHER)).replace("@quantia", String.valueOf(api.getQuantiaWither())));
                                    podecoletar = podecoletar - api.getQuantiaWither();
                                    totalAmount2 += api.getQuantiaWither();
                                    api.setQuantiaWither(0);
                                }
                            }
                            if (podecoletar == 0) {
                                for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                    final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                    api.addLog(message2);
                                }
                                final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados.";
                                p.sendMessage("§e" + totalAmount2 + msg4);
                                p.closeInventory();
                                return;
                            }
                            if (api.getQuantiaZombie() > 0) {
                                entidades2.put(EntityType.ZOMBIE, api.getQuantiaZombie());
                                if (api.getQuantiaZombie() > podecoletar) {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.ZOMBIE)).replace("@quantia", String.valueOf(podecoletar)));
                                    int quantiaatual = api.getQuantiaZombie();
                                    podecoletar = api.getQuantiaZombie() - podecoletar;
                                    api.setQuantiaZombie(api.getQuantiaZombie() - podecoletar);
                                    totalAmount2 += quantiaatual - api.getQuantiaZombie();
                                    for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                        final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                        api.addLog(message2);
                                    }
                                    final String msg4 = (totalAmount2 - podecoletar == 1) ? " gerador coletado." : " geradores coletados. ";
                                    p.sendMessage("§e" + totalAmount2 + msg4);
                                    p.closeInventory();
                                    return;
                                } else {
                                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd2.replace("@tipo", this.getConfigEntityId(EntityType.ZOMBIE)).replace("@quantia", String.valueOf(api.getQuantiaZombie())));
                                    podecoletar = podecoletar - api.getQuantiaZombie();
                                    totalAmount2 += api.getQuantiaZombie();
                                    api.setQuantiaZombie(0);
                                }
                            }
                            for (final Map.Entry<EntityType, Integer> en2 : entidades2.entrySet()) {
                                final String message2 = "&7" + InventoryClick.SDF.format(Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime()) + " " + mplayer.getRole().getPrefix() + p.getName() + " &ccoletou &7" + en2.getValue() + " " + EntityName.valueOf(en2.getKey()).getName();
                                api.addLog(message2);
                            }
                            final String msg4 = (totalAmount2 == 1) ? " gerador coletado." : " geradores coletados. ";
                            p.sendMessage("§e" + totalAmount2 + msg4);
                            p.closeInventory();
                            FactionGeradoresDLL factionGeradoresDLL = new FactionGeradoresDLL(fac.getId());
                            factionGeradoresDLL.exportGeradores();
                        }
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