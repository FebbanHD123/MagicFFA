package de.febanhd.ffa.listener;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.gui.impl.KitSelectionGui;
import de.febanhd.ffa.kit.AbstractKit;
import de.febanhd.ffa.location.LocationManager;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class KitListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        for(AbstractKit kit : FFA.getInstance().getKitManager().getKits()) {
            if(kit.getPlayer().equals(player)) {
                if(!LocationManager.getInstance().getSpawnRegion().containsLocation(kit.getPlayer().getLocation())) {
                    kit.onRightClicked();
                }
                break;
            }
        }

        if(player.getItemInHand() != null && player.getItemInHand().getType() == Material.CHEST && player.getItemInHand().getItemMeta().getDisplayName().equals("§cKits")) {
            new KitSelectionGui(FFA.getInstance()).open(player);
        }
    }

    @EventHandler
    public void onProjectilHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof WitherSkull) {
            event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), 2, false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;
        if(event.getDamager() instanceof Snowball) {
            if(event.getEntity() instanceof Player) {
                ((Player)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1, true));
                ((Player)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 1, true));
                event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), Effect.SMOKE, 20);
            }
        }
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            if(event.getCaught() instanceof Player) {
                Player target = (Player)event.getCaught();

                Location lc = target.getLocation();
                Location to = player.getLocation();
                lc.setY(lc.getY() + 0.5D);
                target.teleport(lc);
                double g = -0.08D;
                double d = to.distance(lc);
                double v_x = (1.0D + 0.07D * d) * (to.getX() - lc.getX()) / d;
                double v_y = (1.0D + 0.03D * d) * (to.getY() - lc.getY()) / d - 0.5D * g * d;
                double v_z = (1.0D + 0.07D * d) * (to.getZ() - lc.getZ()) / d;
                Vector v = player.getVelocity();
                v.setX(v_x);
                v.setY(v_y);
                v.setZ(v_z);
                target.setVelocity(v);
            }
        }else if(event.getState().equals(PlayerFishEvent.State.IN_GROUND) || event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)) {
            Location lc = player.getLocation();
            Location to = event.getHook().getLocation();
            lc.setY(lc.getY() + 0.5D);
            player.teleport(lc);
            double g = -0.08D;
            double d = to.distance(lc);
            double v_x = (1.0D + 0.07D * d) * (to.getX() - lc.getX()) / d;
            double v_y = (1.0D + 0.03D * d) * (to.getY() - lc.getY()) / d - 0.5D * g * d;
            double v_z = (1.0D + 0.07D * d) * (to.getZ() - lc.getZ()) / d;
            Vector v = player.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            player.setVelocity(v);

        }
    }
}
