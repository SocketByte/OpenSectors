package pl.socketbyte.opensectors.linker.api.synchronizable;

import pl.socketbyte.opensectors.linker.packet.PacketListUpdate;
import pl.socketbyte.opensectors.linker.util.NetworkManager;

import java.io.Serializable;
import java.util.*;

public class SynchronizedList<E> extends Synchronizable implements List<E>, Serializable {

    private final List<E> list = new ArrayList<>();

    public SynchronizedList(int id) {
        super(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void synchronize() {
        return;
        /*
        PacketListUpdate<E> packet = new PacketListUpdate<>();
        packet.setId(getId());

        CallbackHandler handler = CallbackHandler.make(packet);
        CompletableFuture<Packet> future = handler.getCompletableFuture();

        handler.sendAndPush();
        System.out.println("SENT LIST WITH ID " + packet.getId());

        try {
            System.out.println("WAITING FOR CALLBACK");
            PacketListUpdate<E> callback = (PacketListUpdate<E>) future.get();

            System.out.println("RECEIVED CALLBACK");
            this.list = callback.getList();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
         */
    }

    public SynchronizedList() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update() {
        System.out.println("SENT LIST TO BE CREATED");
        NetworkManager.sendTCPSync(new PacketListUpdate(this));
    }

    @Override
    public int size() {
        synchronize();
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        synchronize();
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        synchronize();
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        synchronize();
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        synchronize();
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronize();
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        synchronize();
        boolean callback = list.add(e);
        update();
        return callback;
    }

    @Override
    public boolean remove(Object o) {
        synchronize();
        boolean callback = list.remove(o);
        update();
        return callback;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronize();
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        synchronize();
        boolean callback = list.addAll(c);
        update();
        return callback;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        synchronize();
        boolean callback = list.addAll(index, c);
        update();
        return callback;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronize();
        boolean callback = list.removeAll(c);
        update();
        return callback;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronize();
        boolean callback = list.retainAll(c);
        update();
        return callback;
    }

    @Override
    public void clear() {
        synchronize();
        list.clear();
        update();
    }

    @Override
    public boolean equals(Object o) {
        synchronize();
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        synchronize();
        return list.hashCode();
    }

    @Override
    public E get(int index) {
        synchronize();
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        synchronize();
        E callback = list.set(index, element);
        update();
        return callback;
    }

    @Override
    public void add(int index, E element) {
        synchronize();
        list.add(index, element);
        update();
    }

    @Override
    public E remove(int index) {
        synchronize();
        E callback = list.remove(index);
        update();
        return callback;
    }

    @Override
    public int indexOf(Object o) {
        synchronize();
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        synchronize();
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        synchronize();
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        synchronize();
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        synchronize();
        return list.subList(fromIndex, toIndex);
    }
}
