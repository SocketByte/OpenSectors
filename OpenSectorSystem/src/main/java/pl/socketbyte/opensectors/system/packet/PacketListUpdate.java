package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;

import java.util.List;

public class PacketListUpdate<E> extends Packet {

    private List<E> list;
    private boolean callback;
    private long id;

    public PacketListUpdate(SynchronizedList<E> synchronizedList) {
        this.list = synchronizedList.getData();
        this.id = synchronizedList.getId();
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
