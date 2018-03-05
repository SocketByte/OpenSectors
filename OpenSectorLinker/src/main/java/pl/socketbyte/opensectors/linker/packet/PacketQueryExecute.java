package pl.socketbyte.opensectors.linker.packet;

import pl.socketbyte.opensectors.linker.packet.serializable.SerializableResultSet;

public class PacketQueryExecute extends PacketQuery {

    private SerializableResultSet resultSet;

    public PacketQueryExecute() {

    }

    public SerializableResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(SerializableResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public String toString() {
        return "PacketQueryExecute{" +
                "resultSet=" + resultSet +
                '}';
    }
}
