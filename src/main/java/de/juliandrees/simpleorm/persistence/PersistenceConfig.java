package de.juliandrees.simpleorm.persistence;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
public class PersistenceConfig {

    private PersistenceCredentials credentials;
    private String host;
    private String database;
    private int port;
    private String jdbcType;
    private boolean showSql;

    PersistenceConfig(PersistenceCredentials credentials, String host, String database, int port, String jdbcType, boolean showSql) {
        this.credentials = credentials;
        this.host = host;
        this.database = database;
        this.port = port;
        this.jdbcType = jdbcType;
        this.showSql = showSql;
    }

    public PersistenceCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(PersistenceCredentials credentials) {
        this.credentials = credentials;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public static class PersistenceCredentials {

        private String userName;
        private String password;

        PersistenceCredentials(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
