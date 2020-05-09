package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.Entities;
import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.entity.PropertyMapping;
import de.juliandrees.simpleorm.persistence.query.QueryFactory;
import de.juliandrees.simpleorm.persistence.query.persist.InsertQueryFactory;
import de.juliandrees.simpleorm.persistence.query.persist.UpdateQueryFactory;
import de.juliandrees.simpleorm.persistence.query.select.SelectQueryFactory;
import de.juliandrees.simpleorm.persistence.query.select.Where;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.sql.ResultSet;
import java.util.HashMap;
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

    public <T> void persist(T entity) {
        EntityScheme scheme = entityManager.getEntityScheme(entity.getClass());
        try {
            QueryFactory factory = entityToQuery(entity, scheme);
            sqlConnection.update(factory.toSql(entityManager), factory.getParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T loadEntity(Class<T> entityClass, SelectQueryFactory selectQueryFactory) {
        return loadEntities(entityClass, selectQueryFactory.limit(1).toSql(entityManager), selectQueryFactory.getParameters()).first();
    }

    public <T> List<T> loadEntities(Class<T> entityClass, SelectQueryFactory selectQueryFactory) {
        return loadEntities(entityClass, selectQueryFactory.toSql(entityManager), selectQueryFactory.getParameters());
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

    public <T> QueryFactory entityToQuery(T entity, EntityScheme entityScheme) throws Exception {
        HashMap<String, Object> values = getEntityValues(entity, entityScheme);
        Long primaryKeyValue = (Long) entityScheme.getPrimaryKeyMapping().getPropertyMapping().getGetter().invoke(entity);
        QueryFactory factory;
        if (primaryKeyValue == null) {
            factory = new InsertQueryFactory().insertInto(entityScheme.getEntityClass());
            values.forEach(((InsertQueryFactory) factory)::addValue);
        } else {
            factory = new UpdateQueryFactory().updateTable(entityScheme.getEntityClass());
            values.forEach(((UpdateQueryFactory) factory)::setValue);
            ((UpdateQueryFactory) factory).where(Where.of(entityScheme.getPrimaryKeyMapping().getDatabaseColumn(), primaryKeyValue, EqualityType.EQUALS));
        }
        return factory;
    }

    private <T> HashMap<String, Object> getEntityValues(T entity, EntityScheme scheme) throws Exception {
        HashMap<String, Object> values = new HashMap<>(scheme.getPropertyMappings().size());
        for (Map.Entry<String, PropertyMapping> mapping : scheme.getPropertyMappings().entrySet()) {
            Object value = mapping.getValue().getGetter().invoke(entity);
            values.put(mapping.getKey(), value);
        }
        return values;
    }

}
