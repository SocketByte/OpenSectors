package pl.socketbyte.opensectors.system.netty;

import io.netty.channel.Channel;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import pl.socketbyte.opensectors.system.util.Reflection;

public class DimensionChannel {

    private final ProxiedPlayer player;
    private Channel channel;

    public DimensionChannel(ProxiedPlayer player) {
        this.player = player;
        retrieveChannel();
    }

    private void retrieveChannel() {
        try {
            channel = (Channel) Reflection.get(Reflection.get(player, "ch"), "ch");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPipeline() {
        channel.pipeline().addAfter("packet-encoder", "dimension_encoder",
                new DimensionEncoder(player));
    }

}
