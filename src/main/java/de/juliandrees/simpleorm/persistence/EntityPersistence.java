package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.Entities;
import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.entity.PropertyMapping;
import de.juliandrees.simpleorm.persistence.sql.SqlConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

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

    /**
     * Lädt alle Entitäten der Tabelle.
     *
     * @param entityClass der Entitätstyp
     * @param optionalWhere ein optionaler String für where
     * @param parameters optionale Parameter
     */
    public <T> Entities<T> loadEntities(Class<T> entityClass, String optionalWhere, Object... parameters) {
        EntityScheme scheme = getEntityScheme(entityClass);
        EntityScheme.PrimaryKeyPropertyMapping primaryKey = scheme.getPrimaryKeyMapping();
        Entities<T> entities = new Entities<>();
        try (ResultSet rs = sqlConnection.result("select * from " + scheme.getEntityName() + " " + optionalWhere.trim() + " order by " + primaryKey.getDatabaseColumn() + " asc;", parameters)) {
            while (rs.next()) {
                entities.add(resultToEntity(entityClass, rs, scheme));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entities;
    }

    /**
     * Lädt eine Entität aus der Tabelle.
     *
     * @param entityClass der Entiätstyo
     * @param optionalWhere ein optionaler String für where
     * @param parameters optionale Parameter
     */
    public <T> T loadEntity(Class<T> entityClass, String optionalWhere, Object... parameters) {
        return loadEntities(entityClass, optionalWhere + " limit 1", parameters).first();
    }

    protected EntityScheme getEntityScheme(Class<?> entityClass) {
        Optional<EntityScheme> entityScheme = entityManager.getEntityScheme(entityClass);
        if (entityScheme.isEmpty()) {
            throw new IllegalArgumentException(entityClass.getSimpleName() + " is not mapped");
        }
        return entityScheme.get();
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
