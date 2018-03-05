package pl.socketbyte.opensectors.system.api;

import com.esotericsoftware.kryonet.Connection;

public interface IPacketAdapter {

    void received(Connection connection, Object packet);

}
