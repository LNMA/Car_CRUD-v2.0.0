package com.louay.projects.beans;

import com.louay.projects.util.pool.ConnectionWrapper;
import com.louay.projects.util.pool.DBConnectionConfig;
import com.louay.projects.util.pool.MyConnectionPool;
import com.louay.projects.util.queue.MyList;
import com.louay.projects.util.queue.MyQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeansFactory {

    @Bean(name = "queue")
    @Scope("prototype")
    public MyList<ConnectionWrapper> getMyQueue() {
        return new MyQueue<>(10);
    }

    @Bean(name = "dbConfig")
    @Scope("prototype")
    public DBConnectionConfig getConnectionConfig(){
        DBConnectionConfig db = new DBConnectionConfig();
        db.setDriver("jdbc:mysql");
        db.setHost("localhost");
        db.setPort("3306");
        db.setSchema("car_crud");
        db.setUsername("root");
        db.setPassword("1729384#General");
        return db;
    }

    @Bean(name = "pool")
    public MyConnectionPool getPool() {
        return new MyConnectionPool();
    }


}
