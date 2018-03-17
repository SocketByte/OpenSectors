package pl.socketbyte.opensectors.linker.api.task;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TaskManager {

    private static final Map<Long, SynchronizedTask> tasks = new LinkedHashMap<>();

    public static Map<Long, SynchronizedTask> getTasks() {
        return Collections.unmodifiableMap(tasks);
    }

    public static void push(SynchronizedTask task) {
        tasks.put(task.getTaskId(), task);
    }

    public static SynchronizedTask pull(long taskId) {
        return tasks.get(taskId);
    }

}
