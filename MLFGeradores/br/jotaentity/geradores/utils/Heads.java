package br.jotaentity.geradores.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class Heads {
    public static ItemStack BLAZE;
    public static ItemStack CAVE_SPIDER;
    public static ItemStack CHICKEN;
    public static ItemStack COW;
    public static ItemStack CREEPER;
    public static ItemStack ENDERMAN;
    public static ItemStack IRONGOLEM;
    public static ItemStack MAGMA_CUBE;
    public static ItemStack PIG;
    public static ItemStack PIG_ZOMBIE;
    public static ItemStack SHEEP;
    public static ItemStack SKELETON;
    public static ItemStack SLIME;
    public static ItemStack SPIDER;
    public static ItemStack WITHER;
    public static ItemStack ZOMBIE;
    public static ItemStack UP;
    public static ItemStack DOWN;
    public static ItemStack MUSHROOM_COW;
    public static ItemStack WITCH;
    public static ItemStack GHAST;
    public static ItemStack HORSE;

    static {
        Heads.BLAZE = getSkull("http://textures.minecraft.net/texture/b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0");
        Heads.CAVE_SPIDER = getSkull("http://textures.minecraft.net/texture/41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224");
        Heads.CHICKEN = getSkull("http://textures.minecraft.net/texture/1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893");
        Heads.COW = getSkull("http://textures.minecraft.net/texture/5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5");
        Heads.CREEPER = getSkull("http://textures.minecraft.net/texture/f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952");
        Heads.ENDERMAN = getSkull("http://textures.minecraft.net/texture/7a59bb0a7a32965b3d90d8eafa899d1835f424509eadd4e6b709ada50b9cf");
        Heads.IRONGOLEM = getSkull("http://textures.minecraft.net/texture/89091d79ea0f59ef7ef94d7bba6e5f17f2f7d4572c44f90f76c4819a714");
        Heads.MAGMA_CUBE = getSkull("http://textures.minecraft.net/texture/38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429");
        Heads.PIG = getSkull("http://textures.minecraft.net/texture/e239dc5e1bacd518f8b2a2fef515c6a4badeb2c64539313b5fe26f0b899906a");
        Heads.PIG_ZOMBIE = getSkull("http://textures.minecraft.net/texture/95fb2df754c98b742d35e7b81a1eeac9d37c69fc8cfecd3e91c67983516f");
        Heads.SHEEP = getSkull("http://textures.minecraft.net/texture/f31f9ccc6b3e32ecf13b8a11ac29cd33d18c95fc73db8a66c5d657ccb8be70");
        Heads.SKELETON = getSkull("http://textures.minecraft.net/texture/3e8eb33915ccd705a5a42d9cdf463a254bb6e2f1b3aab3e52ae719ef39498d9");
        Heads.SLIME = getSkull("http://textures.minecraft.net/texture/a20e84d32d1e9c919d3fdbb53f2b37ba274c121c57b2810e5a472f40dacf004f");
        Heads.SPIDER = getSkull("http://textures.minecraft.net/texture/c87a96a8c23b83b32a73df051f6b84c2ef24d25ba4190dbe74f11138629b5aef");
        Heads.WITHER = getSkull("http://textures.minecraft.net/texture/cdf74e323ed41436965f5c57ddf2815d5332fe999e68fbb9d6cf5c8bd4139f");
        Heads.ZOMBIE = getSkull("http://textures.minecraft.net/texture/311dd91ee4d31ddd591d2832ea1ec080f2eded33ab89ee1db8b04b26a68a");
        Heads.UP = getSkull("http://textures.minecraft.net/texture/1ad6c81f899a785ecf26be1dc48eae2bcfe777a862390f5785e95bd83bd14d");
        Heads.DOWN = getSkull("http://textures.minecraft.net/texture/882faf9a584c4d676d730b23f8942bb997fa3dad46d4f65e288c39eb471ce7");
        Heads.MUSHROOM_COW = getSkull("http://textures.minecraft.net/texture/a163bc416b8e6058f92b231e9a524b7fe118eb6e7eeab4ad16d1b52a3ec04fcd");
        Heads.WITCH = getSkull("http://textures.minecraft.net/texture/20e13d18474fc94ed55aeb7069566e4687d773dac16f4c3f8722fc95bf9f2dfa");
        Heads.GHAST = getSkull("http://textures.minecraft.net/texture/7a8b714d32d7f6cf8b37e221b758b9c599ff76667c7cd45bbc49c5ef19858646");
        Heads.HORSE = getSkull("http://textures.minecraft.net/texture/26872e47a583f9cb1af290394b6fc73f75107fe0abfffe2dfe1de23bdaed22a");
    }

    public static ItemStack getSkull(final String url) {
        final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), (String) null);
        final byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        try {
            final Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
            skull.setItemMeta((ItemMeta) skullMeta);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return skull;
    }
}
