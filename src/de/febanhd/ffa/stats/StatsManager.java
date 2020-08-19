package de.febanhd.ffa.stats;

import de.febanhd.ffa.FFA;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class StatsManager {

    private File file = new File(FFA.getInstance().getDataFolder(), "stats.yml");
    private FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public void setKills(Player player, int kills) {
        cfg.set(player.getUniqueId().toString() + ".Kills", kills);
        try {
            cfg.save(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDeaths(Player player, int deaths) {
        cfg.set(player.getUniqueId().toString() + ".Deaths", deaths);

        try {
            cfg.save(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getKills(Player player) {
        if(!cfg.contains(player.getUniqueId().toString() + ".Kills")) {
            return 0;
        }else {
            return cfg.getInt(player.getUniqueId().toString() + ".Kills");
        }
    }

    public int getDeaths(Player player) {
        if(!cfg.contains(player.getUniqueId().toString() + ".Deaths")) {
            return 0;
        }else {
            return cfg.getInt(player.getUniqueId().toString() + ".Deaths");
        }
    }

    public boolean hasStats(Player player) {
        return cfg.contains(player.getUniqueId().toString());
    }
}
