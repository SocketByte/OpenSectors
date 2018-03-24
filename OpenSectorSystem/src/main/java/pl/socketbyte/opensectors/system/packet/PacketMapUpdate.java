package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedMap;

import java.util.Map;

public class PacketMapUpdate<K, V> extends Packet {

    private Map<K, V> map;
    private SynchronizedMap<K, V> synchronizedMap;
    private long id;

    public PacketMapUpdate(SynchronizedMap<K, V> synchronizedMap) {
        this.synchronizedMap = synchronizedMap;
        this.id = synchronizedMap.getId();
    }

    public PacketMapUpdate() {

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

    public SynchronizedMap<K, V> getSynchronizedMap() {
        return synchronizedMap;
    }

    @Override
    public String toString() {
        return "PacketMapUpdate{" +
                "synchronizedMap=" + synchronizedMap +
                '}';
    }

}
