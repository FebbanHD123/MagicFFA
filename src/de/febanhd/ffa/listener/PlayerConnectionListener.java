package de.febanhd.ffa.listener;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.kit.impl.WitherKit;
import de.febanhd.ffa.location.LocationManager;
import de.febanhd.ffa.scoreboard.IngameScoreboard;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(FFA.PREFIX + "§a" + player.getDisplayName() + " §7ist dem Spiel §abeigetreten§7.");

        player.setFoodLevel(40);
        player.resetMaxHealth();
        player.setHealth(player.getMaxHealth());

        Location spawnLocation = LocationManager.getInstance().getLocation("spawn");
        if (spawnLocation != null) {
            player.teleport(spawnLocation);
            if(!FFA.getInstance().getStatsManager().hasStats(player)) {
                FFA.getInstance().getStatsManager().setDeaths(player, 0);
                FFA.getInstance().getStatsManager().setKills(player, 0);
            }
            Bukkit.getScheduler().runTaskLater(FFA.getInstance(), () -> {
                if (spawnLocation != null) {
                    player.teleport(spawnLocation);
                    player.getInventory().clear();
                    player.setGameMode(GameMode.SURVIVAL);
                    player.getInventory().setItem(0, new ItemBuilder(Material.CHEST).setDisplayName("§cKits").build());
                    player.getInventory().setHeldItemSlot(0);
                    IngameScoreboard.setScoreboard(player);
                }
            }, 3);
        } else if (player.isOp()) {
            player.sendMessage(FFA.PREFIX + "§cDu musst die SpawnLocation setzen! (/setspawn)");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(FFA.PREFIX + "§c" + player.getDisplayName() + " §7hat das Spiel §cverlassen§7.");
    }
}
