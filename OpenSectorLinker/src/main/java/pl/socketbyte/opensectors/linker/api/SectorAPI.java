package pl.socketbyte.opensectors.linker.api;

import pl.socketbyte.opensectors.linker.OpenSectorLinker;
import pl.socketbyte.opensectors.linker.api.callback.Callback;
import pl.socketbyte.opensectors.linker.api.callback.CallbackHandler;
import pl.socketbyte.opensectors.linker.api.task.SynchronizedTask;
import pl.socketbyte.opensectors.linker.api.task.TaskManager;
import pl.socketbyte.opensectors.linker.packet.Packet;
import pl.socketbyte.opensectors.linker.util.NetworkManager;
import pl.socketbyte.opensectors.linker.util.Util;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This API is targeted towards beginner users unfamiliar with any technology presented here.
 * It limits Kryo or Kryonet usage as much as possible to make simple, fun coding experience for new programmers.
 */
@SuppressWarnings("unchecked")
public class SectorAPI {

    /**
     * Registers new payload channel behaviour using packet adapter interface
     * @param channel Channel name
     * @param packetAdapter Packet adapter (event handler)
     */
    public static void registerPayloadChannel(String channel, IPacketAdapter packetAdapter) {
        ChannelManager.getChannels().put(channel, packetAdapter);
    }

    /**
     * @return All PacketExtenders currently working (if registered)
     */
    public static List<PacketExtender> getPacketExtenders() {
        return PacketExtender.getPacketExtenders();
    }

    /**
     * Creates an PacketExtender class which contains
     * pre-applied packet class and automatically registers it to Kryo serializer, and adds it to the list
     * @param packet Class which will be further sent through proxy and linkers
     * @return PacketExtender class
     */
    public static <V> PacketExtender<V> createPacketExtender(Class<? extends V> packet) {
        return new PacketExtender(packet)
                .register()
                .add();
    }

    /**
     * Registers additional class which you can use to send through proxy and linker
     * @param clazz External class which will be used in one of your Packet classes
     */
    public static void register(Class clazz) {
        OpenSectorLinker.getClient().getKryo().register(clazz);
    }

    /**
     * Sends an TCP packet to proxy
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static <T> void sendTCP(T packet) {
        NetworkManager.sendTCP(packet);
    }

    /**
     * Sends an UDP packet to proxy
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static <T> void sendUDP(T packet) {
        NetworkManager.sendUDP(packet);
    }

    /**
     * Sends an TCP packet to proxy
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static <T> CallbackHandler sendTCP(T packet, Callback<T> callback) {
        return CallbackHandler.make((Packet) packet, callback);
    }

    /**
     * Sends an UDP packet to proxy
     * @param packet Object which extends Packet class, containing serializable fields and methods
     */
    public static <T> CallbackHandler sendUDP(T packet, Callback<T> callback) {
        return CallbackHandler.make((Packet) packet, callback);
    }

    /**
     * Creates a task which is synchronized with all connected sectors
     * @param taskId Task id is an identifier. Just set it to any number
     * @param runnable A code which will be executed
     * @param initialDelay Initial delay
     * @param period A delay between code execution
     * @param unit Time unit
     */
    public static void createTask(long taskId, Runnable runnable, int initialDelay, int period, TimeUnit unit) {
        SynchronizedTask synchronizedTask = new SynchronizedTask(taskId, runnable);
        synchronizedTask.setInitialDelay(initialDelay);
        synchronizedTask.setPeriod(period);
        synchronizedTask.setUnit(unit);

        synchronizedTask.create();
        synchronizedTask.send();
    }

    /**
     * Creates a singleshot task which will be executed on all sectors
     * @param runnable A code which will be executed
     * @return SynchronizedTask object to save or send
     */
    public static SynchronizedTask prepareSingleShot(long taskId, Runnable runnable) {
        SynchronizedTask synchronizedTask = new SynchronizedTask(taskId, runnable);
        synchronizedTask.setInitialDelay(0);
        synchronizedTask.setPeriod(0);
        synchronizedTask.setUnit(null);

        synchronizedTask.create();
        return synchronizedTask;
    }

    /**
     * Executes a singleshot task by taskId
     * @param taskId A task id (random long number)
     */
    public static void executeSingleShot(long taskId) {
        TaskManager.pull(taskId).send();
    }

}
