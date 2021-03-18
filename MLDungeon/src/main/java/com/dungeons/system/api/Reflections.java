package com.dungeons.system.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflections {

    public static void setMobFollowRange(Entity entity, double range) {
        try {
            Object craftLivingEntity = CRAFT_LIVING_ENTITY.cast(entity);
            Object entityLiving = GET_HANDLE.invoke(craftLivingEntity);
            Object attributeInstance = GET_ATTRIBUTE_INSTANCE.invoke(entityLiving, FOLLOW_RANGE);
            SET_VALUE.invoke(attributeInstance, range);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMobFollowSpeed(Entity entity, double speed) {
        try {
            Object craftLivingEntity = CRAFT_LIVING_ENTITY.cast(entity);
            Object entityLiving = GET_HANDLE.invoke(craftLivingEntity);
            Object attributeInstance = GET_ATTRIBUTE_INSTANCE.invoke(entityLiving, SPEED);
            SET_VALUE.invoke(attributeInstance, speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableNoclip(Entity ent){
        try {
            Object craftLivingEntity = CRAFT_LIVING_ENTITY.cast(ent);
            Object entityLiving = GET_HANDLE.invoke(craftLivingEntity);
            Object entity = ENTITY.cast(entityLiving);
            NO_CLIP.set(entity,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method GET_HANDLE, GET_ATTRIBUTE_INSTANCE, SET_VALUE;
    private static Class CRAFT_LIVING_ENTITY, ENTITY;
    private static Object FOLLOW_RANGE, SPEED;
    private static Field NO_CLIP;
    private static String version = Bukkit.getServer().getClass().getPackage().getName().replace('.', ',').split(",")[3];

    private static Class getClass(String clazz) throws ClassNotFoundException {
        return Class.forName(clazz.replace("#x", version));
    }

    static {
        try {
            Class ATTRIBUTES = getClass("net.minecraft.server.#x.GenericAttributes");
            ENTITY = getClass("net.minecraft.server.#x.Entity");
            NO_CLIP = ENTITY.getField("noclip");
            CRAFT_LIVING_ENTITY = getClass("org.bukkit.craftbukkit.#x.entity.CraftLivingEntity");
            GET_HANDLE = CRAFT_LIVING_ENTITY.getMethod("getHandle");
            GET_ATTRIBUTE_INSTANCE = getClass("net.minecraft.server.#x.EntityLiving").getMethod("getAttributeInstance", getClass("net.minecraft.server.#x.IAttribute"));
            FOLLOW_RANGE = ATTRIBUTES.getField("FOLLOW_RANGE").get(null);
            SPEED = ATTRIBUTES.getField("MOVEMENT_SPEED").get(null);
            SET_VALUE = getClass("net.minecraft.server.#x.AttributeInstance").getMethod("setValue", double.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}