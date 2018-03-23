package pl.socketbyte.opensectors.system.packet.serializable;

import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SerializableResultSet implements Serializable {

    private transient ResultSet resultSet;
    private transient int currentIndex = 0;
    private transient Object[] cursor;
    private List<Object[]> data = new LinkedList<>();

    public SerializableResultSet() {

    }

    public SerializableResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void populate() {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Object[] values = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    values[i - 1] = resultSet.getObject(i);
                }
                data.add(values);
            }
        } catch (SQLException e) {
            StackTraceHandler.handle(SerializableResultSet.class, e, StackTraceSeverity.WARNING);
        }
    }

    public boolean next() {
        cursor = data.get(currentIndex);

        currentIndex++;
        return currentIndex < data.size();
    }

    public Object getObject(int index) {
        return cursor[index];
    }

    public int getInteger(int index) {
        return (int) getObject(index);
    }

    public short getShort(int index) {
        return (short) getObject(index);
    }

    public long getLong(int index) {
        return (long) getObject(index);
    }

    public double getDouble(int index) {
        return (double) getObject(index);
    }

    public float getFloat(int index) {
        return (float) getObject(index);
    }

    public char getChar(int index) {
        return (char) getObject(index);
    }

    public boolean getBoolean(int index) {
        return (boolean) getObject(index);
    }

    public byte getByte(int index) {
        return (byte) getObject(index);
    }

    public byte[] getByteArray(int index) {
        return (byte[]) getObject(index);
    }

    public String getString(int index) {
        return String.valueOf(getObject(index));
    }

    @Override
    public String toString() {
        return "SerializableResultSet{" +
                ", data=" + data +
                '}';
    }
}
