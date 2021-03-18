package com.dungeons.system.objeto;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Fase {
    
    private String party;
    private HashMap<EntityType, Integer> entitys;
    private ArrayList<Location> locations;
    private ArrayList<String> items;
    private HashMap<String, LinkedList<String>> coletados;
    private World world;
    private boolean isfinal;
    private Gate gate;
    private String getbacklocation;
    private String chestlocations;
    private String vendedorlocation;
    private boolean temboss;
    private EntityType bosstype;
    private String bosslocation;
    private LivingEntity boss;

    public Fase(String gatepos1, String gatepos2, String world, HashMap<EntityType,Integer> cacheentity,String chest,String vendedor,boolean hasboss){
        super();
        this.gate = new Gate(gatepos1,gatepos2);
        this.world = Bukkit.getWorld(world);
        this.isfinal = false;
        this.entitys = cacheentity;
        this.coletados = new HashMap<>();
        this.chestlocations = chest;
        this.vendedorlocation = vendedor;
        this.temboss = hasboss;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public boolean isIsfinal() {
        return isfinal;
    }

    public void setIsfinal(boolean isfinal) {
        this.isfinal = isfinal;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public String getGetbacklocation() {
        return getbacklocation;
    }

    public void setGetbacklocation(String getbacklocation) {
        this.getbacklocation = getbacklocation;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }


    public HashMap<EntityType, Integer> getEntitys() {
        return entitys;
    }

    public void setEntitys(HashMap<EntityType, Integer> entitys) {
        this.entitys = entitys;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public HashMap<String, LinkedList<String>> getColetados() {
        return coletados;
    }

    public void setColetados(HashMap<String, LinkedList<String>> coletados) {
        this.coletados = coletados;
    }

    public String getChestlocations() {
        return chestlocations;
    }

    public void setChestlocations(String chestlocations) {
        this.chestlocations = chestlocations;
    }

    public String getVendedorlocation() {
        return vendedorlocation;
    }

    public void setVendedorlocation(String vendedorlocation) {
        this.vendedorlocation = vendedorlocation;
    }

    public boolean isTemboss() {
        return temboss;
    }

    public void setTemboss(boolean temboss) {
        this.temboss = temboss;
    }

    public EntityType getBosstype() {
        return bosstype;
    }

    public void setBosstype(EntityType bosstype) {
        this.bosstype = bosstype;
    }

    public String getBosslocation() {
        return bosslocation;
    }

    public void setBosslocation(String bosslocation) {
        this.bosslocation = bosslocation;
    }

    public LivingEntity getBoss() {
        return boss;
    }

    public void setBoss(LivingEntity boss) {
        this.boss = boss;
    }
}