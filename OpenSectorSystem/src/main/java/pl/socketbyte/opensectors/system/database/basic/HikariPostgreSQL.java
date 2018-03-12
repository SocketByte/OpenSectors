package pl.socketbyte.opensectors.system.database.basic;

import com.zaxxer.hikari.HikariDataSource;
import pl.socketbyte.opensectors.system.database.HikariExtender;

public class HikariPostgreSQL extends HikariExtender {
    @Override
    protected void connect() {
        HikariDataSource dataSource = getDataSource();
        String host = getHost();
        String database = getDatabase();
        String user = getUser();
        String password = getPassword();
        int port = getPort();

        dataSource.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        dataSource.addDataSourceProperty("serverName", host);
        dataSource.addDataSourceProperty("portNumber", port);
        dataSource.addDataSourceProperty("databaseName", database);
        dataSource.addDataSourceProperty("user", user);
        dataSource.addDataSourceProperty("password", password);
        dataSource.setPoolName("HikariPostgreSQL");
        dataSource.setMaxLifetime(0);
        dataSource.setConnectionTimeout(15000);
        dataSource.setValidationTimeout(3000);
        dataSource.setMaximumPoolSize(10);
    }
}

