package pl.socketbyte.opensectors.system.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.Database;
import pl.socketbyte.opensectors.system.packet.PacketUpdatePlayerSession;

import java.util.UUID;

public class PlayerSessionListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketUpdatePlayerSession))
            return;

        PacketUpdatePlayerSession packet = (PacketUpdatePlayerSession)object;

        Database.setPlayerSession(UUID.fromString(packet.getPlayerUniqueId()), packet.getServerId());
    }

}
