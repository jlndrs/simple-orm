package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.QueryFactory;
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

    }

    @Override
    public <T> T find(Long id, Class<T> entityClass) {
        EntityScheme scheme = getEntityManager().getEntityScheme(entityClass);
        EntityScheme.PrimaryKeyPropertyMapping primaryKey = scheme.getPrimaryKeyMapping();
        QueryFactory factory = QueryFactory.selectFrom(scheme.getEntityName()).where(primaryKey.getDatabaseColumn(), EqualityType.EQUALS, id).create().limit(1);
        return find(entityClass, factory);
    }

    @Override
    public <T> T find(String column, Object value, Class<T> entityClass) {
        EntityScheme scheme = getEntityManager().getEntityScheme(entityClass);
        return find(entityClass, QueryFactory.selectFrom(scheme.getEntityName()).where(column, EqualityType.EQUALS, value).create().limit(1));
    }

    @Override
    public <T> T find(Class<T> entityClass, QueryFactory queryFactory) {
        return getEntityPersistence().loadEntity(entityClass, queryFactory);
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        EntityScheme scheme = getEntityManager().getEntityScheme(entityClass);
        return getEntityPersistence().loadEntities(entityClass, QueryFactory.selectFrom(scheme.getEntityName()));
    }

    @Override
    public <T> List<T> loadAll(String column, Object value, Class<T> entityClass) {
        return getEntityPersistence().loadEntities(entityClass, "where " + column + " = ?", value);
    }
}
