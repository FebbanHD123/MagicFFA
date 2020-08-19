package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.util.ItemBuilder;;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

public class WitherKit extends AbstractKit {

    private long lastUsed = 0;
    private WitherSkull currentWitherSkull;

    public WitherKit(Player player) {
        super(player, "§5Wither");
    }

    @Override
    public void initItems() {
        this.addItem(0, new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).setDisplayName("§5Wither-Sword").build());
        this.addItem(1, new ItemBuilder(Material.STICK).setDisplayName("§5Wither Kanone").build());

        this.setHelmet(this.getLeatherArmor(Material.LEATHER_HELMET, Color.PURPLE));
        this.setChestplace(this.getLeatherArmor(Material.LEATHER_CHESTPLATE, Color.PURPLE));
        this.setLeggings(this.getLeatherArmor(Material.LEATHER_LEGGINGS, Color.PURPLE));
        this.setBoots(this.getLeatherArmor(Material.LEATHER_BOOTS, Color.PURPLE));
    }

    @Override
    public void onRightClicked() {
        if(player.getItemInHand() != null && player.getItemInHand().getType() == Material.STICK) {
            if(this.currentWitherSkull != null && this.lastUsed + 5000 >= System.currentTimeMillis()) {
                player.sendMessage(FFA.PREFIX + "§cBitte warte noch einen Moment!");
                return;
            }
            WitherSkull ws = player.launchProjectile(WitherSkull.class, player.getEyeLocation().getDirection());
            ws.setIsIncendiary(false);
            ws.setYield(10);

            this.lastUsed = System.currentTimeMillis();
            this.currentWitherSkull = ws;
        }
    }

    @Override
    public void onEveryTick() {
        if(this.currentWitherSkull != null && this.lastUsed + 5500 <= System.currentTimeMillis()) {
            if(!currentWitherSkull.isDead()) {
                currentWitherSkull.remove();
                this.currentWitherSkull = null;
            }
        }
    }

    private ItemStack getLeatherArmor(Material material, Color color) {
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
        meta.setColor(color);
        meta.spigot().setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
        stack.setItemMeta(meta);
        return stack;
    }
}
