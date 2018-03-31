package pl.socketbyte.opensectors.system.packet;

import pl.socketbyte.wrapp.Wrapper;

public class PacketWrapper<T> extends PacketCustomPayload {

    private Wrapper<T> wrapper;

    public PacketWrapper() {

    }

    public Wrapper<T> getWrapper() {
        return wrapper;
    }

    public void setWrapper(Wrapper<T> wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String toString() {
        return "PacketWrapper{" +
                "wrapper=" + wrapper +
                '}';
    }
}
