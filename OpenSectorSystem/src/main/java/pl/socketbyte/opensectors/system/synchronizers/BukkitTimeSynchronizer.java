package pl.socketbyte.opensectors.system.synchronizers;

import pl.socketbyte.opensectors.system.OpenSectorSystem;
import pl.socketbyte.opensectors.system.packet.PacketTimeInfo;
import pl.socketbyte.opensectors.system.util.NetworkManager;

public class BukkitTimeSynchronizer implements Runnable {

    private static int time = 0;
    private static int weatherTime = 0;

    public static int getTime() {
        return time;
    }

    public static int getWeatherTime() {
        return weatherTime;
    }

    public static void setWeatherTime(int weatherTime) {
        BukkitTimeSynchronizer.weatherTime = weatherTime;
    }

    @Override
    public void run() {
        time += OpenSectorSystem.getConfig().bukkitTimeIncremental;
        weatherTime += time;

        if (time >= 24000)
            time = 0;

        PacketTimeInfo packet = new PacketTimeInfo();
        packet.setBukkitTime(time);

        NetworkManager.sendAllUDP(packet);
    }
}
