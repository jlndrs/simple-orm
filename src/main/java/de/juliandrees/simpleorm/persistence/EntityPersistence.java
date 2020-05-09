package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.Entities;
import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.entity.PropertyMapping;
import de.juliandrees.simpleorm.persistence.query.QueryFactory;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
class EntityPersistence {

    private final EntityManager entityManager;
    private final SqlConnection sqlConnection;

    EntityPersistence(EntityManager entityManager, SqlConnection sqlConnection) {
        this.entityManager = entityManager;
        this.sqlConnection = sqlConnection;
    }


    public <T> T loadEntity(Class<T> entityClass, QueryFactory queryFactory) {
        return loadEntities(entityClass, queryFactory.limit(1).toSql(), queryFactory.getParameters()).first();
    }

    public <T> List<T> loadEntities(Class<T> entityClass, QueryFactory queryFactory) {
        return loadEntities(entityClass, queryFactory.toSql(), queryFactory.getParameters());
    }

    public <T> Entities<T> loadEntities(Class<T> entityClass, String query, Object... parameters) {
        Entities<T> entities = new Entities<>();
        try (ResultSet resultSet = sqlConnection.result(query, parameters)) {
            while (resultSet.next()) {
                entities.add(resultToEntity(entityClass, resultSet, entityManager.getEntityScheme(entityClass)));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return entities;
    }

    public <T> T resultToEntity(Class<T> entityClass, ResultSet resultSet, EntityScheme entityScheme) throws Exception {
        T instance = entityClass.getConstructor().newInstance();
        for (Map.Entry<String, PropertyMapping> entrySet : entityScheme.getPropertyMappings().entrySet()) {
            Object value = resultSet.getObject(entrySet.getKey(), entrySet.getValue().getFieldType());
            entrySet.getValue().getSetter().invoke(instance, value);
        }
        return instance;
    }

    public <T> String entityToQuery(T entity, EntityScheme entityScheme) {
        return "";
    }

    public int getRowCount(ResultSet resultSet) throws SQLException {
        int rowCount = 0;
        if (resultSet != null) {
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.beforeFirst();
        }
        return rowCount;
    }

}
