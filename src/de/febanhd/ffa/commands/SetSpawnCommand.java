package de.febanhd.ffa.commands;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.location.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("ffa.setspawn")) {
                LocationManager.getInstance().setLocation("Spawn.Location", player.getLocation());
                player.sendMessage(FFA.PREFIX + "§aDu hast die Spawnlocation gesetzt.");
            }else {
                player.sendMessage(FFA.PREFIX + "§cDazu hast du nicht genügend Rechte!");
            }
        }
        return false;
    }
}
