package pl.socketbyte.opensectors.system.api;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.json.controllers.ServerController;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class LinkerStorage {

    private static Map<Integer, LinkerConnection> linkerConnectionMap = new HashMap<>();

    public static Map<Integer, LinkerConnection> getLinkerConnectionMap() {
        return linkerConnectionMap;
    }

    public static void setLinkerConnectionMap(Map<Integer, LinkerConnection> linkerConnectionMap) {
        LinkerStorage.linkerConnectionMap = linkerConnectionMap;
    }

    public static LinkerConnection getLinker(int serverId) {
        return linkerConnectionMap.get(serverId);
    }

    public static LinkerConnection getLinkerByConnectionId(int id) {
        for (LinkerConnection connection : linkerConnectionMap.values()) {
            if (connection.getId() == id)
                return connection;
        }
        return null;
    }

    public static LinkerConnection getLinkerByPlayer(ProxiedPlayer player) {
        ServerInfo server = player.getServer().getInfo();
        for (ServerController controller : OpenSectorSystem.getConfig().serverControllers) {
            if (controller.name.equals(server.getName()))
                return linkerConnectionMap.get(controller.id);
        }
        return null;
    }
}
