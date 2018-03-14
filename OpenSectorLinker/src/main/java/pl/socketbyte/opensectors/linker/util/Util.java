package pl.socketbyte.opensectors.linker.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;

public class Util {

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static boolean isLocationValid(Location loc) {
        Material mat = loc.getBlock().getType();
        return (mat == Material.GRASS) || (mat == Material.SAND) || (mat == Material.DIRT)
                || (mat == Material.GRAVEL) || (mat == Material.STONE) || (mat == Material.WATER);
    }

    public static Location getValidLocation(Location loc, int y) {
        for (int i = 0; i < 256; i++) {
            loc.setY(loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()));
            if (isLocationValid(new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ()))) {
                loc.add(0, 3, 0);
                if (y > (loc.getBlockY() + 2))
                    loc.setY(y);

                return loc;
            }
        }
        return loc;
    }

    public static int[] getDestinationWithOffset(ServerController current, ServerController next, int x, int z) {
        int distWest = Math.abs((next.x + 1) - current.x);
        int distEast = Math.abs((next.x - 1) - current.x);
        int distNorth = Math.abs((next.z - 1) - current.z);
        int distSouth = Math.abs((next.z + 1) - current.z);
        int dirX = (distWest < distEast) ? -8 : 8;
        int dirZ = (distNorth < distSouth) ? 8 : -8;
        int distX = (distWest < distEast) ? distWest : distEast;
        int distZ = (distNorth < distSouth) ? distNorth : distSouth;
        return distX < distZ ? new int[] { x, z + dirZ } : new int[] { x + dirX, z };
    }

}
