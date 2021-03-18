package br.jotaentity.geradores.objetos;

import br.jotaentity.geradores.Main;

import java.util.HashMap;
import java.util.List;

public class FactionGeradores {

    private String fac;
    private static HashMap<String, Integer> pig;
    private static HashMap<String, Integer> cow;
    private static HashMap<String, Integer> spider;
    private static HashMap<String, Integer> zombie;
    private static HashMap<String, Integer> skeleton;
    private static HashMap<String, Integer> blaze;
    private static HashMap<String, Integer> slime;
    private static HashMap<String, Integer> pig_zombie;
    private static HashMap<String, Integer> iron_golem;
    private static HashMap<String, Integer> wither;
    private static HashMap<String, Integer> cave_spider;
    private static HashMap<String, Integer> chicken;
    private static HashMap<String, Integer> creeper;
    private static HashMap<String, Integer> enderman;
    private static HashMap<String, Integer> magma_cube;
    private static HashMap<String, Integer> sheep;
    private static HashMap<String, String> abriuporultimo;
    private static HashMap<String, Integer> mushroom_cow;
    private static HashMap<String, Integer> bruxa;
    private static HashMap<String, Integer> ghast;
    private static HashMap<String, Integer> cavalo;

    static {
        FactionGeradores.pig = new HashMap<String, Integer>();
        FactionGeradores.cow = new HashMap<String, Integer>();
        FactionGeradores.spider = new HashMap<String, Integer>();
        FactionGeradores.zombie = new HashMap<String, Integer>();
        FactionGeradores.skeleton = new HashMap<String, Integer>();
        FactionGeradores.blaze = new HashMap<String, Integer>();
        FactionGeradores.slime = new HashMap<String, Integer>();
        FactionGeradores.pig_zombie = new HashMap<String, Integer>();
        FactionGeradores.iron_golem = new HashMap<String, Integer>();
        FactionGeradores.wither = new HashMap<String, Integer>();
        FactionGeradores.cave_spider = new HashMap<String, Integer>();
        FactionGeradores.chicken = new HashMap<String, Integer>();
        FactionGeradores.creeper = new HashMap<String, Integer>();
        FactionGeradores.enderman = new HashMap<String, Integer>();
        FactionGeradores.magma_cube = new HashMap<String, Integer>();
        FactionGeradores.sheep = new HashMap<String, Integer>();
        FactionGeradores.abriuporultimo = new HashMap<String, String>();
        FactionGeradores.mushroom_cow = new HashMap<String, Integer>();
        FactionGeradores.bruxa = new HashMap<String, Integer>();
        FactionGeradores.ghast = new HashMap<String, Integer>();
        FactionGeradores.cavalo = new HashMap<String, Integer>();
    }

    public FactionGeradores(final String fac) {
        this.fac = fac;
    }

    public void importGeradores(final Integer pig, final Integer cow, final Integer spider, final Integer zombie, final Integer skeleton, final Integer blaze, final Integer slime, final Integer pig_zombie, final Integer iron_golem, final Integer wither, final Integer cave_spider, final Integer chicken, final Integer creeper, final Integer enderman, final Integer magma_cube, final Integer sheep, final Integer mushroomcow, final Integer bruxa, final Integer ghast, final Integer cavalo) {
        this.setQuantiaPig(pig);
        this.setQuantiaCow(cow);
        this.setQuantiaSpider(spider);
        this.setQuantiaZombie(zombie);
        this.setQuantiaSkeleton(skeleton);
        this.setQuantiaBlaze(blaze);
        this.setQuantiaSlime(slime);
        this.setQuantiaPig_Zombie(pig_zombie);
        this.setQuantiaIron_Golem(iron_golem);
        this.setQuantiaWither(wither);
        this.setQuantiaCave_Spider(cave_spider);
        this.setQuantiaChicken(chicken);
        this.setQuantiaCreeper(creeper);
        this.setQuantiaEnderman(enderman);
        this.setQuantiaMagma_Cube(magma_cube);
        this.setQuantiaSheep(sheep);
        this.setQuantiaMush(mushroomcow);
        this.setQuantiaBruxa(bruxa);
        this.setQuantiaGhast(ghast);
        this.setQuantiaCavalo(cavalo);
    }

    public Integer getQuantiaMushroom() {
        return mushroom_cow.get(this.fac);
    }

    public Integer getQuantiaCavalo() {
        return cavalo.get(this.fac);
    }

    public Integer getQuantiaGhast() {
        return ghast.get(this.fac);
    }

    public Integer getQuantiaBruxa() {
        return bruxa.get(this.fac);
    }

    public void setQuantiaCavalo(final int q) {
        FactionGeradores.cavalo.put(this.fac
                , q);
    }

    public void setQuantiaGhast(final int q) {
        FactionGeradores.ghast.put(this.fac, q);
    }

    public void setQuantiaMush(final int q) {
        FactionGeradores.mushroom_cow.put(this.fac, q);
    }

    public void setQuantiaBruxa(final int q) {
        FactionGeradores.bruxa.put(this.fac, q);
    }

    public void setQuantiaPig(final int q) {
        FactionGeradores.pig.put(this.fac, q);
    }

