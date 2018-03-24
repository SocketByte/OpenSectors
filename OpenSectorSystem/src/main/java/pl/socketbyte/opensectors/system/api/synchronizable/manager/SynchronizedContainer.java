package pl.socketbyte.opensectors.system.api.synchronizable.manager;

import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedMap;

import java.util.List;
import java.util.Map;

public class SynchronizedContainer {

    private static final SynchronizedManager<List> listManager =
            new SynchronizedManager<>();
    private static final SynchronizedManager<Map> mapManager =
            new SynchronizedManager<>();

    public static SynchronizedManager<List> getListManager() {
        return listManager;
    }

    public static SynchronizedManager<Map> getMapManager() {
        return mapManager;
    }
}
