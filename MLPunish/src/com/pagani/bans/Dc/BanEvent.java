package com.pagani.bans.Dc;

import com.pagani.bans.objetos.Ban.Punish;
import net.md_5.bungee.api.plugin.Event;

public class BanEvent extends Event {

    private String punido;
    private Punish punish;

    public BanEvent(String pund, Punish ax){
        this.punido = pund;
        this.punish = ax;
    }

    public Punish getPunish() {
        return punish;
    }

    public void setPunish(Punish punish) {
        this.punish = punish;
    }

    public String getPunido() {
        return punido;
    }

    public void setPunido(String punido) {
        this.punido = punido;
    }
}