package com.pagani.market.holders;

import com.pagani.market.api.Item;
import com.pagani.market.api.Scroller;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {

    private Scroller scroller;
    private Item item;

    public GuiHolder(Scroller x, Item jk) {
        this.scroller = x;
        this.item = jk;
    }

    public Inventory getInventory() {
        return null;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Scroller getScroller() {
        return scroller;
    }

    public void setScroller(Scroller scroller) {
        this.scroller = scroller;
    }
}