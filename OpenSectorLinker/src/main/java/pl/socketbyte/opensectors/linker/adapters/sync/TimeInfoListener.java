package pl.socketbyte.opensectors.linker.adapters.sync;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.bukkit.Bukkit;
import pl.socketbyte.opensectors.linker.packet.PacketTimeInfo;

public class TimeInfoListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketTimeInfo))
            return;

        PacketTimeInfo packet = (PacketTimeInfo)object;

        Bukkit.getWorlds().get(0).setTime(packet.getBukkitTime());
    }

}
