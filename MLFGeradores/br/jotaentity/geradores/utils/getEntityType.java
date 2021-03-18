package br.jotaentity.geradores.utils;

import org.bukkit.entity.EntityType;

public class getEntityType {

    public static EntityType getType(String nome) {
        String ret = "";
        if (nome.equalsIgnoreCase("Aranha")) {
            return EntityType.SPIDER;
        }
        if (nome.equalsIgnoreCase("Blaze")) {
            return EntityType.BLAZE;
        }
        if (nome.equalsIgnoreCase("Aranha das Cavernas")) {
            return EntityType.CAVE_SPIDER;
        }
        if (nome.equalsIgnoreCase("Porco Zumbi")) {
            return EntityType.PIG_ZOMBIE;
        }
        if (nome.equalsIgnoreCase("Golem de Ferro")) {
            return EntityType.IRON_GOLEM;
        }
        if (nome.equalsIgnoreCase("Porco")) {
            return EntityType.PIG;
        }
        if (nome.equalsIgnoreCase("Ovelha")) {
            return EntityType.SHEEP;
        }
        if (nome.equalsIgnoreCase("Zombie")) {
            return EntityType.ZOMBIE;
        }
        if (nome.equalsIgnoreCase("Vaca cogumelo")) {
            return EntityType.MUSHROOM_COW;
        }
        if (nome.equalsIgnoreCase("Vaca")) {
            return EntityType.COW;
        }
        if (nome.equalsIgnoreCase("Esqueleto")) {
            return EntityType.SKELETON;
        }
        if (nome.equalsIgnoreCase("Slime")) {
            return EntityType.SLIME;
        }
        if (nome.equalsIgnoreCase("Enderman")) {
            return EntityType.ENDERMAN;
        }
        if (nome.equalsIgnoreCase("Galinha")) {
            return EntityType.CHICKEN;
        }
        if (nome.equalsIgnoreCase("Creeper")) {
            return EntityType.CREEPER;
        }
        if (nome.equalsIgnoreCase("Wither")) {
            return EntityType.WITHER;
        }
        if (nome.equalsIgnoreCase("Bruxa")) {
            return EntityType.WITCH;
        }
        if (nome.equalsIgnoreCase("Cubo de Magma")) {
            return EntityType.MAGMA_CUBE;
        }
        if (nome.equalsIgnoreCase("Cavalo")) {
            return EntityType.HORSE;
        }
        return null;
    }
}
