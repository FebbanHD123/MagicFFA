package de.febanhd.ffa.scoreboard;

import de.febanhd.ffa.FFA;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class IngameScoreboard {

    public static void setScoreboard(Player player) {
        String kitName = "§4Keins";
        if(FFA.getInstance().getKitManager().getKit(player) != null) {
            kitName = FFA.getInstance().getKitManager().getKit(player).getName();
        }
        int kills = FFA.getInstance().getStatsManager().getKills(player), detahs = FFA.getInstance().getStatsManager().getDeaths(player);
        final Scoreboard scoreboard = new Scoreboard();
        final ScoreboardObjective obj = scoreboard.registerObjective("abc", IScoreboardCriteria.b);
        obj.setDisplayName("§5Magic FFA");
        final PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
        final PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        final ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, " ");
        final ScoreboardScore s2 = new ScoreboardScore(scoreboard, obj, "§5Kills");
        final ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, "§7> §c" + kills);
        final ScoreboardScore s5 = new ScoreboardScore(scoreboard, obj, "  ");
        final ScoreboardScore s6 = new ScoreboardScore(scoreboard, obj, "§5Tode");
        final ScoreboardScore s7 = new ScoreboardScore(scoreboard, obj, "§7> §c" + detahs);
        final ScoreboardScore s8 = new ScoreboardScore(scoreboard, obj, "   ");
        final ScoreboardScore s9 = new ScoreboardScore(scoreboard, obj, "§5Kit");
        final ScoreboardScore s10 = new ScoreboardScore(scoreboard, obj, "§7> §r" + kitName);
        final ScoreboardScore s11 = new ScoreboardScore(scoreboard, obj, "    ");
        s1.setScore(10);
        s2.setScore(9);
        s3.setScore(8);
        s5.setScore(7);
        s6.setScore(6);
        s7.setScore(5);
        s8.setScore(4);
        s9.setScore(3);
        s10.setScore(2);
        s11.setScore(1);
        final PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        final PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(s1);
        final PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(s2);
        final PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(s3);
        final PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(s5);
        final PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(s6);
        final PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(s7);
        final PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(s8);
        final PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(s9);
        final PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(s10);
        final PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(s11);
        sendPacket(removePacket, player);
        sendPacket(createpacket, player);
        sendPacket(display, player);
        sendPacket(pa1, player);
        sendPacket(pa2, player);
        sendPacket(pa3, player);
        sendPacket(pa4, player);
        sendPacket(pa5, player);
        sendPacket(pa6, player);
        sendPacket(pa7, player);
        sendPacket(pa8, player);
        sendPacket(pa9, player);
        sendPacket(pa10, player);
    }

    private static void sendPacket(final Packet<?> packet, final Player p) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }


}
