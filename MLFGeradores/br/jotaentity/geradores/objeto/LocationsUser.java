package br.jotaentity.geradores.objeto;

import java.util.LinkedHashMap;

public class LocationsUser {

    private String facção;
    private LinkedHashMap<String, LinkedHashMap<String,String>> cache;
    private long use;

    public LocationsUser(String facname) {
        this.facção = facname;
        this.cache = new LinkedHashMap<>();
        this.use = 0;
    }

    public String getFacção() {
        return facção;
    }

    public void setFacção(String facção) {
        this.facção = facção;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getCache() {
        return cache;
    }

    public void setCache(LinkedHashMap<String, LinkedHashMap<String, String>> cache) {
        this.cache = cache;
    }

    public long getUse() {
        return use;
    }

    public void setUse(long use) {
        this.use = use;
    }

}