package com.pagani.dungeon.objetos;

import com.google.gson.GsonBuilder;
import com.pagani.dungeon.Main;
import com.pagani.dungeon.mysql.AtlasStorage;

import java.util.LinkedList;

public class DungeonUser {

    private String username;
    private LinkedList<String> cache;
    private String partyname;
    private String partyuuid;
    private int tickets;

    public DungeonUser(String user){
        this.username = user;
        this.cache = new LinkedList<>();
        this.partyname = "";
        this.partyuuid = "";
    }

    public Party getParty(){
        if (Main.cacheparty.containsKey(partyname)){
            if (!Main.cacheparty.get(partyname).getUsers().containsKey(this.username)){
                return null;
            }
            return Main.cacheparty.get(partyname);
        } else {
        }
        return null;
    }

    public boolean hasParty(){
        if (Main.cacheparty.containsKey(partyname)){
            this.partyuuid = Main.cacheparty.get(partyname).getPartyuuid();
            return true;
        } else {
            if (this.partyname.isEmpty() || this.partyname == null){
                return false;
            } else {
                AtlasStorage.importParty(partyname,false,this);
                if (Main.cache.containsKey(partyname)){
                    return true;
                }
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LinkedList<String> getCache() {
        return cache;
    }

    public void setCache(LinkedList<String> cache) {
        this.cache = cache;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public String getPartyuuid() {
        return partyuuid;
    }

    public void setPartyuuid(String partyuuid) {
        this.partyuuid = partyuuid;
    }
}