package pl.socketbyte.opensectors.linker.packet;

public class PacketLinkerAuthRequest extends Packet {

    private int serverId;
    private String password;

    public PacketLinkerAuthRequest() {

    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PacketLinkerAuthRequest{" +
                "serverId=" + serverId +
                ", password='" + password + '\'' +
                '}';
    }
}
