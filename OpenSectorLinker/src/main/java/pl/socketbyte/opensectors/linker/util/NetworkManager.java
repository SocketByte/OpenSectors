package pl.socketbyte.opensectors.linker.util;

import pl.socketbyte.opensectors.linker.Linker;

public class NetworkManager {

    public static void sendTCP(Object packet) {
        Linker.getClient().sendTCP(packet);
    }

    public static void sendUDP(Object packet) {
        Linker.getClient().sendUDP(packet);
    }

}
