package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FishermanKit extends AbstractKit {

    public FishermanKit(Player player) {
        super(player, "§9Fisherman");
    }

    @Override
    public void initItems() {
        this.setHelmet(this.getLeatherArmor(Material.LEATHER_HELMET, Color.BLUE));
        this.setChestplace(this.getLeatherArmor(Material.LEATHER_CHESTPLATE, Color.BLUE));
        this.setLeggings(this.getLeatherArmor(Material.LEATHER_LEGGINGS, Color.BLUE));
        this.setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setUnbreakable(true).build());

        this.addItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setUnbreakable(true).build());
        this.addItem(1, new ItemBuilder(Material.FISHING_ROD).setDisplayName("§9Hapune").setUnbreakable(true).build());
    }

    private ItemStack getLeatherArmor(Material material, Color color) {
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
        meta.setColor(color);
        meta.spigot().setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        stack.setItemMeta(meta);
        return stack;
    }
}
