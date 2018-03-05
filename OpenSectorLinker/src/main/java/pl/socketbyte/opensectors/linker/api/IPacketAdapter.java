package pl.socketbyte.opensectors.linker.api;

import com.esotericsoftware.kryonet.Connection;

public interface IPacketAdapter {

    void received(Connection connection, Object packet);

}
