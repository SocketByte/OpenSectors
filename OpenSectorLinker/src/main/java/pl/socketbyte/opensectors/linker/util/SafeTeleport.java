package pl.socketbyte.opensectors.linker.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Stack;

public class SafeTeleport {

    private final Player player;
    private final World world;

    public SafeTeleport(Player player) {
        this.player = player;
        this.world = player.getWorld();
    }

    public void teleport(int x, int z, int originalY, float yaw, float pitch) {
        int y = determineSafeY(x, z);

        if (originalY > y)
            y = originalY;

        Location location = new Location(world, x, y, z, yaw, pitch);
        player.teleport(location);
    }

    private int determineSafeY(int x, int z) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 256; i++) {
            Block block = world.getBlockAt(x, i, z);
            Material type = block.getType();

            if (type == Material.AIR) {
                if (stack.isEmpty())
                    stack.push(i);
            }
            else if (!stack.isEmpty()
                    && (type == Material.LEAVES
                    || type == Material.LEAVES_2))
                stack.pop();
        }
        return stack.pop();
    }
}
