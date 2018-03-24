package pl.socketbyte.opensectors.linker.api.synchronizable;

import pl.socketbyte.opensectors.linker.api.SectorAPI;
import pl.socketbyte.opensectors.linker.api.callback.CallbackHandler;
import pl.socketbyte.opensectors.linker.packet.Packet;
import pl.socketbyte.opensectors.linker.packet.PacketListUpdate;
import pl.socketbyte.opensectors.linker.packet.PacketMapUpdate;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SynchronizedMap<K, V> extends Synchronizable<Map<K, V>> implements Map<K, V>, Serializable {

    private Map<K, V> map = new HashMap<>();

    public SynchronizedMap(int id) {
        super(id);
        update();
    }

    public SynchronizedMap() {
        super();
    }

    @Override
    public Map<K, V> getData() {
        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void synchronize() {
        PacketMapUpdate<K, V> packet = new PacketMapUpdate<>(true);
        packet.setId(getId());

        CallbackHandler handler = CallbackHandler.make(packet);
        CompletableFuture<Packet> future = handler.getCompletableFuture();

        handler.sendAndPush();

        try {
            PacketMapUpdate<K, V> callback = (PacketMapUpdate<K, V>) future.get();

            this.map = callback.getMap();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update() {
        SectorAPI.sendTCP(new PacketMapUpdate<>(false, this));
    }

    @Override
    public int size() {
        synchronize();
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        synchronize();
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        synchronize();
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        synchronize();
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        synchronize();
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        synchronize();
        V callback = map.put(key, value);
        update();
        return callback;
    }

    @Override
    public V remove(Object key) {
        synchronize();
        V callback = map.remove(key);
        update();
        return callback;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        synchronize();
        map.putAll(m);
        update();
    }

    @Override
    public void clear() {
        synchronize();
        map.clear();
        update();
    }

    @Override
    public Set<K> keySet() {
        synchronize();
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        synchronize();
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        synchronize();
        return map.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        synchronize();
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        synchronize();
        map.forEach(action);
        update();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        synchronize();
        map.replaceAll(function);
        update();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        synchronize();
        V callback = map.putIfAbsent(key, value);
        update();
        return callback;
    }

    @Override
    public boolean remove(Object key, Object value) {
        synchronize();
        boolean callback = map.remove(key, value);
        update();
        return callback;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        synchronize();
        boolean callback = map.replace(key, oldValue, newValue);
        update();
        return callback;
    }

    @Override
    public V replace(K key, V value) {
        synchronize();
        V callback = map.replace(key, value);
        update();
        return callback;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        synchronize();
        V callback = map.computeIfAbsent(key, mappingFunction);
        update();
        return callback;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        synchronize();
        V callback = map.computeIfPresent(key, remappingFunction);
        update();
        return callback;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        synchronize();
        V callback = map.compute(key, remappingFunction);
        update();
        return callback;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        synchronize();
        V callback = map.merge(key, value, remappingFunction);
        update();
        return callback;
    }
}
