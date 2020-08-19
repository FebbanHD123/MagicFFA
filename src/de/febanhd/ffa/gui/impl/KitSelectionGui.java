package de.febanhd.ffa.gui.impl;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.gui.SimpleGui;
import de.febanhd.ffa.kit.KitManager;
import de.febanhd.ffa.kit.impl.*;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class KitSelectionGui extends SimpleGui {

    public KitSelectionGui(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void initGui() {
        this.setTitle("§cKits");
        this.setGuiRows(3);
        this.addItem(0, new ItemBuilder(Material.SKULL_ITEM, 1, (short)1).setDisplayName("§5Wither-Kit").build(), 1);
        this.addItem(1, new ItemBuilder(Material.BLAZE_POWDER).setDisplayName("§eFireman-Kit").build(), 2);
        this.addItem(2, new ItemBuilder(Material.SNOW_BALL).setDisplayName("§fSnwoman-Kit").build(), 3);
        this.addItem(3, new ItemBuilder(Material.EYE_OF_ENDER).setDisplayName("§5Enderman-Kit").build(), 4);
        this.addItem(4, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayName("§dTank-Kit").build(), 5);
        this.addItem(5, new ItemBuilder(Material.FISHING_ROD).setDisplayName("§9Angler-Kit").build(), 6);
        this.addItem(6, new ItemBuilder(Material.COOKED_BEEF).setDisplayName("§e§lFettsack-Kit").build(), 7);
    }

    @Override
    public void onClick(int executeID, InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        KitManager kitManager = FFA.getInstance().getKitManager();
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 1);
        if(executeID == 1) {
            kitManager.setKit(player, new WitherKit(player));
        }
        if(executeID == 2) {
            kitManager.setKit(player, new FiremanKit(player));
        }
        if(executeID == 3) {
            kitManager.setKit(player, new SnowmanKit(player));
        }
        if(executeID == 4) {
            kitManager.setKit(player, new EndermanKit(player));
        }
        if(executeID == 5) {
            kitManager.setKit(player, new TankKit(player));
        }
        if(executeID == 6) {
            kitManager.setKit(player, new FishermanKit(player));
        }
        if(executeID == 7) {
            kitManager.setKit(player, new FettsackKit(player));
        }
    }
}
