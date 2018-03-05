package pl.socketbyte.opensectors.system.packet;

import java.io.Serializable;
import java.util.UUID;

public class Packet implements Serializable {

    private static final long serialVersionUID = -5395761249179523853L;

    private final long time;
    private final String uniqueId;

    public Packet() {
        this.time = System.currentTimeMillis();
        this.uniqueId = UUID.randomUUID().toString();
    }

    public long getTime() {
        return time;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "time=" + time +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
