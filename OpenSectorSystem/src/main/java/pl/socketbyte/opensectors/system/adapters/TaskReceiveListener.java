package pl.socketbyte.opensectors.system.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.system.api.task.TaskHandler;
import pl.socketbyte.opensectors.system.api.task.TaskManager;
import pl.socketbyte.opensectors.system.packet.PacketTaskCreate;

public class TaskReceiveListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketTaskCreate))
            return;

        PacketTaskCreate packet = (PacketTaskCreate)object;
        if (TaskManager.getTaskHandles().containsKey(packet.getTaskId()))
            return;

        TaskHandler handler = new TaskHandler(packet);

        if (packet.getUnit() == null) {
            handler.execute();
            return;
        }
        handler.apply();
    }
}
