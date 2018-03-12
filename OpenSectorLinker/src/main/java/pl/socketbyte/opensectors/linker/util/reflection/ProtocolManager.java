package pl.socketbyte.opensectors.linker.util.reflection;

import pl.socketbyte.opensectors.linker.logging.StackTraceHandler;
import pl.socketbyte.opensectors.linker.util.reflection.v1_11.ActionBar1_11;
import pl.socketbyte.opensectors.linker.util.reflection.v1_8.ActionBar1_8;

public class ProtocolManager {

    private static ActionBar actionBar;

    public static ActionBar getActionBar() {
        if (actionBar == null) {
            String version = Reflection.getVersion();
            if (version == null) {
                StackTraceHandler.handle(ProtocolManager.class, "Unknown bukkit version");
                return null;
            }
            if (version.contains("9")
                    || version.contains("10")
                    || version.contains("11")
                    || version.contains("12")
                    || version.contains("13")) {
                actionBar = new ActionBar1_11();
            }
            else if (version.contains("8"))
                actionBar = new ActionBar1_8();

        }
        return actionBar;
    }

}
