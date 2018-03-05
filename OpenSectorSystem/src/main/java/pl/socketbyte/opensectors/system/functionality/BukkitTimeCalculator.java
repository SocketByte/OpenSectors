package pl.socketbyte.opensectors.system.functionality;

import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.packet.PacketTimeInfo;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class BukkitTimeCalculator implements Runnable {

    private static int time = 0;

    @Override
    public void run() {
        time += OpenSectorSystem.getConfig().bukkitTimeIncremental;

        if (time >= 24000)
            time = 0;

        PacketTimeInfo packet = new PacketTimeInfo();
        packet.setBukkitTime(time);

        NetworkManager.sendAllUDP(packet);
    }
}
