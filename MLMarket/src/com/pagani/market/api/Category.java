package com.pagani.market.api;

import com.pagani.market.enums.CategoryType;

import java.util.LinkedList;

public class Category {

    private LinkedList<Item> items;
    private CategoryType type;
    private LinkedList<HistoricItem> historicItems;

    public Category(CategoryType tipo) {
        this.items = new LinkedList<>();
        this.type = tipo;
        this.historicItems = new LinkedList<>();
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public LinkedList<Item> getItemsInCategory() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public LinkedList<HistoricItem> getHistoricItems() {
        return historicItems;
    }

    public void setHistoricItems(LinkedList<HistoricItem> historicItems) {
        this.historicItems = historicItems;
    }
}