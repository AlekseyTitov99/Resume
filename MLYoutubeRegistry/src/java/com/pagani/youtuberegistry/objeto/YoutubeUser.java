package com.pagani.youtuberegistry.objeto;


import java.util.LinkedHashMap;
import java.util.LinkedList;

public class YoutubeUser {

    private String user;
    private String channel_id;
    private long cooldown;
    private LinkedHashMap<Long,String> cache;

    public YoutubeUser(){
        this.channel_id = "";
        this.cooldown = 0;
        this.user = "";
        this.cache = new LinkedHashMap<>();
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LinkedHashMap<Long, String> getCache() {
        return cache;
    }

    public void setCache(LinkedHashMap<Long, String> cache) {
        this.cache = cache;
    }
}