package pl.socketbyte.opensectors.linker.api;

import pl.socketbyte.opensectors.linker.OpenSectorLinker;

import java.util.ArrayList;
import java.util.List;

public class PacketExtender {

    private static List<PacketExtender> packetExtenders = new ArrayList<>();

    public static void add(PacketExtender packetExtender) {
        packetExtenders.add(packetExtender);
    }

    public static List<PacketExtender> getPacketExtenders() {
        return packetExtenders;
    }

    private Class packet;
    private IPacketAdapter packetAdapter;

    public PacketExtender() {

    }

    public PacketExtender(Class packet) {
        this.packet = packet;
    }

    public Class getPacket() {
        return packet;
    }

    public void setPacket(Class packet) {
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

    public void setPacketAdapter(IPacketAdapter packetAdapter) {
        this.packetAdapter = packetAdapter;
    }
}
