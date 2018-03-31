package pl.socketbyte.opensectors.linker.adapters;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.packet.PacketConfigurationInfo;

public class ConfigurationInfoListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketConfigurationInfo))
            return;

        PacketConfigurationInfo packet = (PacketConfigurationInfo)object;

        OpenSectorLinker.setConfiguration(packet.getJsonConfig());
        OpenSectorLinker.log().info("Successfully received configuration data from the proxy server.");
        OpenSectorLinker.ready();
    }
}
