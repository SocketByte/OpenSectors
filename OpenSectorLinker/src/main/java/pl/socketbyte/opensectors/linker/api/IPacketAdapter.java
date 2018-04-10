package pl.socketbyte.opensectors.linker.api;

import com.esotericsoftware.kryonet.Connection;

public interface IPacketAdapter<V> {

    void received(Connection connection, V packet);

}
