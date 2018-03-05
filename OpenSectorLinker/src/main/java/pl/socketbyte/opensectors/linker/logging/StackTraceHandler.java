package pl.socketbyte.opensectors.linker.logging;

import org.bukkit.Bukkit;
import pl.socketbyte.opensectors.linker.Linker;

import java.util.Arrays;

public class StackTraceHandler {

    public static void handle(Class<?> clazz, Exception e) {
        System.out.println("[------------- Error occured :( -------------]");
        System.out.println("Error occured when executing class `" + clazz.getSimpleName() + "`!");
        System.out.println();
        System.out.println("  Error message: " + (e.getMessage() == null
                ? "Unfortunately, the infamous null pointer exception occured."
                : e.getMessage()));
        System.out.println("  Error class: " + clazz.getName() + " (@i: " + Arrays.toString(clazz.getAnnotations()) + ")");
        System.out.println();
        System.out.println("[------------- Detailed stacktrace -------------]");
        e.printStackTrace();
        System.out.println("[------------- End of detailed stacktrace -------------]");
        System.out.println("OpenSectorLinker version: " + Linker.VERSION);
        System.out.println("[------------- End of error -------------]");
    }

    public static void handle(Class<?> clazz, Exception e, StackTraceSeverity severity) {
        handle(clazz, e);

        switch (severity) {
            case FATAL:
                Bukkit.shutdown();
                break;
            case WARNING:
                Bukkit.getPluginManager().disablePlugin(Linker.getInstance());
                break;
        }
    }

}
