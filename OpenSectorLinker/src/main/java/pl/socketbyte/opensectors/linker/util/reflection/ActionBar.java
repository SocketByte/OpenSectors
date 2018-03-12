package pl.socketbyte.opensectors.linker.util.reflection;

import org.bukkit.entity.Player;

public interface ActionBar {
    void send(Player player, String content);
    void send(String content);
    Object getPacket(String content);
}