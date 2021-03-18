package br.jotaentity.geradores.run;

import br.jotaentity.geradores.Main;
import br.jotaentity.geradores.objetos.FactionGeradoresDLL;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveGeradores extends BukkitRunnable {
    public void run() {
        if (Main.getInstance().geradores.getConfig().getString("Faction.") != null) {
            for (final String fac : Main.getInstance().geradores.getConfig().getConfigurationSection("Faction").getKeys(false)) {
                new FactionGeradoresDLL(fac).exportGeradores();
            }
        }
    }
}
