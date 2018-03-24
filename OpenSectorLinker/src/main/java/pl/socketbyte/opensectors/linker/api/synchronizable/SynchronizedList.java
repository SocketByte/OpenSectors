package pl.socketbyte.opensectors.linker.api.synchronizable;

import pl.socketbyte.opensectors.linker.api.callback.CallbackHandler;
import pl.socketbyte.opensectors.linker.packet.Packet;
import pl.socketbyte.opensectors.linker.packet.PacketListUpdate;
import pl.socketbyte.opensectors.linker.util.NetworkManager;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SynchronizedList<E> extends Synchronizable<List<E>> implements List<E>, Serializable {

    private List<E> content = new ArrayList<>();

    public SynchronizedList(int id) {
        super(id);
        update();
    }

    public SynchronizedList() {
        super();
    }

    @Override
    public List<E> getData() {
        return content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void synchronize() {
        PacketListUpdate<E> packet = new PacketListUpdate<>(true);
        packet.setId(getId());

        CallbackHandler handler = CallbackHandler.make(packet);
        CompletableFuture<Packet> future = handler.getCompletableFuture();

        handler.sendAndPush();

        try {
            PacketListUpdate<E> callback = (PacketListUpdate<E>) future.get();

            this.content = callback.getList();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update() {
        NetworkManager.sendTCPSync(new PacketListUpdate(false, this));
    }

    @Override
    public int size() {
        synchronize();
        return content.size();
    }

    @Override
    public boolean isEmpty() {
        synchronize();
        return content.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        synchronize();
        return content.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        synchronize();
        return content.iterator();
    }

    @Override
    public Object[] toArray() {
        synchronize();
        return content.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronize();
        return content.toArray(a);
    }

    @Override
    public boolean add(E e) {
        synchronize();
        boolean callback = content.add(e);
        update();
        return callback;
    }

    @Override
    public boolean remove(Object o) {
        synchronize();
        boolean callback = content.remove(o);
        update();
        return callback;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronize();
        return content.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        synchronize();
        boolean callback = content.addAll(c);
        update();
        return callback;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        synchronize();
        boolean callback = content.addAll(index, c);
        update();
        return callback;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronize();
        boolean callback = content.removeAll(c);
        update();
        return callback;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronize();
        boolean callback = content.retainAll(c);
        update();
        return callback;
    }

    @Override
    public void clear() {
        synchronize();
        content.clear();
        update();
    }

    @Override
    public boolean equals(Object o) {
        synchronize();
        return content.equals(o);
    }

    @Override
    public int hashCode() {
        synchronize();
        return content.hashCode();
    }

    @Override
    public E get(int index) {
        synchronize();
        return content.get(index);
    }

    @Override
    public E set(int index, E element) {
        synchronize();
        E callback = content.set(index, element);
        update();
        return callback;
    }

    @Override
    public void add(int index, E element) {
        synchronize();
        content.add(index, element);
        update();
    }

    @Override
    public E remove(int index) {
        synchronize();
        E callback = content.remove(index);
        update();
        return callback;
    }

    @Override
    public int indexOf(Object o) {
        synchronize();
        return content.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        synchronize();
        return content.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        synchronize();
        return content.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        synchronize();
        return content.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        synchronize();
        return content.subList(fromIndex, toIndex);
    }
}
