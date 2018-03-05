package pl.socketbyte.opensectors.system.util;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Util {

    public static boolean isOffline(UUID uniqueId) {
        return BungeeCord.getInstance().getPlayer(uniqueId) == null
                && BungeeCord.getInstance().getPlayerByOfflineUUID(uniqueId) != null
                || BungeeCord.getInstance().getPlayer(uniqueId) == null
                || BungeeCord.getInstance().getPlayerByOfflineUUID(uniqueId) != null;
    }

    public static ProxiedPlayer getPlayer(UUID uniqueId) {
        if (isOffline(uniqueId))
            return BungeeCord.getInstance().getPlayerByOfflineUUID(uniqueId);
        else return BungeeCord.getInstance().getPlayer(uniqueId);
    }

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
