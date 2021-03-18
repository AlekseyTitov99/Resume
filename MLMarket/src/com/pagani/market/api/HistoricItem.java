package com.pagani.market.api;

import com.pagani.market.enums.CategoryType;
import com.pagani.market.enums.HistoricEnum;

public class HistoricItem {

    private HistoricEnum historicEnum;
    private CategoryType category;
    private String item;
    private int id;

    public HistoricItem(String itemstack, HistoricEnum historicEnum2, CategoryType type) {
        super();
        this.historicEnum = historicEnum2;
        this.category = type;
        this.item = itemstack;
    }

    public HistoricEnum getHistoricEnum() {
        return historicEnum;
    }

    public void setHistoricEnum(HistoricEnum historicEnum) {
        this.historicEnum = historicEnum;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

