package pl.socketbyte.opensectors.system.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedContainer;
import pl.socketbyte.opensectors.system.api.synchronizable.manager.SynchronizedManager;
import pl.socketbyte.opensectors.system.packet.PacketListUpdate;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class ListUpdateListener extends Listener {

    @SuppressWarnings("unchecked")
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        System.out.println("RECEIVED UNIDENTIFIED PACKET " + object.toString());
        if (!(object instanceof PacketListUpdate))
            return;
        System.out.println("RECEIVED PACKET LIST UPDATE");

        PacketListUpdate packet = (PacketListUpdate)object;
        long id = packet.getId();
        SynchronizedList list = packet.getSynchronizedList();
        SynchronizedManager<SynchronizedList> manager =
                SynchronizedContainer.getListManager();

        if (list == null) {
            System.out.println("NULL LIST, ID: " + id);

            SynchronizedList pulled = manager.pull(id);
            System.out.println("RECEIVED LIST");

            packet.setList(pulled.getData());
            System.out.println("SET LIST");

            System.out.println("EVERYTHING FINE, SENDING CALLBACK");
            NetworkManager.sendTCP(connection, packet);
            return;
        }
        System.out.println("PUSHED LIST WITH ID " + list.getId());
        manager.push(list);
    }
}
