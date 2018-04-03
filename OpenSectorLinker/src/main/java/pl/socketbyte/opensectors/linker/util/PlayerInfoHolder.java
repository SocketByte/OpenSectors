package pl.socketbyte.opensectors.linker.util;

import org.bukkit.entity.Player;
import pl.socketbyte.opensectors.linker.packet.PacketPlayerInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInfoHolder {

    private static Map<UUID, PacketPlayerInfo> playerInfos = new HashMap<>();

    public static Map<UUID, PacketPlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public static void push(PacketPlayerInfo packet) {
        playerInfos.put(UUID.fromString(packet.getPlayerUniqueId()), packet);
    }

    public static PacketPlayerInfo pull(Player player) {
        return playerInfos.get(player.getUniqueId());
    }

    public static void clean(Player player) {
        playerInfos.remove(player.getUniqueId());
    }

}
