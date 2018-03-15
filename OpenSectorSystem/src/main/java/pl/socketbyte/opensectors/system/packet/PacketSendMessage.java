package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.system.packet.Packet;
import pl.socketbyte.opensectors.system.packet.types.MessageType;
import pl.socketbyte.opensectors.system.packet.types.Receiver;

public class PacketSendMessage extends Packet {

    private Receiver receiver;
    private String playerUniqueId;
    private MessageType messageType;
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

    public void setMessage(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
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
