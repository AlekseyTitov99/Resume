package com.pagani.dungeon.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Hidder {

    public static void hideNPCNameTag(Player from, Entity p) {
		Entity npc = (Entity) p;
        Scoreboard score = from.getScoreboard();
        Team team = null;
        for(Team t : score.getTeams()) {
            if(t.getName().equalsIgnoreCase("NPCs")) {
                team = t;
            }
        }
        if(team == null) {
            team = score.registerNewTeam("NPCs");
        }
        team.addEntry(npc.getName());
        team.setNameTagVisibility(NameTagVisibility.NEVER);
        from.setScoreboard(score);
    }

}