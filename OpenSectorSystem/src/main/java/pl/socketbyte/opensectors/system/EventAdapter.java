package pl.socketbyte.opensectors.system;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import pl.socketbyte.opensectors.system.api.LinkerStorage;
import pl.socketbyte.opensectors.system.json.controllers.ServerController;
import pl.socketbyte.opensectors.system.util.ServerManager;
import pl.socketbyte.opensectors.system.util.Util;

public class EventAdapter implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();

        int lastServerId = Database.getPlayerSession(proxiedPlayer.getUniqueId());
        ServerController controller = OpenSectorSystem.getServerController(lastServerId);
        if (controller == null)
            return;
        if (controller.name.equals(proxiedPlayer.getServer().getInfo().getName()))
            return;

        proxiedPlayer.connect(ServerManager.getServerInfo(lastServerId));
    }

    // Default chat handler, to override make your own ChatEvent and set higher priority
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(ChatEvent event) {
        if (event.isCommand())
            return;
        if (event.isCancelled())
            return;

        event.setCancelled(true);

        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();

        String format = Util.fixColors(OpenSectorSystem.getConfig().defaultChatFormat
                .replace("{PLAYER}", sender.getDisplayName())
                .replace("{MESSAGE}", event.getMessage()));

        for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers())
            proxiedPlayer.sendMessage(new TextComponent(format));
    }
}
