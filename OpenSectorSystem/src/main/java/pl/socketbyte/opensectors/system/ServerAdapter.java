package pl.socketbyte.opensectors.system;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.socketbyte.opensectors.linker.packet.PacketSendMessage;
import pl.socketbyte.opensectors.system.api.ChannelManager;
import pl.socketbyte.opensectors.system.api.LinkerConnection;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.api.PacketExtender;
import pl.socketbyte.opensectors.system.logging.ByteInformator;
import pl.socketbyte.opensectors.system.logging.ByteUtil;
import pl.socketbyte.opensectors.system.packet.*;
import pl.socketbyte.opensectors.system.packet.types.Receiver;
import pl.socketbyte.opensectors.system.util.NetworkManager;
import pl.socketbyte.opensectors.system.util.ServerManager;
import pl.socketbyte.opensectors.system.util.Util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class ServerAdapter extends Listener {

    @Override
    public void disconnected(Connection connection) {
        OpenSectorSystem.log().info("Linker (ID #" + connection.getID() + ") connection lost. ("
                + connection.getRemoteAddressTCP() + ")");

        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        ByteInformator.incrementBytesReceived(ByteUtil.sizeof(object));

        if (!SecurityModule.isAuthorized(connection, object))
            return;

        for (PacketExtender packetExtender : PacketExtender.getPacketExtenders())
            if (object.getClass().isAssignableFrom(packetExtender.getPacket())) {
                if (packetExtender.getPacketAdapter() == null)
                    continue;

                packetExtender.getPacketAdapter().received(connection, object);
            }

        super.received(connection, object);
    }
}
