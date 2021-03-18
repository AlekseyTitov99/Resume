package com.pagani.market.objeto;

import com.pagani.market.api.Item;

import java.util.LinkedList;

public class User {

    private String user;
    private LinkedList<Item> pessoal;
    private LinkedList<Item> expirados;
    private LinkedList<Item> vendendo;

    public User(String usuário) {
        this.user = usuário;
        this.expirados = new LinkedList<>();
        this.pessoal = new LinkedList<>();
        this.vendendo = new LinkedList<>();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LinkedList<Item> getExpirados() {
        if (expirados == null){
            setExpirados(new LinkedList<>());
        }
        return expirados;
    }

    public void setExpirados(LinkedList<Item> expirados) {
        this.expirados = expirados;
    }

    public LinkedList<Item> getPessoal() {
        if (pessoal == null){
            setPessoal(new LinkedList<>());
        }
        return pessoal;
    }

    public void setPessoal(LinkedList<Item> pessoal) {
        this.pessoal = pessoal;
    }

    public LinkedList<Item> getVendendo() {
        if (this.vendendo == null){
            this.setVendendo(new LinkedList<>());
        }
        return vendendo;
    }

    public void setVendendo(LinkedList<Item> vendendo) {
        this.vendendo = vendendo;
    }
}