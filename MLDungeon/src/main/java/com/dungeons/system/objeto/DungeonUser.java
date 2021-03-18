package com.dungeons.system.objeto;

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