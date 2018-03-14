package pl.socketbyte.opensectors.system.packet;

import net.md_5.bungee.api.ChatMessageType;
import pl.socketbyte.opensectors.system.packet.serializable.Receiver;

public class PacketSendMessage extends Packet {

    private Receiver receiver;
    private String playerUniqueId;
    private ChatMessageType messageType;
    private String message;

    public PacketSendMessage() {

    }

    public void setReceiver(Receiver receiver, String uniqueId) {
        this.receiver = receiver;
        this.playerUniqueId = uniqueId;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public String getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(String uniqueId) {
        this.playerUniqueId = uniqueId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(ChatMessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public ChatMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ChatMessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "PacketSendMessage{" +
                "receiver=" + receiver +
                ", playerUniqueId='" + playerUniqueId + '\'' +
                ", messageType=" + messageType +
                ", message='" + message + '\'' +
                '}';
    }
}
