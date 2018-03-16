package pl.socketbyte.opensectors.linker.adapters.sync;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import pl.socketbyte.opensectors.linker.packet.PacketWeatherInfo;
import pl.socketbyte.opensectors.linker.packet.types.Weather;

public class WeatherInfoListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketWeatherInfo))
            return;

        PacketWeatherInfo weatherInfo = (PacketWeatherInfo) object;
        Weather weather = weatherInfo.getWeather();

        World world = Bukkit.getWorlds().get(0);

        switch (weather) {
            case RAIN:
                world.setStorm(true);
                world.setThundering(false);
                break;
            case CLEAR:
                world.setStorm(false);
                world.setThundering(false);
                break;
            case STORM:
                world.setStorm(true);
                world.setThundering(true);
                break;
        }
    }
}
