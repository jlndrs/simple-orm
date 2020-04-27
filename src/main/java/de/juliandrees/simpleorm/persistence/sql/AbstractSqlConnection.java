package de.juliandrees.simpleorm.persistence.sql;

import de.juliandrees.simpleorm.persistence.PersistenceConfig;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 26.04.2020
 */
public abstract class AbstractSqlConnection implements SqlConnection {

    private final String host, database, userName, password;
    private final int port;
    private final String jdbcType;
    private Connection connection;

    public AbstractSqlConnection(PersistenceConfig persistenceConfig) {
        this.host = persistenceConfig.getHost();
        this.database = persistenceConfig.getDatabase();
        this.userName = persistenceConfig.getCredentials().getUserName();
        this.password = persistenceConfig.getCredentials().getPassword();
        this.port = persistenceConfig.getPort();
        this.jdbcType = persistenceConfig.getJdbcType();
    }

    @Override
    public void onInitialize() {
        try {
            Class.forName(getDriver().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection() throws SQLException {
        if (!isConnected()) {
            this.connection = DriverManager.getConnection("jdbc:" + this.jdbcType + "://" + this.host + ":" + this.port + "/" + this.database, this.userName, this.password);
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (isConnected()) {
            this.connection.close();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    protected abstract Class<? extends Driver> getDriver();

}
