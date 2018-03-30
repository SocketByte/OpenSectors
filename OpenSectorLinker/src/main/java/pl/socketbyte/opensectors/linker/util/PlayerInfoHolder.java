package pl.socketbyte.opensectors.linker.util;

import pl.socketbyte.opensectors.linker.packet.PacketPlayerInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInfoHolder {

    private static Map<UUID, PacketPlayerInfo> playerInfos = new HashMap<>();

    public static Map<UUID, PacketPlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

}
