package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.packet.serializable.SerializableItem;
import pl.socketbyte.opensectors.linker.packet.types.Receiver;

public class PacketItemTransfer extends Packet {

    private Receiver receiver;
    private String playerUniqueId;
    private SerializableItem itemStack;

    public PacketItemTransfer() {

    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public void setReceiver(Receiver receiver, String uniqueId) {
        this.receiver = receiver;
        this.playerUniqueId = uniqueId;
    }

    public String getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(String playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    public SerializableItem getItemStack() {
        return itemStack;
    }

    public void setItemStack(SerializableItem itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String toString() {
        return "PacketItemTransfer{" +
                "receiver=" + receiver +
                ", playerUniqueId='" + playerUniqueId + '\'' +
                ", itemStack=" + itemStack +
                '}';
    }
}
