package pl.socketbyte.opensectors.linker.api;

import com.esotericsoftware.kryonet.Connection;
import pl.socketbyte.opensectors.linker.packet.PacketCustomPayload;

import java.util.HashMap;
import java.util.Map;

public class ChannelManager {

    private static final Map<String, IPacketAdapter<PacketCustomPayload>> channels = new HashMap<>();

    public static Map<String, IPacketAdapter<PacketCustomPayload>> getChannels() {
        return channels;
    }

    public static void execute(Connection connection, PacketCustomPayload packet) {
        for (Map.Entry<String, IPacketAdapter<PacketCustomPayload>> channel : channels.entrySet())
            if (channel.getKey().equals(packet.getChannel()))
                channel.getValue().received(connection, packet);
    }
}
