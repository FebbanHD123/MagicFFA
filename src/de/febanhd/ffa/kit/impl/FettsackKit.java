package de.febanhd.ffa.kit.impl;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.kit.items.BeefGrenate;
import de.febanhd.ffa.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FettsackKit extends AbstractKit {

    private long lastUsed = 0;

    public FettsackKit(Player player) {
        super(player, "§e§lFettsack");
    }

    @Override
    public void initItems() {
        this.setChestplace(new ItemBuilder(Material.IRON_CHESTPLATE).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());

        this.addItem(1, new ItemBuilder(Material.COOKED_BEEF, 1).setDisplayName("§6Beef Granate").build());
        this.addItem(0, new ItemBuilder(Material.RAW_FISH).setDisplayName("§bFishi").setUnbreakable(true).addEnchant(Enchantment.DAMAGE_ALL, 5).build());
    }

    @Override
    public void onRightClicked() {
        if(player.getItemInHand() != null && player.getItemInHand().getType() == Material.COOKED_BEEF) {
            if(lastUsed + 7500 > System.currentTimeMillis()) {
                player.sendMessage(FFA.PREFIX + "§cBitte warte noch einen moment!");
                return;
            }
            this.lastUsed = System.currentTimeMillis();
            new BeefGrenate(player);
        }
    }
}
