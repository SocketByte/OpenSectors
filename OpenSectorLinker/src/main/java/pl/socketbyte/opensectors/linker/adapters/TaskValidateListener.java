package pl.socketbyte.opensectors.linker.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.linker.api.task.SynchronizedTask;
import pl.socketbyte.opensectors.linker.api.task.TaskManager;
import pl.socketbyte.opensectors.linker.packet.PacketTaskValidate;

public class TaskValidateListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketTaskValidate))
            return;

        PacketTaskValidate packet = (PacketTaskValidate)object;

        SynchronizedTask task = TaskManager.pull(packet.getTaskId());
        if (task == null)
            return;

        task.getRunnable().run();
    }
}
