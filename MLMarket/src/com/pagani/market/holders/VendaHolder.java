package com.pagani.market.holders;

import com.pagani.market.api.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.LinkedList;

public class VendaHolder implements InventoryHolder {

    private LinkedList<Item> items;
    private LinkedList<Inventory> pags;
    private int pagactual;
    private Inventory inventory;

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public VendaHolder(LinkedList<Item> items2, Inventory inventory) {
        this.items = items2;
        this.inventory = inventory;
        this.pags = new LinkedList<Inventory>();
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public int getPagactual() {
        return pagactual;
    }

    public void setPagactual(int pagactual) {
        this.pagactual = pagactual;
    }

    public LinkedList<Inventory> getPags() {
        return pags;
    }

    public void setPags(LinkedList<Inventory> pags) {
        this.pags = pags;
    }
}