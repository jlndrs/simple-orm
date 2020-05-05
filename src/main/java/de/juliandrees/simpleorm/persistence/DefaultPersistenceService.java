package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.sql.ResultSet;
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
        try {
            EntityScheme.PrimaryKeyPropertyMapping primaryKey = scheme.getPrimaryKeyMapping();

            ResultSet rs = getSqlConnection().result("select * from " + scheme.getEntityName() + " where " + primaryKey.getDatabaseColumn() + " = ?;", id);
            T instance = buildEntity(rs, entityClass, scheme);
            rs.close();
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T find(String column, Object value, Class<T> entityClass) {
        List<T> entities = loadAll(column, value, entityClass);
        if (entities.isEmpty()) {
            return null;
        }
        return entities.get(0);
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        EntityScheme scheme = getEntityScheme(entityClass);
        try {
            ResultSet rs = getSqlConnection().result("select * from " + scheme.getEntityName() + " order by " + scheme.getPrimaryKeyMapping().getDatabaseColumn() + " asc;");
            List<T> entities = buildEntities(rs, entityClass, scheme);
            rs.close();
            return entities;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> List<T> loadAll(String column, Object value, Class<?> entityClass) {
        EntityScheme scheme = getEntityScheme(entityClass);
        try {
            ResultSet resultSet = getSqlConnection().result("select * from " + scheme.getEntityName() + " where " + column + " = ?;", value);
            List<T> entities = buildEntities(resultSet, entityClass, scheme);
            resultSet.close();
            return entities;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
