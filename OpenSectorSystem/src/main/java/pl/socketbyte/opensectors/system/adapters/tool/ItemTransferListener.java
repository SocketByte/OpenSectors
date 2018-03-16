package pl.socketbyte.opensectors.system.adapters.tool;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.socketbyte.opensectors.system.api.LinkerConnection;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.packet.PacketItemTransfer;
import pl.socketbyte.opensectors.system.packet.types.Receiver;

import java.util.UUID;

public class ItemTransferListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketItemTransfer))
            return;

        PacketItemTransfer packet = (PacketItemTransfer) object;
        switch (packet.getReceiver()) {
            case ALL:
                for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers()) {
                    LinkerConnection linkerConnection = LinkerStorage.getLinkerByPlayer(proxiedPlayer);
                    if (linkerConnection == null)
                        return;
                    packet.setReceiver(Receiver.PLAYER, proxiedPlayer.getUniqueId().toString());
                    linkerConnection.sendTCP(packet);
                }
                break;
            case PLAYER:
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(packet.getPlayerUniqueId()));
                LinkerConnection linkerConnection = LinkerStorage.getLinkerByPlayer(player);
                if (linkerConnection == null)
                    return;
                linkerConnection.sendTCP(packet);
                break;
        }
    }

}
