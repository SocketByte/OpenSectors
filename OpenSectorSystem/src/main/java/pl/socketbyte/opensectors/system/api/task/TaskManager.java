package pl.socketbyte.opensectors.system.api.task;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskManager {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(
            Runtime.getRuntime().availableProcessors());

    private static final Map<Long, TaskHandler> taskHandles = new LinkedHashMap<>();

    public static Map<Long, TaskHandler> getTaskHandles() {
        return Collections.unmodifiableMap(taskHandles);
    }

    public static void push(TaskHandler taskHandler) {
        taskHandler.schedule();

        taskHandles.put(taskHandler.getTaskId(), taskHandler);
    }

    public static ScheduledExecutorService getExecutor() {
        return executor;
    }

}
