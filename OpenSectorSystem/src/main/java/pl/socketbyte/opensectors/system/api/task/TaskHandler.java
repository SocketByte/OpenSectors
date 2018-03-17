package pl.socketbyte.opensectors.system.api.task;

import pl.socketbyte.opensectors.system.packet.PacketTaskCreate;
import pl.socketbyte.opensectors.system.packet.PacketTaskValidate;
import pl.socketbyte.opensectors.system.util.NetworkManager;

import java.util.concurrent.TimeUnit;

public class TaskHandler {

    private PacketTaskCreate packet;
    private final PacketTaskValidate packetValidate;

    public TaskHandler(PacketTaskCreate packet) {
        this.packet = packet;
        this.packetValidate = new PacketTaskValidate();
        this.packetValidate.setTaskId(packet.getTaskId());
    }

    public void execute() {
        // TODO: 17.03.2018 Send using TCP based on user preference
        NetworkManager.sendAllUDP(packetValidate);
    }

    public void schedule() {
        TaskManager.getExecutor().scheduleAtFixedRate(
                this::execute, getInitialDelay(), getPeriod(), getTimeUnit());
    }

    public void apply() {
        TaskManager.push(this);
    }

    public int getInitialDelay() {
        return packet.getInitialDelay();
    }

    public int getPeriod() {
        return packet.getPeriod();
    }

    public TimeUnit getTimeUnit() {
        return packet.getUnit();
    }

    public long getTaskId() {
        return packet.getTaskId();
    }

}
