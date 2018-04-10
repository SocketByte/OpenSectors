package pl.socketbyte.opensectors.linker.api;

import pl.socketbyte.opensectors.linker.OpenSectorLinker;

import java.util.ArrayList;
import java.util.List;

public class PacketExtender<V> {

    private static List<PacketExtender> packetExtenders = new ArrayList<>();

    public static void add(PacketExtender packetExtender) {
        packetExtenders.add(packetExtender);
    }

    public static List<PacketExtender> getPacketExtenders() {
        return packetExtenders;
    }

    private Class<? extends V> packet;
    private IPacketAdapter<V> packetAdapter;

    public PacketExtender() {

    }

    public PacketExtender(Class<? extends V> packet) {
        this.packet = packet;
    }

    public Class getPacket() {
        return packet;
    }

    public void setPacket(Class<? extends V> packet) {
        this.packet = packet;
    }

    public PacketExtender add() {
        PacketExtender.add(this);
        return this;
    }

    public PacketExtender register() {
        OpenSectorLinker.getClient().getKryo().register(packet);
        return this;
    }

    public IPacketAdapter getPacketAdapter() {
        return packetAdapter;
    }

    public void setPacketAdapter(IPacketAdapter<V> packetAdapter) {
        this.packetAdapter = packetAdapter;
    }
}
