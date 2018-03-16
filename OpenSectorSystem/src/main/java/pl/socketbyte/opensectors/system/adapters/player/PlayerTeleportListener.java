package pl.socketbyte.opensectors.system.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.socketbyte.opensectors.system.api.LinkerConnection;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.packet.PacketPlayerTeleport;

import java.util.UUID;

public class PlayerTeleportListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketPlayerTeleport))
            return;

        PacketPlayerTeleport packet = (PacketPlayerTeleport)object;
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(packet.getPlayerUniqueId()));
        LinkerConnection linkerConnection = LinkerStorage.getLinkerByPlayer(player);
        if (linkerConnection == null)
            return;

        linkerConnection.sendTCP(packet);
    }

}
