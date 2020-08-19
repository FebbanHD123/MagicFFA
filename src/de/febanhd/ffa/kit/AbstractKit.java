package de.febanhd.ffa.kit;

import com.google.common.collect.Maps;
import de.febanhd.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class AbstractKit implements Listener {

    private String name;
    private ItemStack boots, leggings, chestplace, helmet;

    protected Player player;

    private HashMap<Integer, ItemStack> inventoryItems;

    public AbstractKit(Player player, String name) {
        this.inventoryItems = Maps.newHashMap();
        this.name = name;
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this, FFA.getInstance());
        this.initItems();
    }

    public abstract void initItems();

    public void onRightClicked(){}
    public void onEveryTick(){}
    public void onKill(){}

    public void addItem(int slot, ItemStack stack) {
        this.inventoryItems.put(slot, stack);
    }

    public HashMap<Integer, ItemStack> getInventoryItems() {
        return inventoryItems;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getChestplace() {
        return chestplace;
    }

    public void setChestplace(ItemStack chestplace) {
        this.chestplace = chestplace;
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }
}
