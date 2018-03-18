package pl.socketbyte.opensectors.linker.adapters.player;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.api.SectorAPI;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerTeleport;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerTransfer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayerTeleportListener extends Listener{

    private static Map<UUID, CompletableFuture<Player>> awaitingConnections = new HashMap<>();

    public static void complete(Player player) {
        if (!awaitingConnections.containsKey(player.getUniqueId()))
            return;

        awaitingConnections.get(player.getUniqueId()).complete(player);
    }

    public static void push(UUID uniqueId) {
        awaitingConnections.put(uniqueId, new CompletableFuture<>());
    }

    public static Player pull(UUID uniqueId) {
        try {
            return awaitingConnections.get(uniqueId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketPlayerTeleport))
            return;

        PacketPlayerTeleport packet = (PacketPlayerTeleport)object;
        PacketPlayerTransfer transfer = new PacketPlayerTransfer();
        transfer.setPlayerUniqueId(packet.getPlayerUniqueId());
        transfer.setServerId(OpenSectorLinker.getServerId());
        transfer.setPlayerInfo(packet.getPlayerInfo());
        SectorAPI.sendTCP(transfer);

        UUID playerUniqueId = UUID.fromString(packet.getPlayerUniqueId());

        push(playerUniqueId);
        Player player = pull(playerUniqueId);
        if (player == null)
            return;

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
