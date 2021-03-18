package br.jotaentity.geradores.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public enum EntityName {
    AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 0, "\u00c1rea de Efeito de Po\u00e7\u00e3o"),
    ARMOR_STAND("ARMOR_STAND", 1, "Suporte para Armaduras"),
    ARROW("ARROW", 2, "Flecha"),
    BAT("BAT", 3, "Morcego"),
    BLAZE("BLAZE", 4, "Blaze"),
    BOAT("BOAT", 5, "Barco"),
    CAT("CAT", 6, "Gato"),
    CAVE_SPIDER("CAVE_SPIDER", 7, "Aranha da Caverna"),
    CHICKEN("CHICKEN", 8, "Galinha"),
    COD("COD", 9, "Bacalhau"),
    COMPLEX_PART("COMPLEX_PART", 10, "Desconhecido"),
    COW("COW", 11, "Vaca"),
    CREEPER("CREEPER", 12, "Creeper"),
    DOLPHIN("DOLPHIN", 13, "Golfinho"),
    DONKEY("DONKEY", 14, "Burro"),
    DRAGON_FIREBALL("DRAGON_FIREBALL", 15, "Bola de Fogo"),
    DROPPED_ITEM("DROPPED_ITEM", 16, "Item dropado"),
    DROWNED("DROWNED", 17, "Afogado"),
    EGG("EGG", 18, "Ovo"),
    ELDER_GUARDIAN("ELDER_GUARDIAN", 19, "Guardi\u00e3o Mestre"),
    ENDER_CRYSTAL("ENDER_CRYSTAL", 20, "Cristal do End"),
    ENDER_DRAGON("ENDER_DRAGON", 21, "Drag\u00e3o do Fim"),
    ENDER_PEARL("ENDER_PEARL", 22, "P\u00e9rola do Fim"),
    ENDER_SIGNAL("ENDER_SIGNAL", 23, "Olho do Fim"),
    ENDERMAN("ENDERMAN", 24, "Enderman"),
    ENDERMITE("ENDERMITE", 25, "Endermite"),
    EVOKER("EVOKER", 26, "Invocador"),
    EVOKER_FANGS("EVOKER_FANGS", 27, "Presas do Invocador"),
    EXPERIENCE_ORB("EXPERIENCE_ORB", 28, "Orb de Experi\u00eancia"),
    FALLING_BLOCK("FALLING_BLOCK", 29, "Bloco Caindo"),
    FIREBALL("FIREBALL", 30, "Bola de Fogo"),
    FIREWORK("FIREWORK", 31, "Fogos de Artif\u00edcio"),
    FISHING_HOOK("FISHING_HOOK", 32, "Isca da Vara de Pesca"),
    FOX("FOX", 33, "Raposa"),
    GHAST("GHAST", 34, "Ghast"),
    GIANT("GIANT", 35, "Zumbi Gigante"),
    GUARDIAN("GUARDIAN", 36, "Guardi\u00e3o"),
    HORSE("HORSE", 37, "Cavalo"),
    HUSK("HUSK", 38, "Zumbi do Deserto"),
    ILLUSIONER("ILLUSIONER", 39, "Ilusionista"),
    IRON_GOLEM("IRON_GOLEM", 40, "Golem de Ferro"),
    ITEM_FRAME("ITEM_FRAME", 41, "Moldura"),
    LEASH_HITCH("LEASH_HITCH", 42, "Desconhecido"),
    LIGHTNING("LIGHTNING", 43, "Raio"),
    LINGERING_POTION("LINGERING_POTION", 44, "Po\u00e7\u00e3o"),
    LLAMA("LLAMA", 45, "Lhama"),
    LLAMA_SPIT("LLAMA_SPIT", 46, "Cuspe de Lhama"),
    MAGMA_CUBE("MAGMA_CUBE", 47, "Cubo de Magma"),
    MINECART("MINECART", 48, "Carrinho"),
    MINECART_CHEST("MINECART_CHEST", 49, "Carrinho com Ba\u00fa"),
    MINECART_COMMAND("MINECART_COMMAND", 50, "Carrinho com Bloco de Comando"),
    MINECART_FURNACE("MINECART_FURNACE", 51, "Carrinho com Fornalha"),
    MINECART_HOPPER("MINECART_HOPPER", 52, "Carrinho com Funil"),
    MINECART_MOB_SPAWNER("MINECART_MOB_SPAWNER", 53, "Carrinho com Gerador de Monstros"),
    MINECART_TNT("MINECART_TNT", 54, "Carrinho com Dinamite"),
    MULE("MULE", 55, "Mula"),
    MUSHROOM_COW("MUSHROOM_COW", 56, "Vaca de Cogumelo"),
    OCELOT("OCELOT", 57, "Jaguatirica"),
    PAINTING("PAINTING", 58, "Pintura"),
    PANDA("PANDA", 59, "Panda"),
    PARROT("PARROT", 60, "Papagaio"),
    PHANTOM("PHANTOM", 61, "Phantom"),
    PIG("PIG", 62, "Porco"),
    PIG_ZOMBIE("PIG_ZOMBIE", 63, "Porco Zumbi"),
    PILLAGER("PILLAGER", 64, "Saqueador"),
    PLAYER("PLAYER", 65, "Player"),
    POLAR_BEAR("POLAR_BEAR", 66, "Urso Polar"),
    PRIMED_TNT("PRIMED_TNT", 67, "Dinamite"),
    PUFFERFISH("PUFFERFISH", 68, "Baiacu"),
    RABBIT("RABBIT", 69, "Coelho"),
    RAVAGER("RAVAGER", 70, "Devastador"),
    SALMON("SALMON", 71, "Salm\u00e3o"),
    SHEEP("SHEEP", 72, "Ovelha"),
    SHULKER("SHULKER", 73, "Shulker"),
    SHULKER_BULLET("SHULKER_BULLET", 74, "Dardo de Shulker"),
    SILVERFISH("SILVERFISH", 75, "Silverfish"),
    SKELETON("SKELETON", 76, "Esqueleto"),
    SKELETON_HORSE("SKELETON_HORSE", 77, "Cavalo Esqueleto"),
    SLIME("SLIME", 78, "Slime"),
    SMALL_FIREBALL("SMALL_FIREBALL", 79, "Bola de Fogo Pequena"),
    SNOWBALL("SNOWBALL", 80, "Bola de Neve"),
    SNOWMAN("SNOWMAN", 81, "Golem de Neve"),
    SPECTRAL_ARROW("SPECTRAL_ARROW", 82, "Flecha Espectral"),
    SPIDER("SPIDER", 83, "Aranha"),
    SPLASH_POTION("SPLASH_POTION", 84, "Po\u00e7\u00e3o Arremess\u00e1vel"),
    SQUID("SQUID", 85, "Lula"),
    STRAY("STRAY", 86, "Esqueleto Vagante"),
    THROWN_EXP_BOTTLE("THROWN_EXP_BOTTLE", 87, "Frasco de Experi\u00eancia"),
    TIPPED_ARROW("TIPPED_ARROW", 88, "Flecha"),
    TRADER_LLAMA("TRADER_LLAMA", 89, "Lhama"),
    TRIDENT("TRIDENT", 90, "Tridente"),
    TROPICAL_FISH("TROPICAL_FISH", 91, "Peixe Tropical"),
    TURTLE("TURTLE", 92, "Tartaruga"),
    UNKNOWN("UNKNOWN", 93, "Desconhecido"),
    VEX("VEX", 94, "Fantasma"),
    VILLAGER("VILLAGER", 95, "Alde\u00e3o"),
    VINDICATOR("VINDICATOR", 96, "Vingador"),
    WANDERING_TRADER("WANDERING_TRADER", 97, "Vendedor Ambulantes"),
    WEATHER("WEATHER", 98, "Chuva"),
    WITCH("WITCH", 99, "Bruxa"),
    WITHER("WITHER", 100, "Wither"),
    WITHER_SKELETON("WITHER_SKELETON", 101, "Esqueleto Wither"),
    WITHER_SKULL("WITHER_SKULL", 102, "Cabe\u00e7a do Wither"),
    WOLF("WOLF", 103, "Lobo"),
    ZOMBIE("ZOMBIE", 104, "Zumbi"),
    ZOMBIE_HORSE("ZOMBIE_HORSE", 105, "Cavalo Zumbi"),
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 106, "Alde\u00e3o Zumbi");

    private String name;

    private EntityName(final String s, final int n, final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static EntityName valueOf(final Entity entity) {
        return valueOf(entity.getType());
    }

    public static EntityName valueOf(final EntityType entityType) {
        return valueOf(entityType.name());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
