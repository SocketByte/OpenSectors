package pl.socketbyte.opensectors.system.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.packet.Respawn;

import java.util.List;

public class DimensionEncoder extends MessageToMessageEncoder<DefinedPacket> {

    private final ProxiedPlayer player;

    public DimensionEncoder(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          DefinedPacket definedPacket, List<Object> list) {
        if (definedPacket instanceof Respawn) {
            Respawn respawn = (Respawn)definedPacket;

            UserConnection userConnection = (UserConnection)player;
            if (userConnection.getDimension() != respawn.getDimension())
                respawn.setDimension(userConnection.getDimension());
        }
        list.add(definedPacket);
    }
}
