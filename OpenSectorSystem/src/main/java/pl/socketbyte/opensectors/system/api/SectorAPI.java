package pl.socketbyte.opensectors.system.api;

import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.util.NetworkManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
     * Creates an PacketExtender class which contains
     * pre-applied packet class and adds it to the list
     *
     * Beware: It is for existing packet classes like PacketQuery or PacketQueryExecute, not your own packets
     * @param packet Class which will be further sent through proxy and linkers
     * @return PacketExtender class
     */
    public static PacketExtender createExistingPacketExtender(Class packet) {
        return new PacketExtender(packet)
                .add();
    }

    /**
     * @return Linker connection map. Holds server ID as a key and LinkerConnection as value
     */
    public static Map<Integer, LinkerConnection> getLinkerMap() {
        return LinkerStorage.getLinkerConnectionMap();
    }

    /**
     * @return Collection of LinkerConnections
     */
    public static Collection<LinkerConnection> getLinkers() {
        return LinkerStorage.getLinkerConnectionMap().values();
    }

    /**
     * @param id Kryonet connection ID
     * @return LinkerConnection object
     */
    public static LinkerConnection getLinkerByConnectionId(int id) {
        return LinkerStorage.getLinkerByConnectionId(id);
    }

    /**
     * @param serverId Server ID specified in sector's config file
     * @return LinkerConnection object
     */
    public static LinkerConnection getLinker(int serverId) {
        return LinkerStorage.getLinker(serverId);
    }

    /**
     * Registers additional class which you can use to send through proxy and linker
     * @param clazz External class which will be used in one of your Packet classes
     */
    public static void register(Class clazz) {
        OpenSectorSystem.getServer().getKryo().register(clazz);
    }

    /**
     * Sends an TCP packet to specified server ID.
     * @param serverId Server ID specified in sector's config file
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static void sendTCP(int serverId, Object packet) {
        LinkerStorage.getLinker(serverId).sendTCP(packet);
    }

    /**
     * Sends an UDP packet to specified server ID.
     * @param serverId Server ID specified in sector's config file
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static void sendUDP(int serverId, Object packet) {
        LinkerStorage.getLinker(serverId).sendUDP(packet);
    }

    /**
     * Sends a TCP packet to everyone connected (secure)
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static void sendAllTCP(Object packet) {
        NetworkManager.sendAllTCP(packet);
    }

    /**
     * Sends an UDP packet to everyone connected (insecure)
     * Never send important and sensitive information through this method
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static void sendAllUDP(Object packet) {
        NetworkManager.sendAllUDP(packet);
    }

}
