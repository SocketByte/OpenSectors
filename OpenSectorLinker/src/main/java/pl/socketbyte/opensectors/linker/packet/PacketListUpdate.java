package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.api.synchronizable.SynchronizedList;
import pl.socketbyte.opensectors.linker.api.synchronizable.SynchronizedMap;

import java.util.List;

public class PacketListUpdate<E> extends Packet {

    private List<E> list;
    private boolean callback;
    private long id;

    public PacketListUpdate(boolean callback, SynchronizedList<E> synchronizedList) {
        this.list = synchronizedList.getData();
        this.id = synchronizedList.getId();
        this.callback = callback;
    }

    public PacketListUpdate(boolean callback) {
        this.callback = callback;
    }

    public PacketListUpdate() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public boolean isCallback() {
        return callback;
    }

    public void setCallback(boolean callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "PacketListUpdate{" +
                "id=" + id +
                '}';
    }
}
