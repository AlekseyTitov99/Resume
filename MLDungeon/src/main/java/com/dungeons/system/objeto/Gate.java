package com.dungeons.system.objeto;

import com.dungeons.system.api.LocationEncoder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class Gate {

    private String pos1;
    private String pos2;
    private HashMap<Location,Material> back;

    public Gate(String position1,String position2){
        super();
        this.pos1 = position1;
        this.pos2 = position2;
        this.back = new HashMap<>();
    }

    public void openGate(){
        Location posi1 = LocationEncoder.getDeserializedLocation(pos1);
        Location posi2 = LocationEncoder.getDeserializedLocation(pos2);
        int topBlockX = (Math.max(posi1.getBlockX(), posi2.getBlockX()));
        int bottomBlockX = (Math.min(posi1.getBlockX(), posi2.getBlockX()));

        int topBlockY = (Math.max(posi1.getBlockY(), posi2.getBlockY()));
        int bottomBlockY = (Math.min(posi1.getBlockY(), posi2.getBlockY()));

        int topBlockZ = (Math.max(posi1.getBlockZ(), posi2.getBlockZ()));
        int bottomBlockZ = (Math.min(posi1.getBlockZ(), posi2.getBlockZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = posi1.getWorld().getBlockAt(x, y, z);
                    back.put(block.getLocation(),block.getType());
                    block.setType(Material.AIR);
                }
            }
        }
    }

    public void closeGate(){
        for (Map.Entry<Location, Material> locationMaterialEntry : back.entrySet()) {
            locationMaterialEntry.getKey().getBlock().setType(locationMaterialEntry.getValue());
        }
    }

    public HashMap<Location, Material> getBack() {
        return back;
    }

    public void setBack(HashMap<Location, Material> back) {
        this.back = back;
    }

    public String getPos1() {
        return pos1;
    }

    public String getPos2() {
        return pos2;
    }

    public void setPos1(String pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(String pos2) {
        this.pos2 = pos2;
    }

}