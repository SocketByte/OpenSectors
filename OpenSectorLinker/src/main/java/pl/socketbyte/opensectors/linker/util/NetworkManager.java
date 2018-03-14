package pl.socketbyte.opensectors.linker.util;

import org.bukkit.Bukkit;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;

public class NetworkManager {

    public static void sendTCP(Object packet) {
        Bukkit.getScheduler().runTaskAsynchronously(OpenSectorLinker.getInstance(),
                () -> OpenSectorLinker.getClient().sendTCP(packet));
    }

    public static void sendUDP(Object packet) {
        Bukkit.getScheduler().runTaskAsynchronously(OpenSectorLinker.getInstance(),
                () -> OpenSectorLinker.getClient().sendUDP(packet));
    }

    public static void sendTCPSync(Object packet) {
        OpenSectorLinker.getClient().sendTCP(packet);
    }

    public static void sendUDPSync(Object packet) {
        OpenSectorLinker.getClient().sendUDP(packet);
    }
}
