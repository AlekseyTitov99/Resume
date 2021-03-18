package com.dungeons.system.scoreboard;

import com.dungeons.system.dungeon.Dungeon;
import com.google.common.base.Strings;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.UUID;
import java.util.WeakHashMap;

class RandomUUID
{
    public String getUUID()
    {
        return
                UUID.randomUUID().toString().substring(0, 6) + UUID.randomUUID().toString().substring(0, 6) + (int)Math.round(Math.random() * 100.0D);
    }
}

public class ScoreBoard {

    public static WeakHashMap<Player, ScoreBoardAPI> boards = new WeakHashMap<>();

    public static void createLobbyScoreBoard(Player p, Dungeon ds) {
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        String UUIDRandom = new RandomUUID().getUUID();

        ScoreBoardAPI sb = new ScoreBoardAPI("  §6§lLobby  ", UUIDRandom);
        sb.blankLine(4);
        sb.add("  §fJogadores: " +ds.getJogadores().size(),3);
        sb.add("  §fPepitas: " + "0/5000", 2);
        sb.blankLine(1);
        sb.add("§aminelandia.com.br", 0);
        sb.build();
        ScoreBoard.boards.remove(p);
        sb.send(p);
        ScoreBoard.boards.put(p, sb);
    }

    public static void createDungeonScoreBoardWithoutBoss(Player p,Dungeon ds) {
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        String UUIDRandom = new RandomUUID().getUUID();
        ScoreBoardAPI sb = new ScoreBoardAPI("  §6§lDungeon  ", UUIDRandom);
        sb.blankLine(6);
        sb.add(" §fMobs vivos: ",5);
        sb.add(" §fFase atual: ",4);
        sb.add(" §fJogadores: ",3);
        sb.add(" §fPepitas: ", 2);
        sb.blankLine(1);
        sb.add("§aminelandia.com.br", 0);
        sb.build();
        ScoreBoard.boards.remove(p);
        sb.send(p);
        ScoreBoard.boards.put(p, sb);
    }

    public static void updateScoreBoard(Player p,Dungeon ds) {
        DecimalFormatSymbols DFS = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat FORMATTER = new DecimalFormat("###,###,###", DFS);
        ScoreBoardAPI sb = (ScoreBoardAPI) ScoreBoard.boards.get(p);
        if (ds.getCurrentfase().isTemboss() && ds.getCurrentfase().getBoss() != null){
            LivingEntity livingEntity = ds.getCurrentfase().getBoss();
            if (livingEntity.isDead()){
                sb.update("§c♥♥♥♥♥", 6);
            } else {
                sb.update(getProgressBar((int) livingEntity.getHealth(), (int) livingEntity.getMaxHealth(), 5, '♥', ChatColor.RED, ChatColor.GRAY), 6);
            }
            sb.update("§a" + ds.getEntityAlive() + "/" + ds.getEntities().size(), 5);
            if (ds.getCurrentfase().isIsfinal()){
                sb.update("§a"+(ds.getFase()-1)+"/4", 4);
            } else {
                sb.update("§a"+(ds.getFase()-1)+"/4", 4);
            }
            if (!ds.getDead().isEmpty()){
                sb.update("§a"+(ds.getJogadores().size() - ds.getDead().size()) + "/" +ds.getJogadores().size(), 3);
            } else {
                sb.update("§a"+(ds.getJogadores().size()) + "/" +ds.getJogadores().size(), 3);
            }
            sb.update("§a"+ds.getPepitas().get(p.getName())+"/5000", 2);
        } else {
            sb.update("§a"+ds.getEntityAlive()+ "/" + ds.getEntities().size(), 5);
            sb.update("§a"+(ds.getFase()-1)+"/4", 4);
            if (!ds.getDead().isEmpty()){
                sb.update("§a"+(ds.getJogadores().size() - ds.getDead().size()) + "/" +ds.getJogadores().size(), 3);
            } else {
                sb.update("§a"+(ds.getJogadores().size()) + "/" +ds.getJogadores().size(), 3);
            }
            sb.update("§a"+ds.getPepitas().get(p.getName())+"/200", 2);
        }
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                        ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static void createDungeonScoreBoardWithBoss(Player p,Dungeon DS) {
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        String UUIDRandom = new RandomUUID().getUUID();
        ScoreBoardAPI sb = new ScoreBoardAPI("  §6§lDungeon  ", UUIDRandom);
        sb.blankLine(7);
        sb.add(" §fBoss: ",6);
        sb.add(" §fMobs vivos: ",5);
        sb.add(" §fFase atual: ",4);
        sb.add(" §fJogadores: ",3);
        sb.add(" §fPepitas: ", 2);
        sb.blankLine(1);
        sb.add("§eminelandia.com.br", 0);
        sb.build();
        ScoreBoard.boards.remove(p);
        sb.send(p);
        ScoreBoard.boards.put(p, sb);
    }


}