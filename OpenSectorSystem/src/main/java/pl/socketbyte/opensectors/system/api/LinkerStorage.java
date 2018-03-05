package pl.socketbyte.opensectors.system.api;

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
}
