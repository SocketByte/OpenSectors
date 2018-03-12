package pl.socketbyte.opensectors.linker.util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.socketbyte.opensectors.linker.OpenSectorLinker;

public class ActionBar {

    public static void send(Player player, String text) {
        new io.github.theluca98.textapi.ActionBar(Util.fixColors(text)).send(player);
    }

    public static void send(Player player, String text, int seconds) {
        new BukkitRunnable() {
            @Override
            public void run() {
                send(player, text);
            }
        }.runTaskTimer(OpenSectorLinker.getInstance(), 20, seconds * 20);
    }

}
