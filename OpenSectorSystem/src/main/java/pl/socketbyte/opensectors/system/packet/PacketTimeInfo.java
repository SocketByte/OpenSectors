package pl.socketbyte.opensectors.system.packet;

public class PacketTimeInfo extends Packet {

    private int time;

    public PacketTimeInfo() {

    }

    public int getBukkitTime() {
        return time;
    }

    public void setBukkitTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "PacketTimeInfo{" +
                "time=" + time +
                '}';
    }
}
