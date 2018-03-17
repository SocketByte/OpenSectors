package pl.socketbyte.opensectors.linker.packet;

public class PacketTaskValidate extends Packet{

    private long taskId;

    public PacketTaskValidate() {

    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "PacketTaskValidate{" +
                "taskId=" + taskId +
                '}';
    }
}
