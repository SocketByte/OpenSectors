package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.opensectors.system.packet.serializable.Weather;

public class PacketWeatherInfo extends Packet {

    private Weather weather;

    public PacketWeatherInfo() {

    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "PacketWeatherInfo{" +
                "weather=" + weather +
                '}';
    }
}
