package pl.socketbyte.opensectors.system.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.socketbyte.opensectors.system.api.LinkerConnection;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.packet.PacketPlayerState;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class PlayerStateListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketPlayerState))
            return;

        PacketPlayerState packet = (PacketPlayerState) object;
        ProxiedPlayer target = null;
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.getName().equals(packet.getPlayerName())) {
                target = player;
                break;
            }
        }
        packet.setOnline(target != null && target.isConnected());
        if (target != null) {
            packet.setPlayerUniqueId(target.getUniqueId().toString());
            LinkerConnection linkerConnection = LinkerStorage.getLinkerByPlayer(target);
            if (linkerConnection != null)
                packet.setServerId(linkerConnection.getServerId());
        }
        NetworkManager.sendTCP(connection, packet);
    }

}
