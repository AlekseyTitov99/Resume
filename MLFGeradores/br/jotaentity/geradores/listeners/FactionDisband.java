package br.jotaentity.geradores.listeners;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objeto.LocationsUser;
import br.jotaentity.geradores.objeto.Storage;
import br.jotaentity.geradores.objetos.FactionGeradores;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;

public class FactionDisband implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onDisband(final EventFactionsDisband e) {
        final Player p = e.getMPlayer().getPlayer();
        final Faction fac = e.getFaction();
        final FactionGeradores api = new FactionGeradores(fac.getId());
        if (api.getQuantiaWither() != null) {
            if (api.countGeradores() > 0) {
                e.setCancelled(true);
                p.sendMessage(Main.getInstance().config.getConfig().getString("Mensagens.contemspawner").replace('&', '§'));
            } else {
                Main.getInstance().geradores.getConfig().set("Faction." + fac.getId(), (Object) null);
                Main.getInstance().geradores.saveConfig();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (mPlayer.hasFaction()) {
            if (Main.cacheloc.containsKey(mPlayer.getFactionName())) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (mPlayer.getFaction().getOnlinePlayers().containsAll(Collections.singleton(e.getPlayer()))) {
                            Main.cacheloc.remove(mPlayer.getFactionName());
                        }

                    }
                }.runTaskLaterAsynchronously(Main.getPlugin(Main.class), 20L * 3);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (mPlayer.hasFaction()) {
            if (!Main.cacheloc.containsKey(mPlayer.getFactionName())) {
                Storage storage = new Storage();
                storage.importObject(mPlayer.getFactionName());
            }
        }
    }

    public static String chunkSerializer(Chunk chunk) {
        return "X" + chunk.getX() + "Z" + chunk.getZ();
    }

    @EventHandler (ignoreCancelled = true)
    public void onChunkChange(EventFactionsChunksChange e) {
        for (PS chunk : e.getChunks()) {
            Faction faction = e.getMPlayer().getFaction();
            if (e.getNewFaction().getTag().equalsIgnoreCase("znl")) {
                if (Main.cacheloc.containsKey(faction.getName())) {
                    LocationsUser locationsUser = Main.cacheloc.get(faction.getName());
                    locationsUser.getCache().entrySet().removeIf(s -> s.getKey().equalsIgnoreCase(chunkSerializer(chunk.asBukkitChunk())));
                    Storage storage = new Storage();
                    storage.exportObject(locationsUser);
                }
            }
        }
    }

}