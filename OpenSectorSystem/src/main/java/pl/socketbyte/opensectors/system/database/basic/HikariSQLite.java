package pl.socketbyte.opensectors.system.database.basic;

import com.zaxxer.hikari.HikariDataSource;
import pl.socketbyte.opensectors.system.database.HikariExtender;

import java.util.Properties;

public class HikariSQLite extends HikariExtender {
    @Override
    protected void connect() {
        HikariDataSource dataSource = getDataSource();
        String database = getDatabase();

        dataSource.setJdbcUrl("jdbc:sqlite:" + database + ".db");
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setPoolName("HikariSQLite");
        dataSource.setMaxLifetime(0);
        dataSource.setMaxLifetime(60000);
        dataSource.setIdleTimeout(45000);
        dataSource.setMaximumPoolSize(20);

        Properties properties = new Properties();
        properties.put("driverType", "thin");
        dataSource.setDataSourceProperties(properties);
    }
}
