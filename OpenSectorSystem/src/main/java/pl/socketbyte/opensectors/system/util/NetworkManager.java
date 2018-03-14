package pl.socketbyte.opensectors.system.util;

import com.esotericsoftware.kryonet.Connection;
import net.md_5.bungee.api.ProxyServer;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.SecurityModule;
import pl.socketbyte.opensectors.system.logging.ByteInformator;
import pl.socketbyte.opensectors.system.logging.ByteUtil;

public class NetworkManager {

    public static void sendTCP(Connection connection, Object packet) {
        if (!SecurityModule.isAuthorized(connection, packet))
            return;

        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
            connection.sendTCP(packet);

            ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet));
        });
    }

    public static void sendUDP(Connection connection, Object packet) {
        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
            connection.sendUDP(packet);

            ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet));
        });
    }

    public static void sendAllTCP(Object packet) {
        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
            Connection[] connections = OpenSectorSystem.getServer().getConnections();

            int amount = 0;
            for (Connection connection : connections) {
                if (!SecurityModule.isAuthorized(connection, packet))
                    continue;

                connection.sendTCP(packet);
                amount++;
            }

            ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet) * amount);
        });
    }

    public static void sendAllUDP(Object packet) {
        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
            OpenSectorSystem.getServer().sendToAllUDP(packet);

            ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet)
                    * OpenSectorSystem.getServer().getConnections().length);
        });
    }
}
