package pl.socketbyte.opensectors.linker.api.callback;

import pl.socketbyte.opensectors.linker.packet.Packet;

import java.util.LinkedHashMap;
import java.util.Map;

public class CallbackManager {

    private final Map<Long, CallbackHandler> callbacks = new LinkedHashMap<>();

    public void complete(Packet packet) {
        if (!callbacks.containsKey(packet.getCallbackId()))
            return;

        callbacks
                .get(packet.getCallbackId())
                .getCompletableFuture()
                .complete(packet);
    }

    public void push(long callbackId, CallbackHandler future) {
        callbacks.put(callbackId, future);
    }


}
