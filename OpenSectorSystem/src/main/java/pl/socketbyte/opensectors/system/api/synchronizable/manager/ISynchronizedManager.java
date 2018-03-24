package pl.socketbyte.opensectors.system.api.synchronizable.manager;

import java.util.Map;

public interface ISynchronizedManager<E> {

    Map<Long, E> getData();

    void push(E type);
    E pull(long id);

}
