package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
class DefaultPersistenceService extends AbstractPersistenceService {

    public DefaultPersistenceService(EntityManager entityManager, SqlConnection sqlConnection) {
        super(entityManager, sqlConnection);
    }

    @Override
    public <T> void persist(T entity) {

    }

    @Override
    public <T> T find(Long id, Class<T> entityClass) {
        EntityScheme scheme = getEntityScheme(entityClass);
        EntityScheme.PrimaryKeyPropertyMapping primaryKey = scheme.getPrimaryKeyMapping();
        return getEntityPersistence().loadEntity(entityClass, "where " + primaryKey.getDatabaseColumn() + " = ?", id);
    }

    @Override
    public <T> T find(String column, Object value, Class<T> entityClass) {
        return getEntityPersistence().loadEntity(entityClass, "where " + column + " = ?", value);
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        return getEntityPersistence().loadEntities(entityClass, "");
    }

    @Override
    public <T> List<T> loadAll(String column, Object value, Class<T> entityClass) {
        return getEntityPersistence().loadEntities(entityClass, "where " + column + " = ?", value);
    }
}
