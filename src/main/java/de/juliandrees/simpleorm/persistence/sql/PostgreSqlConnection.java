package de.juliandrees.simpleorm.persistence.sql;

import de.juliandrees.simpleorm.persistence.PersistenceConfig;

import java.sql.Driver;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
public class PostgreSqlConnection extends AbstractSqlConnection {

    public PostgreSqlConnection(PersistenceConfig persistenceConfig) {
        super(persistenceConfig);
    }

    @Override
    protected Class<? extends Driver> getDriver() {
        return org.postgresql.Driver.class;
    }
}
