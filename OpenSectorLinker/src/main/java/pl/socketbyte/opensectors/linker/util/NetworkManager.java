package pl.socketbyte.opensectors.linker.util;

import pl.socketbyte.opensectors.linker.OpenSectorLinker;

public class NetworkManager {

    public static void sendTCP(Object packet) {
        OpenSectorLinker.getClient().sendTCP(packet);
    }

    public static void sendUDP(Object packet) {
        OpenSectorLinker.getClient().sendUDP(packet);
    }

}
