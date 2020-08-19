package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

;

public class EndermanKit extends AbstractKit {

    private long lastUsed = 0;
    private EnderPearl currentEnderPearl;

    public EndermanKit(Player player) {
        super(player, "§5Enderman");
    }

    @Override
    public void initItems() {
        this.addItem(0, new ItemBuilder(Material.IRON_SWORD).setUnbreakable(true).setDisplayName("§5Ender-Sword").build());
        this.addItem(1, new ItemBuilder(Material.EYE_OF_ENDER).setDisplayName("§5Enderperlen Kanone").build());

        this.setHelmet(this.getLeatherArmor(Material.LEATHER_HELMET, Color.PURPLE));
        this.setChestplace(this.getLeatherArmor(Material.LEATHER_CHESTPLATE, Color.BLACK));
        this.setLeggings(this.getLeatherArmor(Material.LEATHER_LEGGINGS, Color.BLACK));
        this.setBoots(this.getLeatherArmor(Material.LEATHER_BOOTS, Color.BLACK));
    }

    @Override
    public void onRightClicked() {
        if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.EYE_OF_ENDER) {
            if (this.currentEnderPearl != null && !this.currentEnderPearl.isDead()) {
                player.sendMessage(FFA.PREFIX + "§cDu kannst dies erst wieder tuen, sobald die letzte Enderperle angekommen ist.");
                return;
            }
            this.currentEnderPearl = player.launchProjectile(EnderPearl.class);

            this.lastUsed = System.currentTimeMillis();
        }
    }

    private ItemStack getLeatherArmor(Material material, Color color) {
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
        meta.setColor(color);
        meta.spigot().setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        if(material == Material.LEATHER_BOOTS) {
            meta.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
        }
        stack.setItemMeta(meta);
        return stack;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(event.getEntity().equals(this.player) && this.currentEnderPearl != null) {
            this.currentEnderPearl.remove();
        }
    }
}
