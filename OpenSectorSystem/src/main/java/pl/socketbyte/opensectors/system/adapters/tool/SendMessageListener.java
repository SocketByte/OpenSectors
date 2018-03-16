package pl.socketbyte.opensectors.system.adapters.tool;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.socketbyte.opensectors.linker.packet.PacketSendMessage;
import pl.socketbyte.opensectors.system.util.Util;

import java.util.UUID;

public class SendMessageListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if (!(object instanceof PacketSendMessage))
            return;

        PacketSendMessage packet = (PacketSendMessage)object;
        switch (packet.getReceiver()) {
            case ALL:
                for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers())
                    proxiedPlayer.sendMessage(ChatMessageType.valueOf(packet.getMessageType().toString().toUpperCase()),
                            new TextComponent(Util.fixColors(packet.getMessage())));
                break;
            case PLAYER:
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(packet.getPlayerUniqueId()));
                player.sendMessage(ChatMessageType.valueOf(packet.getMessageType().toString().toUpperCase()),
                        new TextComponent(Util.fixColors(packet.getMessage())));
                break;
        }
    }

}
