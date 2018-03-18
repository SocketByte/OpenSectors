package pl.socketbyte.opensectors.system.packet;

public class PacketPlayerTeleport extends Packet {

    private String playerUniqueId;
    private String targetUniqueId;
    private double x;
    private double y;
    private double z;
    private PacketPlayerInfo playerInfo;

    public PacketPlayerTeleport() {

    }

    public void setLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketPlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PacketPlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public String getPlayerUniqueId() {
        return playerUniqueId;
    }

    public void setPlayerUniqueId(String playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    public String getTargetUniqueId() {
        return targetUniqueId;
    }

    public void setTargetUniqueId(String targetUniqueId) {
        this.targetUniqueId = targetUniqueId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "PacketPlayerTeleport{" +
                "playerUniqueId='" + playerUniqueId + '\'' +
                ", targetUniqueId='" + targetUniqueId + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", playerInfo=" + playerInfo +
                '}';
    }
}
