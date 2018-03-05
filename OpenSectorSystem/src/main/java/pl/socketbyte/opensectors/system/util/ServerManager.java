package pl.socketbyte.opensectors.system.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import pl.socketbyte.opensectors.system.OpenSectorSystem;

import java.util.Objects;
import java.util.UUID;

public class ServerManager {

    public static void transfer(UUID uniqueId, int serverId) {
        Util.getPlayer(uniqueId).connect(getServerInfo(serverId));
    }

    public static ServerInfo getServerInfo(int serverId) {
        return ProxyServer.getInstance().getServerInfo(
                Objects.requireNonNull(OpenSectorSystem.getServerController(serverId)).name);
    }

}
