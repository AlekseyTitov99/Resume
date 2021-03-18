package com.pagani.dungeon.api;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;

public class InvHolder implements InventoryHolder{

    private ArrayList<Inventory> inventories;
    private boolean morethanone;
    private Inventory inventory;

        @Override
        public Inventory getInventory() {
            return this.inventory;
        }

    public boolean isMorethanone() {
        return morethanone;
    }

    public void setMorethanone(boolean morethanone) {
        this.morethanone = morethanone;
    }

    public ArrayList<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(ArrayList<Inventory> inventories) {
        this.inventories = inventories;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}