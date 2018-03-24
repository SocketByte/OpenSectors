package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.opensectors.system.api.synchronizable.SynchronizedList;

import java.util.List;

public class PacketListUpdate<E> extends Packet {

    private List<E> list;
    private SynchronizedList<E> synchronizedList;
    private long id;

    public PacketListUpdate(SynchronizedList<E> synchronizedList) {
        this.synchronizedList = synchronizedList;
        this.id = synchronizedList.getId();
    }

    public PacketListUpdate() {

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

    public SynchronizedList<E> getSynchronizedList() {
        return synchronizedList;
    }

    @Override
    public String toString() {
        return "PacketListUpdate{" +
                "synchronizedList=" + synchronizedList +
                '}';
    }
}
