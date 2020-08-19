package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SnowmanKit extends AbstractKit {

    public SnowmanKit(Player player) {
        super(player, "§fSchneemann");
    }

    @Override
    public void initItems() {
        this.setHelmet(this.getLeatherArmor(Material.LEATHER_HELMET, Color.WHITE));
        this.setChestplace(this.getLeatherArmor(Material.LEATHER_CHESTPLATE, Color.ORANGE));
        this.setLeggings(this.getLeatherArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
        this.setBoots(this.getLeatherArmor(Material.LEATHER_BOOTS, Color.WHITE));

        this.addItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setUnbreakable(true).setDisplayName("§fSnow Sword").build());

        this.addItem(1, new ItemBuilder(Material.SNOW_BALL, 4).setDisplayName("§aSuper Duper Snowball").build());
    }

    @Override
    public void onKill() {
        player.getInventory().setItem(1, new ItemBuilder(Material.SNOW_BALL, 4).setDisplayName("§aSuper Duper Snowball").build());
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
