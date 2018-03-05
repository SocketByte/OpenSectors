package pl.socketbyte.opensectors.system.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Util {

    /*
    public static boolean isOffline(UUID uniqueId) {
        return ProxyServer.getInstance().getPlayer(uniqueId) == null
                && ProxyServer.getInstance().getPlayerByOfflineUUID(uniqueId) != null
                || ProxyServer.getInstance().getPlayer(uniqueId) == null
                || ProxyServer.getInstance().get(uniqueId) != null;
    }

    public static ProxiedPlayer getPlayer(UUID uniqueId) {
        if (isOffline(uniqueId))
            return ProxyServer.getInstance().getPlayerByOfflineUUID(uniqueId);
        else return ProxyServer.getInstance().getPlayer(uniqueId);
    }
    */

    // Should work?
    public static ProxiedPlayer getPlayer(UUID uniqueId) {
        return ProxyServer.getInstance().getPlayer(uniqueId);
    }

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
