package pl.socketbyte.opensectors.system.database.basic;

import com.zaxxer.hikari.HikariDataSource;
import pl.socketbyte.opensectors.system.database.HikariExtender;

public class HikariMariaDb extends HikariExtender {
    @Override
    protected void connect() {
        HikariDataSource dataSource = getDataSource();
        String host = getHost();
        String database = getDatabase();
        String user = getUser();
        String password = getPassword();
        int port = getPort();

        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setDataSourceClassName("org.mariadb.jdbc.MySQLDataSource");
        dataSource.setPoolName("HikariMariaDB");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.addDataSourceProperty("serverName", host);
        dataSource.addDataSourceProperty("port", port);
        dataSource.addDataSourceProperty("databaseName", database);
        dataSource.setMaxLifetime(0);
        dataSource.setConnectionTimeout(15000);
        dataSource.setValidationTimeout(3000);
        dataSource.setMaximumPoolSize(10);

        dataSource.addDataSourceProperty("properties",
                "useUnicode=true;characterEncoding=utf8");

    }
}
