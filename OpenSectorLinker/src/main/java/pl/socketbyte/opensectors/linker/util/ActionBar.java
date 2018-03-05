package pl.socketbyte.opensectors.linker.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.socketbyte.opensectors.linker.Linker;

public class ActionBar {

    // Totally forgot about these
    // Reflection version coming soon xD sorry xD 

    public static void send(Player player, String text) {
        PacketPlayOutChat packet = new PacketPlayOutChat(
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', text) + "\"}"), (byte) 2);

        ((CraftPlayer) player).getHandle()
                .playerConnection.sendPacket(packet);
    }

    public static void send(Player player, String text, int seconds) {
        new BukkitRunnable() {
            @Override
            public void run() {
                send(player, text);
            }
        }.runTaskTimer(Linker.getInstance(), 20, seconds * 20);
    }

}
