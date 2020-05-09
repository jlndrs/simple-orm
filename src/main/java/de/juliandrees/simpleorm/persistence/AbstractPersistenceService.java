package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;
import lombok.AccessLevel;
import lombok.Getter;

import java.sql.SQLException;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 29.04.2020
 */
@Getter
public abstract class AbstractPersistenceService implements PersistenceService {

    private final EntityManager entityManager;
    private final EntityPersistence entityPersistence;

    @Getter(AccessLevel.PROTECTED)
    private final SqlConnection sqlConnection;

    public AbstractPersistenceService(EntityManager entityManager, SqlConnection sqlConnection) {
        this.entityManager = entityManager;
        this.sqlConnection = sqlConnection;
        this.entityPersistence = new EntityPersistence(entityManager, sqlConnection);
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public void close() {
        try {
            sqlConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
