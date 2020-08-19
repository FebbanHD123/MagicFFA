package de.febanhd.ffa.listener;

import com.sun.org.apache.xml.internal.security.keys.content.SPKIData;
import de.febanhd.ffa.FFA;
import de.febanhd.ffa.gui.impl.KitSelectionGui;
import de.febanhd.ffa.location.LocationManager;
import de.febanhd.ffa.scoreboard.IngameScoreboard;
import de.febanhd.ffa.stats.StatsManager;
import de.febanhd.ffa.util.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameProtectionListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(LocationManager.getInstance().getSpawnRegion().containsLocation(player.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player)event.getEntity();
            if(LocationManager.getInstance().getSpawnRegion().containsLocation(player.getLocation())) {
                event.setCancelled(true);
                event.getDamager().sendMessage(FFA.PREFIX + "§cDieser Spieler befindet sich in der Schutz-Zone.");
            }else {
                GameUtil.players.put(player, (Player)event.getDamager());
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE && event.getClickedInventory() != null && event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player killer = GameUtil.players.get(event.getEntity());
        Player player = event.getEntity();
        StatsManager statsManager = FFA.getInstance().getStatsManager();
        statsManager.setDeaths(player,statsManager.getDeaths(player) + 1);
        if(killer != null) {
            event.setDeathMessage(FFA.PREFIX + "§c" + player.getDisplayName() + " §7wurde von §c" + killer.getDisplayName() + " §7getötet.");
            GameUtil.players.remove(player);
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 2, 1);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 6, 3, true));
            FFA.getInstance().getKitManager().getKits().forEach(kit -> {
                if(kit.getPlayer().equals(killer)) {
                    kit.onKill();
                }
            });
            statsManager.setKills(killer,statsManager.getKills(killer) + 1);
            IngameScoreboard.setScoreboard(killer);
        }else {
            event.setDeathMessage(FFA.PREFIX + "§c" + player.getDisplayName() + " §7ist gestorben.");
        }
        Bukkit.getScheduler().runTaskLater(FFA.getInstance(), () -> {
            event.getEntity().spigot().respawn();
            event.getEntity().teleport(LocationManager.getInstance().getLocation("spawn"));
            FFA.getInstance().getKitManager().setItems(event.getEntity(), FFA.getInstance().getKitManager().getPlayerKits().get(event.getEntity()));
            IngameScoreboard.setScoreboard(player);
        }, 3);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

}
