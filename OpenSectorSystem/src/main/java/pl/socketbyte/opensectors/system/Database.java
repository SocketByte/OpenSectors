package pl.socketbyte.opensectors.system;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;
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
import java.util.Properties;
import java.util.UUID;

public class Database {

    private static HikariDataSource dataSource;

    private static Connection connection;

    public static void connect(String host, int port, String database, String user, String password) {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPoolName("OpenSectorSystem");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSource.addDataSourceProperty("useServerPrepStmts", true);
        dataSource.addDataSourceProperty("useLocalSessionState", true);
        dataSource.addDataSourceProperty("useLocalTransactionState", true);
        dataSource.addDataSourceProperty("rewriteBatchedStatements", true);
        dataSource.addDataSourceProperty("cacheResultSetMetadata", true);
        dataSource.addDataSourceProperty("cacheServerConfiguration", true);
        dataSource.addDataSourceProperty("elideSetAutoCommits", true);
        dataSource.addDataSourceProperty("maintainTimeStats", false);
        dataSource.setMaxLifetime(0);
        dataSource.setConnectionTimeout(60000);
        dataSource.setValidationTimeout(3000);
        dataSource.setMaximumPoolSize(10);

        Properties properties = new Properties();
        properties.put("driverType", "thin");

        dataSource.setDataSourceProperties(properties);
        try {
            HikariProxyConnection hikariProxyConnection = (HikariProxyConnection) dataSource.getConnection();
            if (hikariProxyConnection.isWrapperFor(Connection.class)) {
                connection = hikariProxyConnection.unwrap(Connection.class);
            }
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.FATAL);
        }

        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS playerData (" +
                            "uniqueId CHAR(36) NOT NULL, " +
                            "serverId INT NOT NULL)");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.ERROR);
        }
    }

    protected static void setPlayerSession(UUID uniqueId, int serverId) {
        if (getPlayerSession(uniqueId) == -1) {
            insertPlayerSession(uniqueId, serverId);
            return;
        }
        updatePlayerSession(uniqueId, serverId);
    }

    protected static void insertPlayerSession(UUID uniqueId, int serverId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO playerData VALUES (?, ?)");
            statement.setString(1, uniqueId.toString());
            statement.setInt(2, serverId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.WARNING);
        }
    }

    protected static void updatePlayerSession(UUID uniqueId, int serverId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "UPDATE playerData SET serverId=? WHERE uniqueId=?");
            statement.setInt(1, serverId);
            statement.setString(2, uniqueId.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.WARNING);
        }
    }

    protected static int getPlayerSession(UUID uniqueId) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "SELECT serverId FROM playerData WHERE uniqueId=?");
            statement.setString(1, uniqueId.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return rs.getInt("serverId");
            statement.close();
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.WARNING);
        }
        return -1;
    }

    public static PreparedStatement executeUpdate(PacketQuery packet) {
        PreparedStatement statement = null;
        try {
            statement = Database.getConnection().prepareStatement(packet.getQuery());
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
        return packet;
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        return connection;
    }


}
