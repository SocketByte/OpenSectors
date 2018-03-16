package pl.socketbyte.opensectors.linker.packet;

public class PacketPlayerTransfer extends Packet {

    private String playerUniqueId;
    private int serverId;
    private PacketPlayerInfo playerInfo;

    public PacketPlayerTransfer() {

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
        return "PacketPlayerTransfer{" +
                "playerUniqueId='" + playerUniqueId + '\'' +
                ", serverId=" + serverId +
                ", playerInfo=" + playerInfo +
                '}';
    }
}
