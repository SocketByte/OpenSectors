package pl.socketbyte.opensectors.system.util;

import com.esotericsoftware.kryonet.Connection;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.SecurityModule;
import pl.socketbyte.opensectors.system.logging.ByteInformator;
import pl.socketbyte.opensectors.system.logging.ByteUtil;

public class NetworkManager {

    public static void sendTCP(Connection connection, Object packet) {
        if (!SecurityModule.isAuthorized(connection, packet))
            return;

        connection.sendTCP(packet);

        ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet));
    }

    public static void sendUDP(Connection connection, Object packet) {
        connection.sendUDP(packet);

        ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet));
    }

    public static void sendAllTCP(Object packet) {
        Connection[] connections = OpenSectorSystem.getServer().getConnections();

        int amount = 0;
        for (Connection connection : connections) {
            if (!SecurityModule.isAuthorized(connection, packet))
                continue;

            connection.sendTCP(packet);
            amount++;
        }

        ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet) * amount);
    }

    public static void sendAllUDP(Object packet) {
        OpenSectorSystem.getServer().sendToAllUDP(packet);

        ByteInformator.incrementBytesSent(ByteUtil.sizeof(packet)
                * OpenSectorSystem.getServer().getConnections().length);
    }
}
