package com.pagani.auth;

public class Storage {


    private Main main;

    public Storage(Main instance) {
        this.main = instance;
    }

    public Boolean hasRegister(String name){
        boolean x = main.getConfig().contains("users." + name);
        return x;
    }

    public void register(String player,String code){
        main.getConfig().set("users."+player,code);
        main.saveConfig();
        main.reloadConfig();
    }

    public String getIdentifier(String name){
        String x = main.getConfig().getString("users." + name);
        return x;
    }
}