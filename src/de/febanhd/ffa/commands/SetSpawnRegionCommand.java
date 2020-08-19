package de.febanhd.ffa.commands;

import de.febanhd.ffa.FFA;
import de.febanhd.ffa.location.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnRegionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("ffa.setspawnregion")) {
                if(args.length == 1) {
                    if(args[0].equals("1") || args[0].equals("2")) {
                        LocationManager.getInstance().setLocation("Spawn.Region." + args[0], player.getLocation());
                        player.sendMessage(FFA.PREFIX + "§aDu hast die Location " + args[0] + " §avon der Spawnregion gesetzt!");
                    }else {
                        player.sendMessage(FFA.PREFIX + "§7Benutze: §e/setspawnregion [1/2]");
                    }
                }
            }else {
                player.sendMessage(FFA.PREFIX + "§cDazu hast du nicht genügend Rechte!");
            }
        }
        return false;
    }
}
