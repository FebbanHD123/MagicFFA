package de.febanhd.ffa.kit.items;

import de.febanhd.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BeefGrenate {

    private Item item;

    public BeefGrenate(Player player) {
        item = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.COOKED_BEEF));
        Vector v = player.getEyeLocation().getDirection();
        v.add(new Vector(0, 0.5, 0));
        item.setVelocity(v);
        item.setPickupDelay(Integer.MAX_VALUE);
        Bukkit.getScheduler().runTaskLater(FFA.getInstance(), () -> {
            Location location = item.getLocation();
            this.item.remove();
            location.getWorld().createExplosion(location, 6);
        }, 22 * 2);
    }
}
