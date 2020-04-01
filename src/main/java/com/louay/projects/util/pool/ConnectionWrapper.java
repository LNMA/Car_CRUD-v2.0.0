package com.louay.projects.util.pool;

import java.sql.Connection;
import java.time.LocalDateTime;

public class ConnectionWrapper {
    private Connection connection;
    private LocalDateTime dataCreate;


    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
        this.dataCreate = LocalDateTime.now();
    }

    public boolean isAlive() {
        return dataCreate.plusHours(6).compareTo(LocalDateTime.now()) < 0;
    }

    public Connection getConnection() {
        return this.connection;
    }

}
