package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TankKit extends AbstractKit {

    public TankKit(Player player) {
        super(player, "§dTank");
    }

    @Override
    public void initItems() {
        this.setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setChestplace(new ItemBuilder(Material.IRON_CHESTPLATE).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());

        this.addItem(0, new ItemBuilder(Material.IRON_SWORD).setDisplayName("§7Excalibur").setUnbreakable(true).build());
    }

    @Override
    public void onEveryTick() {
        for(PotionEffect effect : player.getActivePotionEffects()) {
            if(effect.getType() == PotionEffectType.SLOW) {
                player.removePotionEffect(PotionEffectType.SLOW);
            }
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1, true));
    }

}
