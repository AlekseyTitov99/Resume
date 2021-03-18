package com.dungeons.system.objeto;

import com.dungeons.system.enums.Cargo;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Party {

    private String name;
    private HashMap<String,Cargo> users;
    private ArrayList<String> convites;
    private String partyuuid;
    private String sala;

    public Party(String name){
        this.users = new HashMap<>();
        this.name = name;
        this.convites = new ArrayList<>();
        this.partyuuid = RandomStringUtils.randomAlphanumeric(30).toUpperCase();
    }

    public HashMap<String, Cargo> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, Cargo> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getConvites() {
        return convites;
    }

    public void setConvites(ArrayList<String> convites) {
        this.convites = convites;
    }

    public String getPartyuuid() {
        if (partyuuid == null || partyuuid.isEmpty()){
            this.partyuuid = RandomStringUtils.randomAlphanumeric(30).toUpperCase();
        }
        return partyuuid;
    }

    public void setPartyuuid(String partyuuid) {
        this.partyuuid = partyuuid;
    }


    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
}