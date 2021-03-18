package br.jotaentity.geradores;

import br.jotaentity.geradores.objetos.FactionGeradores;
import br.jotaentity.geradores.objetos.FactionGeradoresDLL;
import org.bukkit.entity.EntityType;

import java.util.LinkedHashMap;

public class GeradorAPI {

    public static LinkedHashMap<EntityType,Integer> getGeradores(String fac){
        FactionGeradores f = new FactionGeradores(fac);
        LinkedHashMap<EntityType,Integer> cache = new LinkedHashMap<EntityType, Integer>();
        if (f == null || f.getQuantiaSheep() == null){
            new FactionGeradoresDLL(fac).importGeradores();
        }
        if (f.getQuantiaCavalo() > 0){
            cache.put(EntityType.HORSE,f.getQuantiaCavalo());
        }
        if (f.getQuantiaGhast() > 0){
            cache.put(EntityType.GHAST,f.getQuantiaGhast());
        }
        if (f.getQuantiaBruxa() > 0){
            cache.put(EntityType.WITCH,f.getQuantiaBruxa());
        }
        if (f.getQuantiaMushroom() > 0){
            cache.put(EntityType.MUSHROOM_COW,f.getQuantiaMushroom());
        }
        if (f.getQuantiaZombie() > 0){
            cache.put(EntityType.ZOMBIE,f.getQuantiaZombie());
        }
        if (f.getQuantiaCave_Spider() > 0){
            cache.put(EntityType.CAVE_SPIDER,f.getQuantiaCave_Spider());
        }
        if (f.getQuantiaWither() > 0){
            cache.put(EntityType.WITHER,f.getQuantiaWither());
        }
        if (f.getQuantiaSpider() > 0){
            cache.put(EntityType.ZOMBIE,f.getQuantiaSpider());
        }
        if (f.getQuantiaSlime() > 0){
            cache.put(EntityType.SLIME,f.getQuantiaSlime());
        }
        if (f.getQuantiaSkeleton() > 0 ){
            cache.put(EntityType.SKELETON,f.getQuantiaSkeleton());
        }
        if (f.getQuantiaBlaze() > 0){
            cache.put(EntityType.BLAZE,f.getQuantiaBlaze());
        }
        if (f.getQuantiaChicken() > 0){
            cache.put(EntityType.CHICKEN,f.getQuantiaChicken());
        }
        if (f.getQuantiaCow() > 0){
            cache.put(EntityType.COW,f.getQuantiaCow());
        }
        if (f.getQuantiaCreeper() > 0){
            cache.put(EntityType.CREEPER,f.getQuantiaCreeper());
        }
        if (f.getQuantiaEnderman() > 0){
            cache.put(EntityType.ENDERMAN,f.getQuantiaEnderman());
        }
        if (f.getQuantiaIron_Golem() > 0){
            cache.put(EntityType.IRON_GOLEM,f.getQuantiaIron_Golem());
        }
        if (f.getQuantiaMagma_Cube() > 0){
            cache.put(EntityType.MAGMA_CUBE,f.getQuantiaMagma_Cube());
        }
        if (f.getQuantiaPig() > 0){
            cache.put(EntityType.PIG,f.getQuantiaPig());
        }
        if (f.getQuantiaPig_Zombie() > 0){
            cache.put(EntityType.PIG_ZOMBIE,f.getQuantiaPig_Zombie());
        }
        if (f.getQuantiaSheep() > 0){
            cache.put(EntityType.SHEEP,f.getQuantiaSheep());
        }
        return cache;
    }

}