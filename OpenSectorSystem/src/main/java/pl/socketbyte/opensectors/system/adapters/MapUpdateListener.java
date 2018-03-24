package pl.socketbyte.opensectors.system.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedMap;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedContainer;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedManager;
import pl.socketbyte.opensectors.system.packet.PacketMapUpdate;
import pl.socketbyte.opensectors.system.util.NetworkManager;

import java.util.Map;

public class MapUpdateListener extends Listener {

    @SuppressWarnings("unchecked")
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketMapUpdate))
            return;

        PacketMapUpdate packet = (PacketMapUpdate)object;
        long id = packet.getId();
        Map map = packet.getMap();
        SynchronizedManager<Map> manager =
                SynchronizedContainer.getMapManager();

        if (packet.isCallback()) {
            Map pulled = manager.pull(id);
            if (pulled == null)
                throw new RuntimeException("Map with this ID does not exist or is invalid. [id: " + id + "]");

            packet.setMap(pulled);

            NetworkManager.sendTCP(connection, packet);
            return;
        }
        if (map == null)
            throw new RuntimeException("List can not be null [id: " + id + "]");

        manager.push(id, map);
    }

}
