package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.entity.PropertyMapping;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;
import lombok.AccessLevel;
import lombok.Getter;

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

    protected <T> T buildEntity(ResultSet resultSet, Class<?> entityClass, EntityScheme scheme) throws Exception {
        T instance = null;
        if (resultSet.next()) {
            instance = (T) entityClass.getConstructor().newInstance();
            for (Map.Entry<String, PropertyMapping> entry : scheme.getPropertyMappings().entrySet()) {
                Object value = resultSet.getObject(entry.getKey(), entry.getValue().getFieldType());
                entry.getValue().getSetter().invoke(instance, value);
            }
        }
        return instance;
    }

    protected <T> List<T> buildEntities(ResultSet resultSet, Class<?> entityClass, EntityScheme scheme) throws Exception {
        int rowCount = 0;
        if (resultSet != null) {
            resultSet.last();
            rowCount = resultSet.getRow();
            resultSet.beforeFirst();
        }
        List<T> entities = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            entities.add(buildEntity(resultSet, entityClass, scheme));
        }
        return entities;
    }
    protected EntityScheme getEntityScheme(Class<?> entityClass) {
        Optional<EntityScheme> optionalScheme = getEntityManager().getEntityScheme(entityClass);
        if (optionalScheme.isEmpty()) {
            throw new IllegalArgumentException("entity class not mapped: " + entityClass.getName());
        }
        return optionalScheme.get();
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
