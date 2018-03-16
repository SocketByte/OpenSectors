package pl.socketbyte.opensectors.system.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.AuthorizedConnectionData;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.api.LinkerConnection;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.packet.PacketConfigurationInfo;
import pl.socketbyte.opensectors.system.packet.PacketLinkerAuthRequest;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class LinkerAuthListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketLinkerAuthRequest))
            return;

        PacketLinkerAuthRequest packet = (PacketLinkerAuthRequest)object;
        String password = packet.getPassword();
        if (!password.equals(OpenSectorSystem.getPassword()))
            connection.close();
        else {
            AuthorizedConnectionData.getAuthorizedConnectionMap()
                    .put(connection.getID(), connection);

            LinkerConnection linkerConnection = new LinkerConnection(packet.getServerId(), connection.getID());
            linkerConnection.setConnection(connection);
            LinkerStorage.getLinkerConnectionMap().put(linkerConnection.getServerId(), linkerConnection);

            PacketConfigurationInfo packetConfigurationInfo = new PacketConfigurationInfo();
            packetConfigurationInfo.setJsonConfig(OpenSectorSystem.getConfig());

            NetworkManager.sendTCP(connection, packetConfigurationInfo);

            OpenSectorSystem.log().info("New linker connection estabilished with " + connection.getRemoteAddressTCP() +
                    " (ID #" + connection.getID() + ", server id: " + linkerConnection.getServerId() + ")");
        }
    }
}
