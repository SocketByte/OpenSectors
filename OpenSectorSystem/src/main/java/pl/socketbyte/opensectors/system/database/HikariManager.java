package pl.socketbyte.opensectors.system.database;

import java.sql.Connection;

public enum HikariManager {
    INSTANCE;

    private HikariExtender hikariExtender;

    public void setHikariExtender(HikariExtender hikariExtender) {
        this.hikariExtender = hikariExtender;
    }

    public HikariExtender getHikariExtender() {
        return hikariExtender;
    }

    public Connection getConnection() {
        return hikariExtender.getConnection();
    }

    public void createBasicTables() {
        hikariExtender
                .getTableWork()
                .createBasicTables(
                        hikariExtender.getConnection());
    }

    public void connect() {
        hikariExtender.connect();
    }
}
