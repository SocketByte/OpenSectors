package pl.socketbyte.opensectors.linker.api;

import pl.socketbyte.opensectors.linker.packet.PacketQueryExecute;

public interface Callback {

    void received(PacketQueryExecute query);

}
