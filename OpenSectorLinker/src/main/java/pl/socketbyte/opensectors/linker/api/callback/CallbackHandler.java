package pl.socketbyte.opensectors.linker.api.callback;

import pl.socketbyte.opensectors.linker.ClientAdapter;
import pl.socketbyte.opensectors.linker.packet.Packet;
import pl.socketbyte.opensectors.linker.util.NetworkManager;

import java.util.concurrent.*;

public class CallbackHandler implements Runnable {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public static void make(Packet packet, Callback callbackAction) {
        CallbackHandler callbackHandler = new CallbackHandler();
        callbackHandler.setCallbackAction(callbackAction);
        callbackHandler.setCallbackId(packet.getCallbackId());
        callbackHandler.setPacket(packet);

        executorService.submit(callbackHandler);
    }

    private CallbackHandler() {

    }

    private final CompletableFuture<Packet> completableFuture = new CompletableFuture<>();

    private Packet packet;
    private Callback callbackAction;
    private long callbackId;

    public CompletableFuture<Packet> getCompletableFuture() {
        return completableFuture;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Callback getCallbackAction() {
        return callbackAction;
    }

    public void setCallbackAction(Callback callbackAction) {
        this.callbackAction = callbackAction;
    }

    public long getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(long callbackId) {
        this.callbackId = callbackId;
    }

    @Override
    public void run() {
        NetworkManager.sendTCP(packet);

        ClientAdapter
                .getCallbackCatcher()
                .push(callbackId, this);

        callback(packet, callbackAction);
    }

    public void callback(Packet packet, Callback callbackAction) {
        Packet callback = null;
        try {
            callback = completableFuture.get();

            callbackAction.execute(callback);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

