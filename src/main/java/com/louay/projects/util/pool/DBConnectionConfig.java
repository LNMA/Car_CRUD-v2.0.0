package com.louay.projects.util.pool;



public class DBConnectionConfig {

    private String driver;
    private String host;
    private String port;
    private String schema;
    private String username;
    private String password;


    public DBConnectionConfig() {
    }

    private String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    private String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    private String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        String url = String.format("%s://%s:%s/%s", getDriver(), getHost(), getPort(), getSchema());
        return url;
    }

}
