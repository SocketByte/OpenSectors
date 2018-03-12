package pl.socketbyte.opensectors.system.database;

import java.sql.Connection;

public interface HikariTableWork {

    void createBasicTables(Connection connection);

}
