package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.location.Cuboid;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FiremanKit extends AbstractKit {

    private long lastUsed = 0;

    private int taskID, current;

    public FiremanKit(Player player) {
        super(player, "§6Fireman");
    }

    @Override
    public void initItems() {
        this.addItem(0, new ItemBuilder(Material.GOLD_SWORD).setUnbreakable(true).setDisplayName("§eSword").build());
        this.addItem(1, new ItemBuilder(Material.BLAZE_POWDER).setDisplayName("§eFire Attack").build());

        this.setBoots(new ItemBuilder(Material.GOLD_BOOTS).addEnchant(Enchantment.PROTECTION_FIRE, 4).setUnbreakable(true).build());
        this.setLeggings(new ItemBuilder(Material.GOLD_LEGGINGS).addEnchant(Enchantment.PROTECTION_FIRE, 4).setUnbreakable(true).build());
        this.setChestplace(new ItemBuilder(Material.GOLD_CHESTPLATE).addEnchant(Enchantment.PROTECTION_FIRE, 4).setUnbreakable(true).build());
        this.setHelmet(new ItemBuilder(Material.GOLD_HELMET).addEnchant(Enchantment.PROTECTION_FIRE, 4).setUnbreakable(true).build());
    }

    @Override
    public void onEveryTick() {
        for(PotionEffect effect : player.getActivePotionEffects()) {
            if(effect.getType() == PotionEffectType.SPEED) {
                player.removePotionEffect(PotionEffectType.SPEED);
            }
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, true));
    }

    @Override
    public void onRightClicked() {
        if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.BLAZE_POWDER) {
            if (this.lastUsed + 7500 >= System.currentTimeMillis()) {
                player.sendMessage(FFA.PREFIX + "§cBitte warte noch einen Moment!");
                return;
            }

            current = 0;
            Cuboid cuboid = new Cuboid(player.getLocation().add(6, 1, 6), player.getLocation().add(-6, 0, -6));
            this.lastUsed = System.currentTimeMillis();

            for (Block block : cuboid.getBlocks()) {
                block.getLocation().getWorld().spigot().playEffect(block.getLocation(), Effect.LAVA_POP, 0, 1, 0.0f, 255.0f, 0.0f, 1.0f, 0, 30);
            }

            for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 5, 2, 5)) {
                if (!(entity.equals(player))) {
                    if (entity.getFireTicks() <= 0)
                        entity.setFireTicks(20 * 30);
                }
            }
        }
    }
}
