package pl.socketbyte.opensectors.system.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorScheduler {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(
            Runtime.getRuntime().availableProcessors());

    public static void runScheduler(Runnable runnable, long frequency) {
        executor.scheduleAtFixedRate(runnable, 0, frequency, TimeUnit.MILLISECONDS);
    }

}
