package pl.socketbyte.opensectors.system.adapters.query;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ProxyServer;
import pl.socketbyte.opensectors.system.Database;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.packet.PacketQueryExecute;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class QueryExecuteListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketQueryExecute))
            return;

        PacketQueryExecute packet = (PacketQueryExecute) object;

        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(),
                () -> NetworkManager.sendTCP(connection, Database.executeQuery(packet)));
    }

}
