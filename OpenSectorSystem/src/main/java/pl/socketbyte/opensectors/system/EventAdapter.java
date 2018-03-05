package pl.socketbyte.opensectors.system;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.socketbyte.opensectors.system.util.ServerManager;
import pl.socketbyte.opensectors.system.util.Util;

public class EventAdapter implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();

        int lastServerId = Database.getPlayerSession(proxiedPlayer.getUniqueId());

        if (lastServerId == -1)
            lastServerId = 0;

        proxiedPlayer.connect(ServerManager.getServerInfo(lastServerId));
    }

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if (event.isCommand())
            return;
        if (event.isCancelled())
            return;

        event.setCancelled(true);

        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();

        for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers())
            proxiedPlayer.sendMessage(new TextComponent(Util.fixColors("&7" + sender.getName() + "&8: &f" +
                    event.getMessage())));
    }
}
