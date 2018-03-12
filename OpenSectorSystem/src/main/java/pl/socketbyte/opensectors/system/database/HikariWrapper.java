package pl.socketbyte.opensectors.system.database;

import com.zaxxer.hikari.HikariDataSource;

public class HikariWrapper extends HikariExtender {

    private final HikariDataSource dataSource;
    private final HikariRunnable runnable;

    public HikariWrapper(HikariRunnable runnable) {
        this.dataSource = new HikariDataSource();
        this.runnable = runnable;
    }

    @Override
    protected void connect() {
        runnable.connect(this);
    }

    @Override
    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
