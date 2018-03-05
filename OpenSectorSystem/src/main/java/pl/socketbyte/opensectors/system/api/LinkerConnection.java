package pl.socketbyte.opensectors.system.api;

import com.esotericsoftware.kryonet.Connection;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class LinkerConnection {

    private final int id;
    private final int serverId;
    private Connection connection;

    public LinkerConnection(int serverId, int id) {
        this.id = id;
        this.serverId = serverId;
    }

    public int getId() {
        return id;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getServerId() {
        return serverId;
    }

    public void sendTCP(Object packet) {
        NetworkManager.sendTCP(this.connection, packet);
    }

    public void sendUDP(Object packet) {
        NetworkManager.sendUDP(this.connection, packet);
    }

    @Override
    public String toString() {
        return "LinkerConnection{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", connection=" + connection +
                '}';
    }
}
