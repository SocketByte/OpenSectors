package pl.socketbyte.opensectors.linker.packet;

import java.util.concurrent.TimeUnit;

public class PacketTaskCreate extends Packet{

    private int initialDelay;
    private int period;
    private TimeUnit unit;

    private long taskId;

    public PacketTaskCreate() {

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

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "PacketTaskCreate{" +
                "initialDelay=" + initialDelay +
                ", period=" + period +
                ", unit=" + unit +
                ", taskId=" + taskId +
                '}';
    }
}
