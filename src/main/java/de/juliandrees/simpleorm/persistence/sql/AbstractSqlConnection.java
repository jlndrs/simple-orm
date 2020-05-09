package de.juliandrees.simpleorm.persistence.sql;

import de.juliandrees.simpleorm.persistence.PersistenceConfig;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private final boolean showSql;
    private Connection connection;

    public AbstractSqlConnection(PersistenceConfig persistenceConfig) {
        this.host = persistenceConfig.getHost();
        this.database = persistenceConfig.getDatabase();
        this.userName = persistenceConfig.getCredentials().getUserName();
        this.password = persistenceConfig.getCredentials().getPassword();
        this.port = persistenceConfig.getPort();
        this.jdbcType = persistenceConfig.getJdbcType();
        this.showSql = persistenceConfig.isShowSql();
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
            System.out.println("simple-orm - Connection established");
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (isConnected()) {
            this.connection.close();
            System.out.println("simple-orm - Connection closed");
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public PreparedStatement prepare(String query, Object... parameters) throws SQLException {
        if (showSql) {
            System.out.println(query);
        }
        PreparedStatement ps = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        for (int i = 0; i < parameters.length; i++) {
            ps.setObject(i + 1, parameters[i]);
        }
        return ps;
    }

    @Override
    public void update(String query, Object... parameters) throws SQLException {
        try (PreparedStatement statement = this.prepare(query, parameters)) {
            statement.executeUpdate();
        }
    }

    @Override
    public ResultSet result(String query, Object... parameters) throws SQLException {
        return prepare(query, parameters).executeQuery();
    }

    protected abstract Class<? extends Driver> getDriver();

}
