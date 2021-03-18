package com.pagani.bans.objetos;

import com.pagani.bans.objetos.Ban.Punish;

import java.util.LinkedList;

public class PunishUser {

    private String playername;
    private LinkedList<Punish> punições;
    private Punish actual;

    public PunishUser(String name) {
        super();
        this.playername = name;
        this.punições = new LinkedList<>();
        this.actual = null;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public LinkedList<Punish> getPunições() {
        if (punições == null){
            this.punições = new LinkedList<>();
        }
        return punições;
    }

    public void setPunições(LinkedList<Punish> punições) {
        this.punições = punições;
    }

    public Punish getActual() {
        return actual;
    }

    public void setActual(Punish actual) {
        this.actual = actual;
    }
}