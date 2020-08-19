package de.febanhd.ffa.location;

import com.google.common.collect.Maps;
import de.febanhd.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LocationManager {

    private static LocationManager instance;

    private File file = new File(FFA.getInstance().getDataFolder(), "data.yml");
    private FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    private HashMap<String, Location> locations = Maps.newHashMap();
    private Cuboid spawnRegion;

    public LocationManager() {
        instance = this;
        this.initLocations();
    }

    public void initLocations() {
        try {
            locations.put("spawn", this.getLocationFromConfig("Spawn.Location"));

            Location spawnRagion1 = this.getLocationFromConfig("Spawn.Region.1");
            Location spawnRagion2 = this.getLocationFromConfig("Spawn.Region.2");

            this.spawnRegion = new Cuboid(spawnRagion1, spawnRagion2);
        }catch (NullPointerException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(FFA.PREFIX + "§cCant load all Locations");
        }
    }

    public void setLocation(String path, Location location) {
        cfg.set(path + ".World", location.getWorld().getName());
        cfg.set(path + ".X", location.getX());
        cfg.set(path + ".Y", location.getY());
        cfg.set(path + ".Z", location.getZ());
        cfg.set(path + ".Yaw", location.getYaw());
        cfg.set(path + ".Pitch", location.getPitch());

        try {
            cfg.save(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Location getLocationFromConfig(String path) {
        try {

            World world = Bukkit.getWorld(cfg.getString(path + ".World"));
            double x = cfg.getDouble(path + ".X");
            double y = cfg.getDouble(path + ".Y");
            double z = cfg.getDouble(path + ".Z");
            float yaw = (float)cfg.getDouble(path + ".Yaw");
            float pitch = (float) cfg.getDouble(path + ".Pitch");

            return new Location(world, x, y, z, yaw, pitch);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Location getLocation(String key) {
        return this.locations.get(key);
    }

    public static LocationManager getInstance() {
        return instance;
    }

    public Cuboid getSpawnRegion() {
        return spawnRegion;
    }
}
