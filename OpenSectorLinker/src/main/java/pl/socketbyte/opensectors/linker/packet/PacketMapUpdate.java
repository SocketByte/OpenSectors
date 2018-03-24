package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.linker.api.synchronizable.SynchronizedMap;

import java.util.List;
import java.util.Map;

public class PacketMapUpdate<K, V> extends Packet {

    private Map<K, V> map;
    private boolean callback;
    private long id;

    public PacketMapUpdate(boolean callback, SynchronizedMap<K, V> synchronizedMap) {
        this.map = synchronizedMap.getData();
        this.id = synchronizedMap.getId();
        this.callback = callback;
    }

    public PacketMapUpdate(boolean callback) {
        this.callback = callback;
    }

    public PacketMapUpdate() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    public boolean isCallback() {
        return callback;
    }

    public void setCallback(boolean callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "PacketMapUpdate{" +
                "id=" + id +
                '}';
    }

}
