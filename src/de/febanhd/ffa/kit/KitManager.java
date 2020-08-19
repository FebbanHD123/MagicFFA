package de.febanhd.ffa.kit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.febanhd.ffa.FFA;
import de.febanhd.ffa.kit.impl.WitherKit;
import de.febanhd.ffa.scoreboard.IngameScoreboard;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;

public class KitManager {

    private HashMap<Player, AbstractKit> playerKits = Maps.newHashMap();

    public KitManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(FFA.getInstance(), () -> {
            this.getKits().forEach(kit -> {
                kit.onEveryTick();
            });
        }, 1, 1);
    }

    public void setKit(Player player, AbstractKit kit) {
        this.playerKits.put(player, kit);

        this.setItems(player, kit);

        player.sendMessage(FFA.PREFIX + "§7Dein Kit wurde zu §r" + kit.getName() + " §7gewechselt.");

        IngameScoreboard.setScoreboard(player);
    }

    public void setItems(Player player, AbstractKit kit) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setBoots(kit.getBoots());
        inventory.setLeggings(kit.getLeggings());
        inventory.setChestplate(kit.getChestplace());
        inventory.setHelmet(kit.getHelmet());

        for(int slot : kit.getInventoryItems().keySet()) {
            inventory.setItem(slot, kit.getInventoryItems().get(slot));
        }
        player.getInventory().setItem(8, new ItemBuilder(Material.CHEST).setDisplayName("§cKits").build());
    }

    public ArrayList<AbstractKit> getKits() {
        return Lists.newArrayList(this.playerKits.values());
    }

    public HashMap<Player, AbstractKit> getPlayerKits() {
        return playerKits;
    }

    public AbstractKit getKit(Player player) {
        return this.playerKits.get(player);
    }
}
