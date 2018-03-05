package pl.socketbyte.opensectors.system.logging;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ByteInformator {

    private static final int INITIAL_DELAY = 0;
    private static final int FREQUENCY = 860;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private static int bytesSent = 0;
    private static int bytesReceived = 0;

    public static void runScheduledTask() {
        executor.scheduleAtFixedRate(
                new ByteInformatorTask(),
                INITIAL_DELAY,
                FREQUENCY,
                UNIT);
    }

    public static void setBytesSent(int bytesSent) {
        ByteInformator.bytesSent = bytesSent;
    }

    public static void setBytesReceived(int bytesReceived) {
        ByteInformator.bytesReceived = bytesReceived;
    }

    public static int getBytesSent() {
        return bytesSent;
    }

    public static int getBytesReceived() {
        return bytesReceived;
    }

    public static void incrementBytesSent(int bytesSent) {
        ByteInformator.bytesSent += bytesSent;
    }

    public static void incrementBytesReceived(int bytesReceived) {
        ByteInformator.bytesReceived += bytesReceived;
    }
}
