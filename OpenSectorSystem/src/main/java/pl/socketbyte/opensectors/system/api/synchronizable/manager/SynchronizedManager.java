package pl.socketbyte.opensectors.system.api.synchronizable.manager;

import pl.socketbyte.opensectors.system.api.synchronizable.Synchronizable;
import pl.socketbyte.opensectors.system.api.synchronizable.exception.SynchronizableException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedManager<T> implements ISynchronizedManager<T> {

    private final Map<Long, T> data = new HashMap<>();

    @Override
    public Map<Long, T> getData() {
        return Collections.unmodifiableMap(data);
    }

    @Override
    public void push(long id, T type) {
        data.put(id, type);
    }

    @Override
    public T pull(long id) {
        return data.get(id);
    }
}
