package de.febanhd.ffa;

import de.febanhd.ffa.commands.SetSpawnCommand;
import de.febanhd.ffa.commands.SetSpawnRegionCommand;
import de.febanhd.ffa.kit.KitManager;
import de.febanhd.ffa.listener.GameProtectionListener;
import de.febanhd.ffa.listener.KitListener;
import de.febanhd.ffa.listener.PlayerConnectionListener;
import de.febanhd.ffa.location.LocationManager;
import de.febanhd.ffa.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin {

    private static FFA instance;
    public static final String PREFIX = "§8[§5M-FFA§8] §r";

    private KitManager kitManager;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        instance = this;

        this.statsManager = new StatsManager();
        this.kitManager = new KitManager();
        new LocationManager();

        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getCommand("setspawnregion").setExecutor(new SetSpawnRegionCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerConnectionListener(), this);
        pm.registerEvents(new GameProtectionListener(), this);
        pm.registerEvents(new KitListener(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.getWorlds().forEach(world -> {
                world.setTime(1000);
                world.setStorm(false);
                world.setThundering(false);
            });
        }, 0, 10);
    }

    @Override
    public void onDisable() {

    }

    public static FFA getInstance() {
        return instance;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
