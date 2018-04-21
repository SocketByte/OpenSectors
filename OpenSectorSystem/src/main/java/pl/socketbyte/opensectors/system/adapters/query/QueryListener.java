package pl.socketbyte.opensectors.system.adapters.query;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ProxyServer;
import pl.socketbyte.opensectors.system.Database;
import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.packet.PacketQuery;
import pl.socketbyte.opensectors.system.packet.PacketQueryExecute;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (object instanceof PacketQuery
                && (!(object instanceof PacketQueryExecute))) {
            PacketQuery packet = (PacketQuery) object;

            ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
                PreparedStatement statement = Database.executeUpdate(packet);
                try {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        statement.close();
                        statement.getConnection().close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
