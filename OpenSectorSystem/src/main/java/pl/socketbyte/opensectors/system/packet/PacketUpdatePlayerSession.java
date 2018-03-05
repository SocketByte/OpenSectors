package pl.socketbyte.opensectors.system.packet;

public class PacketUpdatePlayerSession extends Packet {

    private String playerUniqueId;
    private int serverId;

    public PacketUpdatePlayerSession() {

    }

    public String getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(String playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "PacketUpdatePlayerSession{" +
                "playerUniqueId='" + playerUniqueId + '\'' +
                ", serverId=" + serverId +
                '}';
    }
}
