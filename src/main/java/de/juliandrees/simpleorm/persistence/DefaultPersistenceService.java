package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.EntityManager;
import de.juliandrees.simpleorm.MappedEntity;
import de.juliandrees.simpleorm.PropertyMapping;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public DefaultPersistenceService(EntityManager entityManager, SqlConnection sqlConnection) {
        this.entityManager = entityManager;
        this.sqlConnection = sqlConnection;
    }

    @Override
    public <T> void persist(T entity) {

    }

    @Override
    public <T> T find(Long id, Class<T> entityClass) {
        MappedEntity mappedEntity = getMappedEntity(entityClass);
        try {
            MappedEntity.PrimaryKeyPropertyMapping primaryKey = mappedEntity.getPrimaryKeyMapping();

            ResultSet rs = sqlConnection.result("select * from " + mappedEntity.getEntityName() + " where " + primaryKey.getDatabaseColumn() + " = ?;", id);
            T instance = buildEntity(rs, entityClass, mappedEntity);
            rs.close();
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        MappedEntity mappedEntity = getMappedEntity(entityClass);
        try {
            ResultSet rs = sqlConnection.result("select * from " + mappedEntity.getEntityName() + " order by " + mappedEntity.getPrimaryKeyMapping().getDatabaseColumn() + " asc;");
            List<T> entities = buildEntities(rs, entityClass, mappedEntity);
            rs.close();
            return entities;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T, E> List<T> find(String column, E value, Class<T> entityClass) {
        MappedEntity mappedEntity = getMappedEntity(entityClass);
        try {
            ResultSet rs = sqlConnection.result("select * from " + mappedEntity.getEntityName() + " where " + column + " = ?;", value);
            List<T> entities = buildEntities(rs, entityClass, mappedEntity);
            rs.close();
            return entities;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> T buildEntity(ResultSet resultSet, Class<?> entityClass, MappedEntity scheme) throws Exception {
        T instance = (T) entityClass.getConstructor().newInstance();
        if (resultSet.next()) {
            for (Map.Entry<String, PropertyMapping> entry : scheme.getPropertyMappings().entrySet()) {
                Object value = resultSet.getObject(entry.getKey(), entry.getValue().getFieldType());
                entry.getValue().getSetter().invoke(instance, value);
            }
        }
        return instance;
    }

    private <T> List<T> buildEntities(ResultSet resultSet, Class<?> entityClass, MappedEntity scheme) throws Exception {
        List<T> entities = new ArrayList<>();

        while (!resultSet.isLast()) {
            entities.add(buildEntity(resultSet, entityClass, scheme));
        }
        return entities;
    }

    private MappedEntity getMappedEntity(Class<?> entityClass) {
        Optional<MappedEntity> optionalEntity = entityManager.getMappedEntity(entityClass);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("entity class not mapped: " + entityClass.getName());
        }
        return optionalEntity.get();
    }

    @Override
    public void close() throws IOException {
        try {
            sqlConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
