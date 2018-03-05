package pl.socketbyte.opensectors.system.logging;

import net.md_5.bungee.api.ProxyServer;
import pl.socketbyte.opensectors.system.OpenSectorSystem;

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
        System.out.println("OpenSectorSystem version: " + OpenSectorSystem.VERSION);
        System.out.println("[------------- End of error -------------]");
    }

    public static void handle(Class<?> clazz, Exception e, StackTraceSeverity severity) {
        handle(clazz, e);

        switch (severity) {
            case FATAL:
                ProxyServer.getInstance().stop("A fatal error occured in OpenSectorSystem. (" + e.getMessage() + ")");
                break;
            case WARNING:
                OpenSectorSystem.getInstance().close();
                break;
        }
    }

}
