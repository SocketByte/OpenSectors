package pl.socketbyte.opensectors.system.database.basic;

import com.zaxxer.hikari.HikariDataSource;
import pl.socketbyte.opensectors.system.database.HikariExtender;

import java.util.Properties;

public class HikariMySQL extends HikariExtender {
    @Override
    protected void connect() {
        HikariDataSource dataSource = getDataSource();
        String host = getHost();
        String database = getDatabase();
        String user = getUser();
        String password = getPassword();
        int port = getPort();

        dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPoolName("HikariMySQL");
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
        dataSource.setConnectionTimeout(15000);
        dataSource.setValidationTimeout(3000);
        dataSource.setMaximumPoolSize(10);

        Properties properties = new Properties();
        properties.put("driverType", "thin");
        dataSource.setDataSourceProperties(properties);
    }
}
