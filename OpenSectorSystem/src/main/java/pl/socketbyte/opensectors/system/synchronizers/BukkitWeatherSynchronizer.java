package pl.socketbyte.opensectors.system.synchronizers;

import pl.socketbyte.opensectors.system.packet.PacketWeatherInfo;
import pl.socketbyte.opensectors.system.packet.serializable.Weather;
import pl.socketbyte.opensectors.system.util.NetworkManager;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BukkitWeatherSynchronizer implements Runnable {

    private static Weather weather = Weather.CLEAR;
    private static int nextWeatherChange = ThreadLocalRandom.current().nextInt(24000 * 7);

    @Override
    public void run() {
        if (BukkitTimeSynchronizer.getWeatherTime() >= nextWeatherChange) {
            Weather weather = Weather.values()[ThreadLocalRandom.current().nextInt(Weather.values().length - 1)];

            PacketWeatherInfo weatherInfo = new PacketWeatherInfo();
            weatherInfo.setWeather(weather);

            NetworkManager.sendAllUDP(weatherInfo);

            nextWeatherChange = ThreadLocalRandom.current().nextInt(24000 * 7);
            BukkitTimeSynchronizer.setWeatherTime(0);
        }
        else {
            PacketWeatherInfo weatherInfo = new PacketWeatherInfo();
            weatherInfo.setWeather(weather);
            NetworkManager.sendAllUDP(weatherInfo);
        }
    }
}
