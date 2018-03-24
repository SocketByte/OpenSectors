package pl.socketbyte.opensectors.system.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedMap;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedContainer;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedManager;
import pl.socketbyte.opensectors.system.packet.PacketMapUpdate;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class MapUpdateListener extends Listener {

    @SuppressWarnings("unchecked")
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketMapUpdate))
            return;

        PacketMapUpdate packet = (PacketMapUpdate)object;
        long id = packet.getId();
        SynchronizedMap map = packet.getSynchronizedMap();
        SynchronizedManager<SynchronizedMap> manager =
                SynchronizedContainer.getMapManager();

        if (map == null) {
            SynchronizedMap pulled = manager.pull(id);

            packet.setMap(pulled.getData());

            NetworkManager.sendTCP(connection, packet);
            return;
        }
        manager.push(map);
    }

}
