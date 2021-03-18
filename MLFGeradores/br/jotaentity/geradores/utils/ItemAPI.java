package br.jotaentity.geradores.utils;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ItemAPI {
    private static Field map;
    private static Method asNMSCopy;
    private static Method hasNBTTagCompound;
    private static Method getNBTTagCompound;
    private static Collection<Map.Entry<EntityType, String[]>> entityNameBR;

    static {
        ItemAPI.entityNameBR = Collections.emptySet();
    }

    public static EntityType getEntityType(final ItemStack item) {
        final Map<String, Object> map = getNBTTags(item);
        if (map == null) {
            return null;
        }
        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                final String value = entry.getValue().toString().toUpperCase().replace(":", "").replace("'", "").replace("{", "").replace("}", "").replace(" ", "").replace("\"", "").replace("ENTITYID", "");
                try {
                    final EntityType tipo = EntityType.valueOf(value);
                    return tipo;
                } catch (Throwable t) {
                    try {
                        final EntityType tipo = EntityType.fromName(value);
                        if (tipo != null) {
                            return tipo;
                        }
                    } catch (Throwable t2) {
                    }
                    try {
                        final EntityType tipo = EntityType.fromId((int) Integer.valueOf(value));
                        if (tipo != null) {
                            return tipo;
                        }
                        continue;
                    } catch (Throwable t3) {
                    }
                }
            } catch (Throwable t4) {
            }
        }
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName()) {
                final String name = meta.getDisplayName().replace(" ", "").toUpperCase();
                final EntityType entity = getEntityByBrazilianName(name);
                if (entity != null) {
                    return entity;
                }
                for (int i = 0; i < name.length(); ++i) {
                    int j = 0;
                    while (j <= name.length()) {
                        try {
                            final EntityType tipo2 = EntityType.valueOf(name.substring(i, j));
                            return tipo2;
                        } catch (Throwable t5) {
                            try {
                                final EntityType tipo2 = EntityType.fromName(name.substring(i, j));
                                if (tipo2 != null) {
                                    return tipo2;
                                }
                            } catch (Throwable t6) {
                            }
                            ++j;
                        }
                    }
                }
            }
            if (meta.hasLore()) {
                final List<String> lore = (List<String>) meta.getLore();
                for (int l = 0; l < lore.size(); ++l) {
                    final String line = lore.get(l).replace(" ", "").toUpperCase();
                    final EntityType entity2 = getEntityByBrazilianName(line);
                    if (entity2 != null) {
                        return entity2;
                    }
                    for (int k = 0; k < line.length(); ++k) {
                        int m = 0;
                        while (m <= line.length()) {
                            try {
                                final EntityType tipo3 = EntityType.valueOf(line.substring(k, m));
                                return tipo3;
                            } catch (Throwable t7) {
                                try {
                                    final EntityType tipo3 = EntityType.fromName(line.substring(k, m));
                                    if (tipo3 != null) {
                                        return tipo3;
                                    }
                                } catch (Throwable t8) {
                                }
                                ++m;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static EntityType getEntityByBrazilianName(final String string) {
        for (final Map.Entry<EntityType, String[]> entry : ItemAPI.entityNameBR) {
            String[] array;
            for (int length = (array = entry.getValue()).length, i = 0; i < length; ++i) {
                final String brazilianName = array[i];
                if (string.contains(brazilianName)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    private static Map<String, Object> getNBTTags(final ItemStack item) {
        try {
            final Object CraftItemStack = ItemAPI.asNMSCopy.invoke(null, item);
            final boolean hasNBTTag = (boolean) ItemAPI.hasNBTTagCompound.invoke(CraftItemStack, new Object[0]);
            if (hasNBTTag) {
                final Object NBTTagCompound = ItemAPI.getNBTTagCompound.invoke(CraftItemStack, new Object[0]);
                return (Map<String, Object>) ItemAPI.map.get(NBTTagCompound);
            }
            return null;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void load() {
        try {
            final Class<?> CraftItemStackClass = ReflectionUtils.getOBClass("inventory.CraftItemStack");
            final Class<?> ItemStackClass = ReflectionUtils.getNMSClass("ItemStack");
            final Class<?> NBTTagCompoundClass = ReflectionUtils.getNMSClass("NBTTagCompound");
            (ItemAPI.map = NBTTagCompoundClass.getDeclaredField("map")).setAccessible(true);
            ItemAPI.asNMSCopy = CraftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
            ItemAPI.getNBTTagCompound = ItemStackClass.getDeclaredMethod("getTag", (Class<?>[]) new Class[0]);
            ItemAPI.hasNBTTagCompound = ItemStackClass.getDeclaredMethod("hasTag", (Class<?>[]) new Class[0]);
            final Map<EntityType, String[]> mapEntityNameBR = new LinkedHashMap<EntityType, String[]>();
            mapEntityNameBR.put(EntityType.CAVE_SPIDER, new String[]{"ARANHADACAVERNA"});
            mapEntityNameBR.put(EntityType.CHICKEN, new String[]{"GALINHA"});
            mapEntityNameBR.put(EntityType.COW, new String[]{"VACA"});
            mapEntityNameBR.put(EntityType.IRON_GOLEM, new String[]{"GOLEM", "VILLAGER_GOLEM", "VILLAGERGOLEM", "GOLEMDEFERRO", "IRONGOLEM"});
            mapEntityNameBR.put(EntityType.MAGMA_CUBE, new String[]{"CUBODEMAGMA"});
            mapEntityNameBR.put(EntityType.PIG_ZOMBIE, new String[]{"PIG_ZOMBIE", "PIGZOMBIE", "PORCOZUMBI", "PORCOZOMBIE"});
            mapEntityNameBR.put(EntityType.PIG, new String[]{"PORCO"});
            mapEntityNameBR.put(EntityType.SHEEP, new String[]{"OVELHA"});
            mapEntityNameBR.put(EntityType.SKELETON, new String[]{"ESQUELETO"});
            mapEntityNameBR.put(EntityType.SPIDER, new String[]{"ARANHA"});
            mapEntityNameBR.put(EntityType.ZOMBIE, new String[]{"ZUMBI"});
            ItemAPI.entityNameBR = mapEntityNameBR.entrySet();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
