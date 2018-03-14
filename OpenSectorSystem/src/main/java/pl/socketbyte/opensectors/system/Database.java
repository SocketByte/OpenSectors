package pl.socketbyte.opensectors.system;

import net.md_5.bungee.api.ProxyServer;
import pl.socketbyte.opensectors.system.database.HikariManager;
import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;
import pl.socketbyte.opensectors.system.packet.PacketQuery;
import pl.socketbyte.opensectors.system.packet.PacketQueryExecute;
import pl.socketbyte.opensectors.system.packet.serializable.SerializableResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class Database {

    protected static void setPlayerSession(UUID uniqueId, int serverId) {
        if (getPlayerSession(uniqueId) == -1) {
            insertPlayerSession(uniqueId, serverId);
            return;
        }
        updatePlayerSession(uniqueId, serverId);
    }

    protected static void insertPlayerSession(UUID uniqueId, int serverId) {
        // Not sure if it is safe to async this, probably will make that an option in the future.
        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
            Connection connection = HikariManager.INSTANCE.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO playerData VALUES (?, ?)");
                statement.setString(1, uniqueId.toString());
                statement.setInt(2, serverId);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                StackTraceHandler.handle(Database.class, e, StackTraceSeverity.WARNING);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected static void updatePlayerSession(UUID uniqueId, int serverId) {
        ProxyServer.getInstance().getScheduler().runAsync(OpenSectorSystem.getInstance(), () -> {
            Connection connection = HikariManager.INSTANCE.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE playerData SET serverId=? WHERE uniqueId=?");
                statement.setInt(1, serverId);
                statement.setString(2, uniqueId.toString());
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                StackTraceHandler.handle(Database.class, e, StackTraceSeverity.WARNING);
            }
            finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected static int getPlayerSession(UUID uniqueId) {
        Connection connection = HikariManager.INSTANCE.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT serverId FROM playerData WHERE uniqueId=?");
            statement.setString(1, uniqueId.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return rs.getInt("serverId");
            statement.close();
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.WARNING);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static PreparedStatement executeUpdate(PacketQuery packet) {
        Connection connection = HikariManager.INSTANCE.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(packet.getQuery());
            for (Map.Entry<Integer, Object> replacement : packet.getReplacements().entrySet())
                statement.setObject(replacement.getKey(), replacement.getValue());
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.ERROR);
        }
        return statement;
    }

    public static PacketQueryExecute executeQuery(PacketQueryExecute packet) {
        PreparedStatement statement = executeUpdate(packet);
        try {
            ResultSet resultSet = statement.executeQuery();
            SerializableResultSet serializableResultSet = new SerializableResultSet(resultSet);
            serializableResultSet.populate();

            packet.setResultSet(serializableResultSet);
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.ERROR);
        }
        finally {
            try {
                statement.close();
                statement.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return packet;
    }


}
