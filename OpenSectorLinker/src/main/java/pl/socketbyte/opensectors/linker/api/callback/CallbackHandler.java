package pl.socketbyte.opensectors.linker.api.callback;

import pl.socketbyte.opensectors.linker.ClientAdapter;
import pl.socketbyte.opensectors.linker.packet.Packet;
import pl.socketbyte.opensectors.linker.util.NetworkManager;

import java.util.concurrent.*;

public class CallbackHandler implements Runnable {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static CallbackHandler make(Packet packet, Callback callbackAction) {
        CallbackHandler callbackHandler = make(packet);
        callbackHandler.setCallbackAction(callbackAction);

        executorService.submit(callbackHandler);
        return callbackHandler;
    }

    public static CallbackHandler make(Packet packet) {
        CallbackHandler callbackHandler = new CallbackHandler();
        callbackHandler.setCallbackAction(null);
        callbackHandler.setCallbackId(packet.getCallbackId());
        callbackHandler.setPacket(packet);
        return callbackHandler;
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

    public void sendAndPush() {
        NetworkManager.sendTCPSync(packet);

        ClientAdapter
                .getCallbackCatcher()
                .push(callbackId, this);
    }

    @Override
    public void run() {
        sendAndPush();
        callback(packet, callbackAction);
    }

    @SuppressWarnings("unchecked")
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

