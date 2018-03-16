package pl.socketbyte.opensectors.system.packet;

public class PacketPlayerState extends Packet {

    private String playerName;
    private String playerUniqueId;
    private boolean online;
    private int serverId = -1;

    public PacketPlayerState() {

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(String playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "PacketPlayerState{" +
                "playerName='" + playerName + '\'' +
                ", playerUniqueId='" + playerUniqueId + '\'' +
                ", online=" + online +
                ", serverId=" + serverId +
                '}';
    }
}
