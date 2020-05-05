package de.juliandrees.simpleorm.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Getter
public class EntityScheme {

    private Map<String, PropertyMapping> propertyMappings = new HashMap<>();
    private PrimaryKeyPropertyMapping primaryKeyMapping;

    private final Class<?> entityClass;
    private final String entityName;

    public EntityScheme(Class<?> entityClass, String entityName) {
        this.entityClass = entityClass;
        this.entityName = entityName;
    }

    public void addMapping(String databaseColumn, PropertyMapping propertyMapping, boolean primaryKey) {
        propertyMappings.put(databaseColumn, propertyMapping);
        if (primaryKey) {
            this.primaryKeyMapping = new PrimaryKeyPropertyMapping(databaseColumn, propertyMapping);
        }
    }

    @Getter
    public static class PrimaryKeyPropertyMapping {

        private final String databaseColumn;
        private final PropertyMapping propertyMapping;

        public PrimaryKeyPropertyMapping(String databaseColumn, PropertyMapping propertyMapping) {
            this.databaseColumn = databaseColumn;
            this.propertyMapping = propertyMapping;
        }
    }
}
