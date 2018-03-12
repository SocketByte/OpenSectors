package pl.socketbyte.opensectors.linker.api;

import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.packet.PacketQuery;
import pl.socketbyte.opensectors.linker.packet.PacketQueryExecute;
import pl.socketbyte.opensectors.linker.util.NetworkManager;

import java.util.List;

/**
 * This API is targeted towards beginner users unfamiliar with any technology presented here.
 * It limits Kryo or Kryonet usage as much as possible to make simple, fun coding experience for new programmers.
 */
public class SectorAPI {

    /**
     * Registers new payload channel behaviour using packet adapter interface
     * @param channel Channel name
     * @param packetAdapter Packet adapter (event handler)
     */
    public static void registerPayloadChannel(String channel, IPacketAdapter packetAdapter) {
        ChannelManager.getChannels().put(channel, packetAdapter);
    }

    /**
     * @return All PacketExtenders currently working (if registered)
     */
    public static List<PacketExtender> getPacketExtenders() {
        return PacketExtender.getPacketExtenders();
    }

    /**
     * Creates an PacketExtender class which contains
     * pre-applied packet class and automatically registers it to Kryo serializer, and adds it to the list
     * @param packet Class which will be further sent through proxy and linkers
     * @return PacketExtender class
     */
    public static PacketExtender createPacketExtender(Class packet) {
        return new PacketExtender(packet)
                .register()
                .add();
    }

    /**
     * Sends a query
     * @param query Query packet
     */
    public static void sendQuery(PacketQuery query) {
        sendTCP(query);
    }

    /**
     * Sends a query with a callback
     * @param query Query packet
     * @param callback Callback interface
     */
    public static void sendQuery(PacketQueryExecute query, Callback callback) {
        sendTCP(query);

        CallbackManager.getCallbackMap().put(query.getUniqueId(), callback);
    }

    /**
     * Registers additional class which you can use to send through proxy and linker
     * @param clazz External class which will be used in one of your Packet classes
     */
    public static void register(Class clazz) {
        OpenSectorLinker.getClient().getKryo().register(clazz);
    }

    /**
     * Sends an TCP packet to proxy
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static void sendTCP(Object packet) {
        NetworkManager.sendTCP(packet);
    }

    /**
     * Sends an UDP packet to proxy
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static void sendUDP(Object packet) {
        NetworkManager.sendUDP(packet);
    }

}
