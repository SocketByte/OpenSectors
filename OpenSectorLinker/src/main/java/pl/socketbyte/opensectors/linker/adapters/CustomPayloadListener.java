package pl.socketbyte.opensectors.linker.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.linker.api.ChannelManager;
import pl.socketbyte.opensectors.linker.packet.PacketCustomPayload;

public class CustomPayloadListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketCustomPayload))
            return;

        ChannelManager.execute(connection, (PacketCustomPayload) object);
    }

}
