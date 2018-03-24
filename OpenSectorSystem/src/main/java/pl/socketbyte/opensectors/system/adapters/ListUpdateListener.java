package pl.socketbyte.opensectors.system.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedContainer;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedManager;
import pl.socketbyte.opensectors.system.packet.PacketListUpdate;
import pl.socketbyte.opensectors.system.util.NetworkManager;

import java.util.Arrays;
import java.util.List;

public class ListUpdateListener extends Listener {

    @SuppressWarnings("unchecked")
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketListUpdate))
            return;

        PacketListUpdate packet = (PacketListUpdate)object;
        long id = packet.getId();
        List list = packet.getList();
        SynchronizedManager<List> manager =
                SynchronizedContainer.getListManager();

        if (packet.isCallback()) {
            List pulled = manager.pull(id);
            if (pulled == null)
                throw new RuntimeException("List with this ID does not exist or is invalid. [id: " + id + "]");

            packet.setList(pulled);

            NetworkManager.sendTCP(connection, packet);
            return;
        }
        if (list == null)
            throw new RuntimeException("List can not be null [id: " + id + "]");

        manager.push(id, list);
    }
}
