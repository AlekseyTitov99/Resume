package br.jotaentity.geradores;

import br.jotaentity.geradores.listeners.*;
import br.jotaentity.geradores.objeto.LocationsUser;
import br.jotaentity.geradores.objeto.Storage;
import br.jotaentity.geradores.objetos.FactionGeradoresDLL;
import br.jotaentity.geradores.run.SaveGeradores;
import br.jotaentity.geradores.utils.Config;
import br.jotaentity.geradores.utils.ItemAPI;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {
    public Config config;
    public Config geradores;

    public static HashMap<Faction, Player> cache = new HashMap<>();
    public static HashMap<String, LocationsUser> cacheloc = new HashMap<>();

    public void onEnable() {
        ItemAPI.load();
        (this.config = new Config(this, "config.yml")).saveDefaultConfig();
        (this.geradores = new Config(this, "geradores.dll")).saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents((Listener) new PlayerCommand(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new InventoryClick(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new FactionDisband(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener)new InventoryClickId2(),(Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InventoryClick2(),(Plugin)this);
        if (getInstance().geradores.getConfig().getString("Faction.") != null) {
            for (final String fac : getInstance().geradores.getConfig().getConfigurationSection("Faction").getKeys(false)) {
                new FactionGeradoresDLL(fac).importGeradores();
            }
        }
        new SaveGeradores().runTaskTimerAsynchronously((Plugin) this, 0L, (long) (1200 * getInstance().config.getConfig().getInt("TempoSave")));
        try {
            File pasta = new File(Main.getPlugin(Main.class).getDataFolder() + File.separator + "storage");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
        } catch (SecurityException e) {
            Bukkit.getConsoleSender().sendMessage("DEU RUIM");
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            MPlayer mPlayer = MPlayer.get(player);
            if (mPlayer.hasFaction()) {
                if (!Main.cacheloc.containsKey(mPlayer.getFactionName())) {
                    Storage storage = new Storage();
                    storage.importObject(mPlayer.getFactionName());
                }
            }
        }
    }

    public void onDisable() {
        if (getInstance().geradores.getConfig().getString("Faction.") != null) {
            for (final String fac : getInstance().geradores.getConfig().getConfigurationSection("Faction").getKeys(false)) {
                if (fac != null) {
                    new FactionGeradoresDLL(fac).exportGeradores();
                }
            }
        }
        for (Map.Entry<String, LocationsUser> stringLocationsUserEntry : cacheloc.entrySet()) {
            Storage storage = new Storage();
            storage.exportObject(stringLocationsUserEntry.getValue());
        }
    }

    public static Main getInstance() {
        return (Main) getPlugin((Class) Main.class);
    }

}