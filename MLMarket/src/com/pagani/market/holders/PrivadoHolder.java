package com.pagani.market.holders;

import com.pagani.market.api.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.LinkedList;
import java.util.List;

public class PrivadoHolder implements InventoryHolder {

    private LinkedList<Item> items;
    private LinkedList<Inventory> pags;
    private int pagactual;
    private Inventory inventory;

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public PrivadoHolder(LinkedList<Item> linkedList, List<Integer> x, int pagactual3, LinkedList<Inventory> invs) {
        this.items = linkedList;
        this.pagactual = pagactual3;
        this.pags = invs;
    }

    public LinkedList<Item> getItems() {
        return items;
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}