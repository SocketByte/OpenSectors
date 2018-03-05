package pl.socketbyte.opensectors.system.packet;

public class PacketPlayerTransferRequest extends Packet {

    private String playerUniqueId;
    private int serverId;
    private PacketPlayerInfo playerInfo;

    public PacketPlayerTransferRequest() {

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

    public PacketPlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PacketPlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    @Override
    public String toString() {
        return "PacketPlayerTransferRequest{" +
                "playerUniqueId='" + playerUniqueId + '\'' +
                ", serverId=" + serverId +
                ", playerInfo=" + playerInfo +
                '}';
    }
}
