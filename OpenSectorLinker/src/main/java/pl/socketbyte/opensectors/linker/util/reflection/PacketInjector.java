package pl.socketbyte.opensectors.linker.util.reflection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class PacketInjector {

    public static final Map<UUID, Object> connections = new WeakHashMap<>();
    private static final Class<?> packetClass = Reflection.getCraftClass("Packet");

    public static void sendPacket(Player player, Object packet) {
        try {
            Object playerConnection = connections.get(player.getUniqueId());
            if (playerConnection == null)
                return;

            playerConnection.getClass().getMethod("sendPacket",
                    packetClass).invoke(playerConnection, packet);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(Player player, Object... packets) {
        for (Object packet : packets) {
            sendPacket(player, packet);
        }
    }

    public static void sendPacket(Object packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, packet);
        }
    }

    public static void sendPacket(Object... packets) {
        for (Object packet : packets) {
            sendPacket(packet);
        }
    }

}
