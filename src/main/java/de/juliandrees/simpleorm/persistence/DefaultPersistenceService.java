package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.select.SelectQueryFactory;
import de.juliandrees.simpleorm.persistence.query.select.Where;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;
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
        getEntityPersistence().persist(entity);
    }

    @Override
    public <T> T find(Long id, Class<T> entityClass) {
        EntityScheme scheme = getEntityManager().getEntityScheme(entityClass);
        EntityScheme.PrimaryKeyPropertyMapping primaryKey = scheme.getPrimaryKeyMapping();
        SelectQueryFactory factory = createQueryFactory(entityClass).where(Where.of(primaryKey.getDatabaseColumn(), id, EqualityType.EQUALS)).limit(1);
        return find(entityClass, factory);
    }

    @Override
    public <T> T find(String column, Object value, Class<T> entityClass) {
        return find(entityClass, createQueryFactory(entityClass).where(Where.of(column, value, EqualityType.EQUALS)).limit(1));
    }

    @Override
    public <T> T find(Class<T> entityClass, SelectQueryFactory selectQueryFactory) {
        return getEntityPersistence().loadEntity(entityClass, selectQueryFactory);
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        return loadAll(entityClass, createQueryFactory(entityClass));
    }

    @Override
    public <T> List<T> loadAll(String column, Object value, Class<T> entityClass) {
        return loadAll(entityClass, createQueryFactory(entityClass).where(Where.of(column, value, EqualityType.EQUALS)));
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass, SelectQueryFactory selectQueryFactory) {
        return getEntityPersistence().loadEntities(entityClass, selectQueryFactory);
    }
}
