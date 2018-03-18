package pl.socketbyte.opensectors.linker.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerInfo;
import pl.socketbyte.opensectors.linker.util.PlayerInfoHolder;

import java.util.UUID;

public class PlayerInfoListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketPlayerInfo))
            return;

        PacketPlayerInfo packet = (PacketPlayerInfo)object;
        System.out.println("Received player info for " + packet.getPlayerUniqueId());

        PlayerInfoHolder.getPlayerInfos().put(UUID.fromString(packet.getPlayerUniqueId()), packet);
    }
}