    public void setQuantiaCow(final int q) {
        FactionGeradores.cow.put(this.fac, q);
    }

    public void setQuantiaSpider(final int q) {
        FactionGeradores.spider.put(this.fac, q);
    }

    public void setQuantiaZombie(final int q) {
        FactionGeradores.zombie.put(this.fac, q);
    }

    public void setQuantiaSkeleton(final int q) {
        FactionGeradores.skeleton.put(this.fac, q);
    }

    public void setQuantiaBlaze(final int q) {
        FactionGeradores.blaze.put(this.fac, q);
    }

    public void setQuantiaSlime(final int q) {
        FactionGeradores.slime.put(this.fac, q);
    }

    public void setQuantiaPig_Zombie(final int q) {
        FactionGeradores.pig_zombie.put(this.fac, q);
    }

    public void setQuantiaIron_Golem(final int q) {
        FactionGeradores.iron_golem.put(this.fac, q);
    }

    public void setQuantiaWither(final int q) {
        FactionGeradores.wither.put(this.fac, q);
    }

    public void setQuantiaCave_Spider(final int q) {
        FactionGeradores.cave_spider.put(this.fac, q);
    }

    public void setQuantiaChicken(final int q) {
        FactionGeradores.chicken.put(this.fac, q);
    }

    public void setQuantiaCreeper(final int q) {
        FactionGeradores.creeper.put(this.fac, q);
    }

    public void setQuantiaEnderman(final int q) {
        FactionGeradores.enderman.put(this.fac, q);
    }

    public void setQuantiaMagma_Cube(final int q) {
        FactionGeradores.magma_cube.put(this.fac, q);
    }

    public void setQuantiaSheep(final int q) {
        FactionGeradores.sheep.put(this.fac, q);
    }

    public Integer getQuantiaPig() {
        return FactionGeradores.pig.get(this.fac);
    }

    public Integer getQuantiaCow() {
        return FactionGeradores.cow.get(this.fac);
    }

    public Integer getQuantiaSpider() {
        return FactionGeradores.spider.get(this.fac);
    }

    public Integer getQuantiaZombie() {
        return FactionGeradores.zombie.get(this.fac);
    }

    public Integer getQuantiaSkeleton() {
        return FactionGeradores.skeleton.get(this.fac);
    }

    public Integer getQuantiaBlaze() {
        return FactionGeradores.blaze.get(this.fac);
    }

    public Integer getQuantiaSlime() {
        return FactionGeradores.slime.get(this.fac);
    }

    public Integer getQuantiaPig_Zombie() {
        return FactionGeradores.pig_zombie.get(this.fac);
    }

    public Integer getQuantiaIron_Golem() {
        return FactionGeradores.iron_golem.get(this.fac);
    }

    public Integer getQuantiaWither() {
        return FactionGeradores.wither.get(this.fac);
    }

    public Integer getQuantiaCave_Spider() {
        return FactionGeradores.cave_spider.get(this.fac);
    }

    public Integer getQuantiaChicken() {
        return FactionGeradores.chicken.get(this.fac);
    }

    public Integer getQuantiaCreeper() {
        return FactionGeradores.creeper.get(this.fac);
    }

    public Integer getQuantiaEnderman() {
        return FactionGeradores.enderman.get(this.fac);
    }

    public Integer getQuantiaMagma_Cube() {
        return FactionGeradores.magma_cube.get(this.fac);
    }

    public Integer getQuantiaSheep() {
        return FactionGeradores.sheep.get(this.fac);
    }

    public Integer countGeradores() {
        return this.getQuantiaPig() + this.getQuantiaCow() + this.getQuantiaSpider() + this.getQuantiaZombie() + this.getQuantiaSkeleton() + this.getQuantiaBlaze() + this.getQuantiaSlime() + this.getQuantiaPig_Zombie() + this.getQuantiaIron_Golem() + this.getQuantiaWither() + this.getQuantiaCave_Spider() + this.getQuantiaChicken() + this.getQuantiaCreeper() + this.getQuantiaEnderman() + this.getQuantiaMagma_Cube() + this.getQuantiaSheep();
    }

    public String getAbriuPorUltimo() {
        return FactionGeradores.abriuporultimo.get(this.fac);
    }

    public void setAbriuPorUltimo(final String player) {
        FactionGeradores.abriuporultimo.put(this.fac, player);
    }

    public boolean checkAbriuPorUltimoOpen() {
        return FactionGeradores.abriuporultimo.get(this.fac) != null;
    }

    public List<String> getLog() {
        return (List<String>) Main.getInstance().geradores.getConfig().getStringList("Faction." + this.fac + ".ultimo");
    }

    public void addLog(final String mensagem) {
        final List<String> abc = (List<String>) Main.getInstance().geradores.getConfig().getStringList("Faction." + this.fac + ".ultimo");
        if (abc.size() == 30) {
            abc.remove(0);
        }
        abc.add(mensagem);
        Main.getInstance().geradores.getConfig().set("Faction." + this.fac + ".ultimo", (Object) abc);
        Main.getInstance().geradores.saveConfig();
    }
}