package pl.socketbyte.opensectors.linker.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.socketbyte.opensectors.linker.json.controllers.ServerController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static long getRandomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public static List<String> fixColors(List<String> texts) {
        List<String> strings = new ArrayList<>();
        for (String str : texts)
            strings.add(Util.fixColors(str));
        return strings;
    }
}
