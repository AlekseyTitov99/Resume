package br.jotaentity.geradores.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class GuiHolder implements InventoryHolder {
    private HashMap<Integer, EntityType> slots;
    private String fac;
    private int id;
    private EntityType entityType;

    public GuiHolder(final int id, final String fac) {
        this.id = id;
        this.fac = fac;
    }

    public GuiHolder(final int id, final String fac, final HashMap<Integer, EntityType> slots) {
        this.id = id;
        this.fac = fac;
        this.slots = slots;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getFac() {
        return this.fac;
    }

    public void setFac(final String fac) {
        this.fac = fac;
    }

    public HashMap<Integer, EntityType> getSlots() {
        return this.slots;
    }

    public void setSlots(final HashMap<Integer, EntityType> slots) {
        this.slots = slots;
    }

    public Inventory getInventory() {
        return Bukkit.createInventory((InventoryHolder) this, 27);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
