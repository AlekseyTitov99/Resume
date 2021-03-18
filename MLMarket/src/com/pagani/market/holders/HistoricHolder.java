package com.pagani.market.holders;

import com.pagani.market.api.HistoricItem;
import com.pagani.market.enums.CategoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.LinkedList;

public class HistoricHolder implements InventoryHolder {

    private LinkedList<Inventory> pags;
    private int pagactual;
    private Inventory inventory;
    private LinkedList<HistoricItem> historicItems;
    private CategoryType categoryType;

    public HistoricHolder() {
        this.pags = new LinkedList<>();
        this.pagactual = 0;
        this.historicItems = new LinkedList<>();
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
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

    public LinkedList<HistoricItem> getHistoricItems() {
        return historicItems;
    }

    public void setHistoricItems(LinkedList<HistoricItem> historicItems) {
        this.historicItems = historicItems;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
