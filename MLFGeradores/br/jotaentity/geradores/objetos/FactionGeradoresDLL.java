package br.jotaentity.geradores.objetos;

import br.jotaentity.geradores.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class FactionGeradoresDLL {
    private String fac;

    public FactionGeradoresDLL(final String fac) {
        this.fac = fac;
    }

    public void exportGeradores() {
        final FactionGeradores fc = new FactionGeradores(this.fac);
        final FileConfiguration config = Main.getInstance().geradores.getConfig();
        config.set("Faction." + this.fac + ".PIG", (Object) fc.getQuantiaPig());
        config.set("Faction." + this.fac + ".COW", (Object) fc.getQuantiaCow());
        config.set("Faction." + this.fac + ".SPIDER", (Object) fc.getQuantiaSpider());
        config.set("Faction." + this.fac + ".ZOMBIE", (Object) fc.getQuantiaZombie());
        config.set("Faction." + this.fac + ".SKELETON", (Object) fc.getQuantiaSkeleton());
        config.set("Faction." + this.fac + ".BLAZE", (Object) fc.getQuantiaBlaze());
        config.set("Faction." + this.fac + ".SLIME", (Object) fc.getQuantiaSlime());
        config.set("Faction." + this.fac + ".PIG_ZOMBIE", (Object) fc.getQuantiaPig_Zombie());
        config.set("Faction." + this.fac + ".IRON_GOLEM", (Object) fc.getQuantiaIron_Golem());
        config.set("Faction." + this.fac + ".WITHER", (Object) fc.getQuantiaWither());
        config.set("Faction." + this.fac + ".CAVE_SPIDER", (Object) fc.getQuantiaCave_Spider());
        config.set("Faction." + this.fac + ".CHICKEN", (Object) fc.getQuantiaChicken());
        config.set("Faction." + this.fac + ".CREEPER", (Object) fc.getQuantiaCreeper());
        config.set("Faction." + this.fac + ".ENDERMAN", (Object) fc.getQuantiaEnderman());
        config.set("Faction." + this.fac + ".MAGMA_CUBE", (Object) fc.getQuantiaMagma_Cube());
        config.set("Faction." + this.fac + ".SHEEP", (Object) fc.getQuantiaSheep());
        config.set("Faction." + this.fac + ".MUSHROOM_COW", (Object) fc.getQuantiaMushroom());
        config.set("Faction." + this.fac + ".WITCH", (Object) fc.getQuantiaBruxa());
        config.set("Faction." + this.fac + ".GHAST", (Object) fc.getQuantiaGhast());
        config.set("Faction." + this.fac + ".HORSE", (Object) fc.getQuantiaCavalo());
        Main.getInstance().geradores.saveConfig();
        Main.getInstance().geradores.reloadConfig();
    }

    public void importGeradores() {
        final FactionGeradores fc = new FactionGeradores(this.fac);
        final FileConfiguration config = Main.getInstance().geradores.getConfig();
        fc.importGeradores(config.getInt("Faction." + this.fac + ".PIG"), config.getInt("Faction." + this.fac + ".COW"), config.getInt("Faction." + this.fac + ".SPIDER"), config.getInt("Faction." + this.fac + ".ZOMBIE"), config.getInt("Faction." + this.fac + ".SKELETON"), config.getInt("Faction." + this.fac + ".BLAZE"), config.getInt("Faction." + this.fac + ".SLIME"), config.getInt("Faction." + this.fac + ".PIG_ZOMBIE"), config.getInt("Faction." + this.fac + ".IRON_GOLEM"), config.getInt("Faction." + this.fac + ".WITHER"), config.getInt("Faction." + this.fac + ".CAVE_SPIDER"), config.getInt("Faction." + this.fac + ".CHICKEN"), config.getInt("Faction." + this.fac + ".CREEPER"), config.getInt("Faction." + this.fac + ".ENDERMAN"), config.getInt("Faction." + this.fac + ".MAGMA_CUBE"), config.getInt("Faction." + this.fac + ".SHEEP"), config.getInt("Faction." + this.fac + ".MUSHROOM_COW"), config.getInt("Faction." + this.fac + ".WITCH"),
                config.getInt("Faction." + this.fac + ".GHAST"), config.getInt("Faction." + this.fac + ".HORSE"));
        Main.getInstance().geradores.reloadConfig();
    }

}