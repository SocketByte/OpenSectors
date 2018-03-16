package pl.socketbyte.opensectors.linker.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerTeleport;

import java.util.UUID;

public class PlayerTeleportListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketPlayerTeleport))
            return;

        PacketPlayerTeleport packet = (PacketPlayerTeleport)object;
        Player player = Bukkit.getPlayer(UUID.fromString(packet.getPlayerUniqueId()));
        if (packet.getTargetUniqueId() != null) {
            Player target = Bukkit.getPlayer(UUID.fromString(packet.getTargetUniqueId()));
            if (target == null)
                return;
            player.teleport(target);
            return;
        }
        Location location = packet.getLocation();

        player.teleport(location);
    }
}
