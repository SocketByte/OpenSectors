package pl.socketbyte.opensectors.system.database;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;
import pl.socketbyte.opensectors.system.Database;
import pl.socketbyte.opensectors.system.logging.StackTraceHandler;
import pl.socketbyte.opensectors.system.logging.StackTraceSeverity;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class HikariExtender {

    private final HikariDataSource dataSource;
    private HikariTableWork tableWork;

    private String host;
    private int port;
    private String database;
    private String user;
    private String password;

    public HikariExtender() {
        this.dataSource = new HikariDataSource();
    }

    public HikariExtender apply() {
        HikariManager.INSTANCE.setHikariExtender(this);
        return this;
    }

    public void setTableWork(HikariTableWork tableWork) {
        this.tableWork = tableWork;
    }

    protected HikariTableWork getTableWork() {
        return tableWork;
    }

    protected abstract void connect();

    public Connection getConnection() {
        Connection connection = null;
        try {
            HikariProxyConnection hikariProxyConnection = (HikariProxyConnection) getDataSource().getConnection();
            if (hikariProxyConnection.isWrapperFor(Connection.class)) {
                connection = hikariProxyConnection.unwrap(Connection.class);
            }
        } catch (SQLException e) {
            StackTraceHandler.handle(Database.class, e, StackTraceSeverity.FATAL);
        }
        return connection;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
