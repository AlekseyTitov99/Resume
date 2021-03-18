package com.dungeons.system.dungeon;

import com.dungeons.system.Main;
import com.dungeons.system.api.ItemBuilder;
import com.dungeons.system.api.ItemEncoder;
import com.dungeons.system.api.LocationEncoder;
import com.dungeons.system.api.Reflections;
import com.dungeons.system.objeto.Fase;
import com.dungeons.system.objeto.Party;
import com.dungeons.system.scoreboard.ScoreBoard;
import com.dungeons.system.util.TitleAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.Controllable;
import net.citizensnpcs.trait.MountTrait;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.util.NMS;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Chest;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class Dungeon {

    private String partyname;
    private Party party;
    private Fase currentfase;
    private String lobbylocation;
    private ArrayList<String> jogadores;
    private ArrayList<String> dead;
    private ArrayList<String> ready;
    private ArrayList<String> waitingfor;
    private ArrayList<LivingEntity> entities;
    private ArrayList<Hologram> holograms;
    private HashMap<String, Integer> pepitas;
    private List<String> itens_iniciais;
    private HashMap<String,ItemStack[][]> itemsafterdead;
    private World world;
    private int fase;
    private int vendedorid;
    private int horsenumber;

    public Dungeon(Party festa, ArrayList<String> players, World mundo) {
        this.party = festa;
        this.world = mundo;
        this.jogadores = players;
        this.dead = new ArrayList<>();
        this.ready = new ArrayList<>();
        Location location = LocationEncoder.getDeserializedLocation(Main.getPlugin(Main.class).getConfig().getString("Dungeons.padrao.LobbyLocation"));
        location.setWorld(Bukkit.getWorld(festa.getSala()));
        this.lobbylocation = LocationEncoder.getSerializedLocation(location);
        this.fase = 1;
        this.waitingfor = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.holograms = new ArrayList<>();
        this.vendedorid = new Random().nextInt(100000);
        this.pepitas = new HashMap<>();
        this.itens_iniciais = Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.ItensIniciais");
        this.itemsafterdead = new HashMap<>();
    }

    /**
     * Utilizado para teleportar os jogadores assim que o mundo for instalado/criado.
     * NOTE: O Mundo deve ser criado em async, assim que o socket for recebido.
     * E os jogadores devem ser teleportados para a dungeon, assim que se conectarem ao servidor, o setup será feito instantãneamente
     * É apenas para caso de que tenha algum delay entre o bungeecord/esta conexão, portanto será feito desta forma.
     */

    public void joinDungeon() {
        Location location = LocationEncoder.getDeserializedLocation(this.lobbylocation);
        for (String jogadore : this.jogadores) {
            if (Bukkit.getPlayer(jogadore) == null) {
                this.waitingfor.add(jogadore);
                continue;
            }
            Player player = Bukkit.getPlayer(jogadore);
            ScoreBoard.createLobbyScoreBoard(player,this);
            player.teleport(location);
            player.sendMessage("§aSeja bem vindo a Dungeon.");
            player.sendMessage("§8Sala-" + Integer.valueOf(this.getParty().getSala().split("sala")[1]) + 1);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 100f);
            player.sendMessage("§aDigite §7/pronto§a para ficar pronto para a dungeon.");
            if (!getPepitas().containsKey(jogadore)) {
                getPepitas().put(jogadore, 0);
            }
            player.getInventory().clear();
            player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
            player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
            player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
            player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).addEnchant(Enchantment.DURABILITY,3).toItemStack());
            for (String i :  getItens_iniciais()) {
                player.getInventory().addItem(ItemEncoder.getItemStackFromString(i));
            }
            TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20000, 20, "§cUtilize /pronto", "§7e esteja pronto para combate.");
        }
    }

    public void joinDungeon(Player player) {
        Location location = LocationEncoder.getDeserializedLocation(this.lobbylocation);
        if (waitingfor.contains(player.getName())) {
            player.teleport(location);
            player.sendMessage("§aSeja bem vindo a Dungeon.");
            player.sendMessage("§8Sala-" + Integer.valueOf(this.getParty().getSala().split("sala")[1]) + 1);
            if (!getPepitas().containsKey(player.getName())) {
                getPepitas().put(player.getName(), 0);
            }
            ScoreBoard.createLobbyScoreBoard(player, this);
            player.getInventory().clear();
            player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).toItemStack());
            player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).toItemStack());
            player.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).toItemStack());
            player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).toItemStack());
            for (String i : getItens_iniciais()) {
                player.getInventory().addItem(ItemEncoder.getItemStackFromString(i));
            }
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 100f);
            player.sendMessage("§aDigite §7/pronto§a para ficar pronto para a dungeon.");
            TitleAPI.sendTitle(player, 20, 20000, 20, "§cUtilize /pronto", "§7e esteja pronto para combate.");
        }
    }

    public void startDungeon() {
        generateFase();
        startFase();
    }

    public void passarFase() {
        for (String jogadore : this.jogadores) {
            if (Bukkit.getPlayer(jogadore) == null) continue;
            Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.ANVIL_BREAK, 1f, 0f);
            TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 40, 20, "§6Fim da ronda", "§7Siga para a próxima fase");
            pepitas.replace(jogadore,pepitas.get(jogadore) + 200);
        }
        for (String s : this.dead) {
            if (Bukkit.getPlayer(s) == null){continue;}
            for (String sf : this.jogadores) {
                if (Bukkit.getPlayer(sf) == null){continue;}
                Bukkit.getPlayer(sf).showPlayer(Bukkit.getPlayer(s));
            }
            Location location = LocationEncoder.getDeserializedLocation(this.getCurrentfase().getGetbacklocation());
            Player player = Bukkit.getPlayer(s);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.teleport(location);
            player.getInventory().clear();
            if (getItemsafterdead().containsKey(player.getName())){
                ItemStack[][] itemStack = getItemsafterdead().get(player.getName());
                player.getInventory().setArmorContents(itemStack[1]);
                player.getInventory().setContents(itemStack[0]);
            }
        }
        this.dead.clear();
        this.currentfase.getGate().openGate();
        this.spawnChest();
        this.spawnVendedor();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (passouDoPortão()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.ANVIL_BREAK, 1f, 0f);
                            }
                            currentfase.getGate().closeGate();
                            despawnChest();
                            despawnVendedor();
                            despawnChest();
                            despawnVendedor();
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 0L);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "", "§65");
                            }
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 20L);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "", "§64");
                            }
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 20L * 2);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "", "§63");
                            }
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 20L * 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "", "§62");
                            }
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 20L * 4);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.LEVEL_UP, 1f, 100f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "", "§61");
                            }
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 20L * 5);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (String jogadore : jogadores) {
                                if (Bukkit.getPlayer(jogadore) == null) continue;
                                Bukkit.getPlayer(jogadore).playSound(Bukkit.getPlayer(jogadore).getLocation(), Sound.ENDERDRAGON_WINGS, 1f, 0f);
                                TitleAPI.sendTitle(Bukkit.getPlayer(jogadore), 20, 20, 20, "§aFase Iniciada", "§7Elimine todos os mobs!");
                            }
                            startDungeon();
                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 20L * 6);
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 20L);
    }

    public void startFase() {
        if (this.currentfase != null) {
            this.getEntities().clear();
            Fase fase = this.currentfase;
            for (Map.Entry<EntityType, Integer> entityTypeIntegerEntry : fase.getEntitys().entrySet()) {
                if (this.getJogadores().size() >= 5){
                    for (int i = 0; i < entityTypeIntegerEntry.getValue() * this.getJogadores().size()-2; i++) {
                        int random = new Random().nextInt(fase.getLocations().size());
                        Location loc1 = fase.getLocations().get(random);
                        LivingEntity entity = (LivingEntity) loc1.getWorld().spawnEntity(loc1, entityTypeIntegerEntry.getKey());
                        entity.setMaxHealth(60);
                        entity.setHealth(60);
                        Reflections.setMobFollowSpeed(entity,0.3);
                        if (entity.getType() == EntityType.ZOMBIE){
                            CraftZombie entityCreature = (CraftZombie) entity;
                            entityCreature.getHandle().goalSelector.a(4, new PathfinderGoalMeleeAttack(entityCreature.getHandle(), 2.0D, true));
                            entityCreature.getHandle().targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(entityCreature.getHandle(), EntityHuman.class, true));
                        }
                        this.getEntities().add(entity);
                        int chance = new Random().nextInt(100);
                        if (chance < 60) {
                            entity.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        }
                        if (chance < 40) {
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        }
                    }
                    continue;
                }
                for (int i = 0; i < entityTypeIntegerEntry.getValue(); i++) {
                    int random = new Random().nextInt(fase.getLocations().size());
                    Location loc1 = fase.getLocations().get(random);
                    LivingEntity entity = (LivingEntity) loc1.getWorld().spawnEntity(loc1, entityTypeIntegerEntry.getKey());
                    entity.setMaxHealth(60);
                    entity.setHealth(60);
                    Reflections.setMobFollowSpeed(entity,0.3);
                    if (entity.getType() == EntityType.ZOMBIE){
                        int chance = new Random().nextInt(100);
                        CraftZombie entityCreature = (CraftZombie) entity;
                        entityCreature.getHandle().goalSelector.a(4, new PathfinderGoalMeleeAttack(entityCreature.getHandle(), 1.0D, true));
                        entityCreature.getHandle().targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(entityCreature.getHandle(), EntityHuman.class, true));
                        if (chance < 60) {
                            entity.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                        }
                        if (chance < 40) {
                            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        }
                        if (chance < 15){
                            entity.getEquipment().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                        }
                        if (chance < 55){
                            entity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                        }
                        this.getEntities().add(entity);
                        continue;
                    }
                    if (entity.getType() == EntityType.RABBIT){
                        CraftRabbit craftRabbit = (CraftRabbit)entity;
                        craftRabbit.getHandle().goalSelector.a(4,new PathfinderGoalMeleeAttack(craftRabbit.getHandle(),1.0D,true));
                        craftRabbit.getHandle().targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(craftRabbit.getHandle(), EntityHuman.class, true));
                        this.getEntities().add(entity);
                        continue;
                    }
                    if (entity.getType() == EntityType.IRON_GOLEM){
                        CraftGolem craftGolem = (CraftGolem)entity;
                        craftGolem.getHandle().goalSelector.a(4,new PathfinderGoalMeleeAttack(craftGolem.getHandle(),1.0D,true));
                        craftGolem.getHandle().targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(craftGolem.getHandle(), EntityHuman.class, true));
                        this.getEntities().add(entity);
                        continue;
                    }
                    if (entity.getType() == EntityType.PIG_ZOMBIE){
                        CraftPigZombie craftGolem = (CraftPigZombie)entity;
                        craftGolem.getHandle().goalSelector.a(4,new PathfinderGoalMeleeAttack(craftGolem.getHandle(),1.0D,true));
                        craftGolem.getHandle().targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(craftGolem.getHandle(), EntityHuman.class, true));
                        this.getEntities().add(entity);
                        continue;
                    }
                    if (entity.getType() == EntityType.SLIME){
                        EntitySlime craftSlime = (EntitySlime) entity;
                        craftSlime.goalSelector.a(4, new PathfinderGoalMeleeAttack(((CraftCreature)craftSlime.getBukkitEntity()).getHandle(), 1.0D, true));
                        craftSlime.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(((CraftCreature)craftSlime.getBukkitEntity()).getHandle(), EntityHuman.class, true));
                        this.getEntities().add(entity);
                        continue;
                    }
                    if (entity.getType() == EntityType.HORSE){
                        EntityHorse craftHorse = (EntityHorse)entity;
                        craftHorse.goalSelector.a(4,new PathfinderGoalMeleeAttack(craftHorse,1.0D,true));
                        craftHorse.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(craftHorse, EntityHuman.class, true));
                        this.getEntities().add(entity);
                        Horse horse = (Horse) entity;
                        int x = new Random().nextInt(100);
                        if (x < 30) {
                            horse.setVariant(Horse.Variant.SKELETON_HORSE);
                        } else {
                            horse.setVariant(Horse.Variant.UNDEAD_HORSE);
                            horse.setCarryingChest(true);
                        }
                        horse.setTamed(true);
                        horse.setStyle(Horse.Style.WHITEFIELD);
                        continue;
                    }
                }
            }
            if (fase.isTemboss()) {
                String bossloc = fase.getBosslocation();
                Location location = LocationEncoder.getDeserializedLocation(bossloc);
                LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, fase.getBosstype());
                entity.setCustomName("§6BOSS");
                entity.setCustomNameVisible(true);
                entity.setMaxHealth(2000);
                for (String jogadore : jogadores) {
                    if (Bukkit.getPlayer(jogadore) == null) continue;
                    ScoreBoard.createDungeonScoreBoardWithBoss(Bukkit.getPlayer(jogadore),this);
                    ScoreBoard.updateScoreBoard(Bukkit.getPlayer(jogadore),this);
                }
                entity.setHealth(2000);
                Reflections.setMobFollowRange(entity,16);
                this.getEntities().add(entity);
                this.getCurrentfase().setBoss(entity);
                if (entity.getType() == EntityType.GIANT) {
                        CraftGiant entityCreature = ((CraftGiant)entity);
                        ((CraftGiant) entity).getHandle().goalSelector.a(2, new PathfinderGoalMeleeAttack(entityCreature.getHandle(), 1D, true));
                        ((CraftGiant) entity).getHandle().targetSelector.a(16, new PathfinderGoalNearestAttackableTarget(entityCreature.getHandle(), EntityHuman.class, true));
                }
                if (entity.getType() == EntityType.WITHER){
                    CraftWither entityCreature  = ((CraftWither) entity);
                    ((CraftWither) entity).getHandle().goalSelector.a(2, new PathfinderGoalMeleeAttack(entityCreature.getHandle(), 1D, true));
                    ((CraftWither) entity).getHandle().targetSelector.a(16, new PathfinderGoalNearestAttackableTarget(entityCreature.getHandle(), EntityHuman.class, true));
                }
                if (entity.getType() == EntityType.MAGMA_CUBE){
                    CraftMagmaCube entityCreature  = ((CraftMagmaCube) entity);
                    entityCreature.getHandle().goalSelector.a(2, new PathfinderGoalMeleeAttack(((CraftCreature)entityCreature.getHandle().getBukkitEntity()).getHandle(), 1D, true));
                    entityCreature.getHandle().targetSelector.a(16, new PathfinderGoalNearestAttackableTarget(((CraftCreature)entityCreature.getHandle().getBukkitEntity()).getHandle(), EntityHuman.class, true));
                }
            } else {
                for (String jogadore : jogadores) {
                    if (Bukkit.getPlayer(jogadore) == null) continue;
                    ScoreBoard.createDungeonScoreBoardWithoutBoss(Bukkit.getPlayer(jogadore),this);
                    ScoreBoard.updateScoreBoard(Bukkit.getPlayer(jogadore),this);
                }
            }
        }
    }

    public int getEntityAlive() {
        return this.getEntities().stream().filter(s -> !s.isDead()).collect(Collectors.toSet()).size();
    }

    /**
     * Isso irá checar caso o jogador já tenha passado do portão, deve ser inicada uma runnable assim que todas as entidades foram mortas, deve-se spawnar
     * jogadores que estavam mortos, e em seguida run essa runnable, para verificar se todos passaram do portão, caso passe 1 minuto e meio, e o jogador não
     * passe do portão, deve-se fazer o setup com o jogador na arena sala antiga, e teleportar ele para o get back location!
     * também deve-se deixar os itens do jogador morto no inventário, assim teremos certeza de que ele não vai nascer com itens novos.
     */

    public boolean passouDoPortão() {
        for (String s : getJogadores()) {
            if (Bukkit.getPlayer(s) != null && !getDead().contains(s)) {
                Location loc1 = LocationEncoder.getDeserializedLocation(currentfase.getGate().getPos1());
                int x = Math.abs(loc1.getBlockX()) - 9;
                if (Math.abs(Bukkit.getPlayer(s).getLocation().getBlockX()) > x) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generateFase() {
        ArrayList<String> gates = new ArrayList<>();
        String worldname = party.getSala();
        ArrayList<Location> locations = new ArrayList<>();
        HashMap<EntityType, Integer> cache = new HashMap<>();
        this.holograms.clear();
        for (String mobs : Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + this.fase + ".Mobs")) {
            EntityType entityType = EntityType.valueOf(mobs.split(",")[0]);
            Integer howmuch = Integer.valueOf(mobs.split(",")[1]);
            cache.put(entityType, howmuch);
        }
        for (String location : Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + this.fase + ".Locations")) {
            Location location1 = LocationEncoder.getDeserializedLocation(location);
            location1.setWorld(Bukkit.getWorld(worldname));
            locations.add(location1);
        }
        for (String location : Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + this.fase + ".Gate")) {
            Location location1 = LocationEncoder.getDeserializedLocation(location);
            location1.setWorld(Bukkit.getWorld(worldname));
            gates.add(LocationEncoder.getSerializedLocation(location1));
        }
        ArrayList<String> itemstacks = new ArrayList<>();
        for (String item : Main.getPlugin(Main.class).getConfig().getStringList("Dungeons.padrao.Fases.Fase_" + this.fase + ".Drops")) {
            ItemStack itemStack = ItemEncoder.getItemStackFromString(item);
            try {
                String itemstackreal = ItemEncoder.toBase64(itemStack);
                itemstacks.add(itemstackreal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String chestlocation = Main.getPlugin(Main.class).getConfig().getString("Dungeons.padrao.Fases.Fase_" + this.fase + ".ChestLocation");
        Location location = LocationEncoder.getDeserializedLocation(chestlocation);
        location.setWorld(Bukkit.getWorld(worldname));
        String loc2 = LocationEncoder.getSerializedLocation(location);
        String locs = Main.getPlugin(Main.class).getConfig().getString("Dungeons.padrao.Fases.Fase_" + this.fase + ".HorseLocation");
        Location location2 = LocationEncoder.getDeserializedLocation(locs);
        location2.setWorld(Bukkit.getWorld(worldname));
        String vendedor = LocationEncoder.getSerializedLocation(location2);
        boolean hasboss = false;
        String boss = Main.getPlugin(Main.class).getConfig().getString("Dungeons.padrao.Fases.Fase_" + this.fase + ".Boss");
        if (boss.equalsIgnoreCase("") || boss.isEmpty()) {
            hasboss = false;
        } else {
            hasboss = true;
        }
        Fase fase = new Fase(gates.get(0), gates.get(1), worldname, cache, loc2, vendedor, hasboss);
        if (hasboss) {
            String bosss = Main.getPlugin(Main.class).getConfig().getString("Dungeons.padrao.Fases.Fase_" + this.fase + ".BossLocation");
            Location location32 = LocationEncoder.getDeserializedLocation(bosss);
            location32.setWorld(Bukkit.getWorld(worldname));
            String fw = LocationEncoder.getSerializedLocation(location32);
            fase.setBosslocation(fw);
            fase.setBosstype(EntityType.valueOf(boss));
        }
        String back = Main.getPlugin(Main.class).getConfig().getString("Dungeons.padrao.Fases.Fase_" + this.fase + ".Location");
        Location location23 = LocationEncoder.getDeserializedLocation(back);
        location23.setWorld(Bukkit.getWorld(worldname));
        String wf = LocationEncoder.getSerializedLocation(location23);
        fase.setGetbacklocation(wf);
        fase.setLocations(locations);
        fase.setItems(itemstacks);
        this.currentfase = fase;
        if (this.fase == 8) {
            fase.setIsfinal(true);
        }
        this.fase = this.fase + 1;
    }

    public void spawnChest() {
        String loc = this.getCurrentfase().getChestlocations();
        Location chest = LocationEncoder.getDeserializedLocation(loc);
        Hologram holo = HologramsAPI.createHologram(Main.getPlugin(Main.class), chest.clone().add(0.5, 1.5, 0.5));
        holo.appendTextLine("§aRecompensas Coletadas");
        this.holograms.add(holo);
        Block b = chest.getBlock();
        b.setMetadata("Rewards", new FixedMetadataValue(Main.getPlugin(Main.class), "Rewards"));
        b.setType(Material.CHEST);
        BlockState state = b.getState();
        Chest chest1 = new Chest(BlockFace.EAST);
        state.setData(chest1);
        state.update();
    }

    public void spawnVendedor() {
        String locvendedor = this.currentfase.getVendedorlocation();
        Location location = LocationEncoder.getDeserializedLocation(locvendedor);
        if (CitizensAPI.getNPCRegistry().getById(this.vendedorid) != null) {
            if (CitizensAPI.getNPCRegistry().getById(this.vendedorid).isSpawned()) {
                CitizensAPI.getNPCRegistry().getById(this.vendedorid).despawn();
                CitizensAPI.getNPCRegistry().deregister(CitizensAPI.getNPCRegistry().getById(this.vendedorid));
            }
        }
        if (CitizensAPI.getNPCRegistry().getById(this.horsenumber) != null) {
            if (CitizensAPI.getNPCRegistry().getById(this.horsenumber).isSpawned()) {
                CitizensAPI.getNPCRegistry().getById(this.horsenumber).despawn();
                CitizensAPI.getNPCRegistry().deregister(CitizensAPI.getNPCRegistry().getById(this.horsenumber));
            }
        }
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.HORSE, UUID.randomUUID(), this.horsenumber, "");
        npc.data().set(NPC.NAMEPLATE_VISIBLE_METADATA, false);
        if (!npc.hasTrait(Controllable.class)) {
            npc.addTrait(new Controllable(false));
        }
        if (!npc.hasTrait(MountTrait.class)) {
            npc.addTrait(new MountTrait());
        }
        NPC player = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, UUID.randomUUID(), this.vendedorid, "");
        if (!player.hasTrait(SkinTrait.class)) {
            player.addTrait(new SkinTrait());
        }
        if (!player.hasTrait(Equipment.class)) {
            player.addTrait(new Equipment());
        }
        if (!player.hasTrait(MountTrait.class)) {
            player.addTrait(new MountTrait());
        }
        Controllable controllable = npc.getTrait(Controllable.class);
        controllable.setEnabled(true);
        controllable.setOwnerRequired(false);
        player.getTrait(SkinTrait.class).setFetchDefaultSkin(false);
        player.getTrait(SkinTrait.class).setSkinPersistent("00werner",
                "lENfVj3Gb3zqbhGhApNkJXK6EoatW615QDC+TO3+PQ/SatazGwEKiYTyQEnIahzgQzebfjie/iITmVaXyloMSkhyzw1QocNrAGw5oSZesMhoaWw6/j4k0a2+cyVSuzEcJVUmnJH5CciR/ZlHWIvUMmpd84HporPjewravF+fnB9x/hdf1Ja3e1c1WUcIzTMCRVfqssuWwSO0XhTbwtQI3vab+D/RkhZTgLH4tHBCIIKvk/diLP5fe+Y9TWd0N6Vt7QKHFDwdeyk6ROGgReMbd9NnVZEuE3geU+5LZLGUcVnTCvfEd3fqVGQSlIG2g7ZJiYCbawdhBX6dEFDblo5jTmFrrJMNSxoMI8sIsdX6smSyJr+KcGLebKBapWy0WkUDmc6pmCVFrHmuzL7h7NfG9a7pYLnUkrnaaxzMl1mHZXLrKY0bxq+aa7+rk+Q0jcxn3WtgifbZMIJ3RvZshDQTPj2T5xc/L2sYvEL9stefovj5m68Iy+UDU/UXGQjrf8tWnBo+7uZkzSaRLab6Bhsqv8O3pQM1Tj9aMVasVMwpQXEVWZUSQ4UHMU+isq7/3vbmc0FH//SSi9xA2ofokAwQQ+yUY+ESlWTSbqA+dTyVdCOGUNwXe5KLBJ+FEXzd5My9AZiydeYRZnR9+VWUrmLjZCK0i/Gzo5R2K6qRwPfskYc=",
                "eyJ0aW1lc3RhbXAiOjE1ODI1MjYwMzM3MDMsInByb2ZpbGVJZCI6IjczODJkZGZiZTQ4NTQ1NWM4MjVmOTAwZjg4ZmQzMmY4IiwicHJvZmlsZU5hbWUiOiJZYU9PUCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcwZmVhNDE2N2M2NWJkMjJiOTU4Zjc5MjViYTMxOWQ5Nzc1YjBhZGMxZWMwZjJhZDhlNmVjYjA3ODU2MTk0OCJ9fX0=");
        player.spawn(location);
        npc.spawn(location);
        Horse h = (Horse) npc.getEntity();
        h.setOwner((AnimalTamer) player.getEntity());
        h.setTamed(true);
        h.setCanPickupItems(false);
        h.setVariant(Horse.Variant.HORSE);
        h.setColor(Horse.Color.DARK_BROWN);
        h.setStyle(Horse.Style.WHITEFIELD);
        h.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING));
        h.setCarryingChest(true);
        LivingEntity e = (LivingEntity) player.getEntity();
        NMS.mount(npc.getEntity(), player.getEntity());
        npc.getNavigator().getDefaultParameters().baseSpeed(1.60f);
        npc.getNavigator().setTarget(location.add(20, 0, 0.6));
        for (String jogadore : this.jogadores) {
            if (Bukkit.getPlayer(jogadore) == null) {
                continue;
            }
            hideNPCNameTag(Bukkit.getPlayer(jogadore), npc.getEntity());
            hideNPCNameTag(Bukkit.getPlayer(jogadore), player.getEntity());
        }
    }

    public void despawnVendedor() {
        if (CitizensAPI.getNPCRegistry().getById(this.vendedorid) != null) {
            if (CitizensAPI.getNPCRegistry().getById(this.vendedorid).isSpawned()) {
                CitizensAPI.getNPCRegistry().getById(this.vendedorid).despawn();
            }
        }
        if (CitizensAPI.getNPCRegistry().getById(this.horsenumber) != null) {
            if (CitizensAPI.getNPCRegistry().getById(this.horsenumber).isSpawned()) {
                CitizensAPI.getNPCRegistry().getById(this.horsenumber).despawn();
            }
        }
    }

    public void despawnChest() {
        String loc = this.getCurrentfase().getChestlocations();
        Location chest = LocationEncoder.getDeserializedLocation(loc);
        Block b = chest.getBlock();
        b.removeMetadata("Rewards", Main.getPlugin(Main.class));
        b.setType(Material.AIR);
        for (Hologram hologram : this.holograms) {
            hologram.delete();
        }
        this.holograms.clear();
    }

    public static void hideNPCNameTag(Player from, Entity p) {
        Entity npc = (Entity) p;
        Scoreboard score = from.getScoreboard();
        Team team = null;
        for (Team t : score.getTeams()) {
            if (t.getName().equalsIgnoreCase("NPCs")) {
                team = t;
            }
        }
        if (team == null) {
            team = score.registerNewTeam("NPCs");
        }
        team.addEntry(npc.getName());
        team.setNameTagVisibility(NameTagVisibility.NEVER);
        from.setScoreboard(score);
    }

    public ArrayList<String> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<String> jogadores) {
        this.jogadores = jogadores;
    }

    public String getLobbylocation() {
        return lobbylocation;
    }

    public void setLobbylocation(String lobbylocation) {
        this.lobbylocation = lobbylocation;
    }

    public Fase getCurrentfase() {
        return currentfase;
    }

    public void setCurrentfase(Fase currentfase) {
        this.currentfase = currentfase;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyName(String party) {
        this.partyname = party;
    }

    public ArrayList<String> getDead() {
        return dead;
    }

    public void setDead(ArrayList<String> dead) {
        this.dead = dead;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public World getWorld() {
        return world;
    }

    public ArrayList<String> getReady() {
        return ready;
    }

    public void setReady(ArrayList<String> ready) {
        this.ready = ready;
    }

    public ArrayList<String> getWaitingfor() {
        return waitingfor;
    }

    public void setWaitingfor(ArrayList<String> waitingfor) {
        this.waitingfor = waitingfor;
    }

    public ArrayList<LivingEntity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<LivingEntity> entities) {
        this.entities = entities;
    }

    public int getVendedorid() {
        return vendedorid;
    }

    public void setVendedorid(int vendedorid) {
        this.vendedorid = vendedorid;
    }

    public int getHorsenumber() {
        return horsenumber;
    }

    public void setHorsenumber(int horsenumber) {
        this.horsenumber = horsenumber;
    }

    public HashMap<String, Integer> getPepitas() {
        return pepitas;
    }

    public void setPepitas(HashMap<String, Integer> pepitas) {
        this.pepitas = pepitas;
    }

    public List<String> getItens_iniciais() {
        return itens_iniciais;
    }

    public HashMap<String, ItemStack[][]> getItemsafterdead() {
        return itemsafterdead;
    }

    public void setItemsafterdead(HashMap<String, ItemStack[][]> itemsafterdead) {
        this.itemsafterdead = itemsafterdead;
    }
    public int getFase() {
        return fase;
    }
}