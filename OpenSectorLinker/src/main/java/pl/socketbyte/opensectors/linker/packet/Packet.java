package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.util.Util;

import java.io.Serializable;
import java.util.UUID;

public class Packet implements Serializable {

    private static final long serialVersionUID = -5395761249179523853L;

    private long callbackId;

    public Packet() {
        this.callbackId = Util.getRandomLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public long getCallbackId() {
        return callbackId;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "callbackId=" + callbackId +
                '}';
    }
}
