package com.pagani.market.api;

public class Item {

    private String itemStack;
    private String sender;
    private String getBackItem;
    private double value;
    private String realstack;
    private Long aLong;
    private boolean vendaprivada;
    private String vendeupraquem;
    private double id;

    public Item() {
        super();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getItemStack() {
        return itemStack;
    }

    public void setItemStack(String itemStack) {
        this.itemStack = itemStack;
    }

    public String getRealstack() {
        return realstack;
    }

    public void setRealstack(String realstack) {
        this.realstack = realstack;
    }

    public Long getaLong() {
        return aLong;
    }

    public void setaLong(Long aLong) {
        this.aLong = aLong;
    }

    public boolean isVendaprivada() {
        return vendaprivada;
    }

    public void setVendaprivada(boolean vendaprivada) {
        this.vendaprivada = vendaprivada;
    }

    public String getVendeupraquem() {
        return vendeupraquem;
    }

    public void setVendeupraquem(String vendeupraquem) {
        this.vendeupraquem = vendeupraquem;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getGetBackItem() {
        return getBackItem;
    }

    public void setGetBackItem(String getBackItem) {
        this.getBackItem = getBackItem;
    }
}