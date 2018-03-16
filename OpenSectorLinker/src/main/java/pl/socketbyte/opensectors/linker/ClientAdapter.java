package pl.socketbyte.opensectors.linker;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.linker.api.PacketExtender;
import pl.socketbyte.opensectors.linker.api.callback.CallbackManager;
import pl.socketbyte.opensectors.linker.packet.Packet;

public class ClientAdapter extends Listener {

    private static final CallbackManager callbackCatcher = new CallbackManager();

    public static CallbackManager getCallbackCatcher() {
        return callbackCatcher;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Packet)
            callbackCatcher.complete((Packet)object);

        for (PacketExtender packetExtender : PacketExtender.getPacketExtenders())
            if (object.getClass().isAssignableFrom(packetExtender.getPacket())) {
                if (packetExtender.getPacketAdapter() == null)
                    continue;

                packetExtender.getPacketAdapter().received(connection, object);
            }

        super.received(connection, object);
    }
}
