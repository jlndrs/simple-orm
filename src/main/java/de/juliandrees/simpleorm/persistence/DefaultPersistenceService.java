package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.EntityManager;
import de.juliandrees.simpleorm.MappedEntity;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
class DefaultPersistenceService implements PersistenceService {

    private final EntityManager entityManager;
    private final SqlConnection sqlConnection;

    DefaultPersistenceService(EntityManager entityManager, SqlConnection sqlConnection) {
        this.entityManager = entityManager;
        this.sqlConnection = sqlConnection;
    }

    @Override
    public <T> void persist(T entity) {

    }

    @Override
    public <T> T find(Long id, Class<?> entityClass) {
        Optional<MappedEntity> optionalEntity = entityManager.getMappedEntity(entityClass);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("entity class not mapped: " + entityClass.getName());
        }
        try {
            ResultSet rs = sqlConnection.result("select * from " + optionalEntity.get().getEntityName() + " where ");
            T instance = (T) entityClass.getConstructor().newInstance();
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
