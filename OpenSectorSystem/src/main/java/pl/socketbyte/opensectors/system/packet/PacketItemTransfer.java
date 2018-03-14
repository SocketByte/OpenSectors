package pl.socketbyte.opensectors.system.packet;

import org.bukkit.inventory.ItemStack;
import pl.socketbyte.opensectors.system.packet.serializable.Receiver;

public class PacketItemTransfer extends Packet {

    private Receiver receiver;
    private String playerUniqueId;
    private ItemStack itemStack;

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

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
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
