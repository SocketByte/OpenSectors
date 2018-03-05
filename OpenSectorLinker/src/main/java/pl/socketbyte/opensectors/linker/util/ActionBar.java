package pl.socketbyte.opensectors.linker.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.socketbyte.opensectors.linker.Linker;

public class ActionBar {

    public static void send(Player player, String text) {
        new io.github.theluca98.textapi.ActionBar(text).send(player);
    }

    public static void send(Player player, String text, int seconds) {
        new BukkitRunnable() {
            @Override
            public void run() {
                send(player, text);
            }
        }.runTaskTimer(Linker.getInstance(), 20, seconds * 20);
    }

}
