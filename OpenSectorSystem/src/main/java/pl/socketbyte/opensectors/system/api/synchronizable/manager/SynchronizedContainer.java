package pl.socketbyte.opensectors.system.api.synchronizable.manager;

import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedMap;

public class SynchronizedContainer {

    private static final SynchronizedManager<SynchronizedList> listManager =
            new SynchronizedManager<>();
    private static final SynchronizedManager<SynchronizedMap> mapManager =
            new SynchronizedManager<>();

    public static SynchronizedManager<SynchronizedList> getListManager() {
        return listManager;
    }

    public static SynchronizedManager<SynchronizedMap> getMapManager() {
        return mapManager;
    }
}
