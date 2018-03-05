package pl.socketbyte.opensectors.system;

import com.esotericsoftware.kryonet.Connection;
import pl.socketbyte.opensectors.system.packet.Packet;
import pl.socketbyte.opensectors.system.packet.PacketLinkerAuthRequest;

public class SecurityModule {

    public static boolean isAuthorized(Connection connection, Object object) {
        if ((!(object instanceof PacketLinkerAuthRequest))
                && !isAuthorized(connection)
                && (object instanceof Packet)) {
            connection.close();
            OpenSectorSystem.log().info("Linker (ID #" + connection.getID() + ") was suspicious. ("
                    + connection.getRemoteAddressTCP() + "). It was not authorized properly.");
            return false;
        }
        return true;
    }

    public static boolean isAuthorized(Connection connection) {
        return AuthorizedConnectionData.getAuthorizedConnectionMap().containsKey(connection.getID());
    }

}
