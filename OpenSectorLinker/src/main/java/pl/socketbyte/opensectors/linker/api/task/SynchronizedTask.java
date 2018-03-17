package pl.socketbyte.opensectors.linker.api.task;

import pl.socketbyte.opensectors.linker.api.SectorAPI;
import pl.socketbyte.opensectors.linker.packet.PacketTaskCreate;
import pl.socketbyte.opensectors.linker.util.Util;

import java.util.concurrent.TimeUnit;

public class SynchronizedTask {

    private final Runnable runnable;
    private final long taskId;

    private int initialDelay;
    private int period;
    private TimeUnit unit;

    public SynchronizedTask(long taskId, Runnable runnable) {
        this.taskId = taskId;
        this.runnable = runnable;
    }

    public void create() {
        TaskManager.push(this);
    }

    public void send() {
        PacketTaskCreate packet = new PacketTaskCreate();
        packet.setInitialDelay(initialDelay);
        packet.setPeriod(period);
        packet.setUnit(unit);
        packet.setTaskId(taskId);

        SectorAPI.sendTCP(packet);
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public int getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public long getTaskId() {
        return taskId;
    }

    public void run() {
        runnable.run();
    }
}
