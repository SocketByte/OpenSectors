package pl.socketbyte.opensectors.system.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.api.LinkerConnection;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.packet.PacketPlayerInfo;
import pl.socketbyte.opensectors.system.packet.PacketPlayerTransfer;
import pl.socketbyte.opensectors.system.util.NetworkManager;
import pl.socketbyte.opensectors.system.util.ServerManager;

import java.util.UUID;

public class PlayerTransferListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketPlayerTransfer))
            return;

        PacketPlayerTransfer packet = (PacketPlayerTransfer)object;
        UUID uniqueId = UUID.fromString(packet.getPlayerUniqueId());
        int id = packet.getServerId();

        PacketPlayerInfo packetPlayerInfo = packet.getPlayerInfo();
        LinkerConnection linkerConnection = LinkerStorage.getLinker(id);

        if (linkerConnection == null) {
            OpenSectorSystem.log().warning("Linker with id " + id + " is not connected or it is not responding!");
            return;
        }

        NetworkManager.sendTCP(linkerConnection.getConnection(), packetPlayerInfo);
        ServerManager.transfer(uniqueId, id);
    }

}
