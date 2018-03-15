package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.packet.types.Weather;

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
