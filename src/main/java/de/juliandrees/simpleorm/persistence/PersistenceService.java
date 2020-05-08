package de.juliandrees.simpleorm.persistence;

import java.io.Closeable;
import java.util.List;

/**
 * Service for entity sql handling.
 * See the default implementation {@link DefaultPersistenceService}.
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public interface PersistenceService extends Closeable {

    void onInitialize();

    /**
     * Store an entity to the database.
     *
     * @param entity the entity to store
     * @param <T> the entity type
     */
    <T> void persist(T entity);

    /**
     * Fetch an entity from the database.
     *
     * @param id the id of the entity (Pri)
     * @param entityClass class of the entity
     * @param <T> return type (entity type)
     * @return the entity from database
     */
    <T> T find(Long id, Class<T> entityClass);

    /**
     * Fetch an entity from the database by another column.
     *
     * @param column the database field name
     * @param value the property to use for comparison
     * @param entityClass class of the entity
     * @param <T> return type
     * @return the entity
     */
    <T> T find(String column, Object value, Class<T> entityClass);

    /**
     * Fetches all entities.
     *
     * @param entityClass class of the entities
     * @param <T> return type (entity type)
     * @return Entities
     */
    <T> List<T> loadAll(Class<T> entityClass);

    /**
     * Fetches all entites.
     *
     * @param column the database field name
     * @param value the property to use for comparison
     * @param entityClass class of the entity
     * @param <T> return type
     * @return all entities
     */
    <T> List<T> loadAll(String column, Object value, Class<T> entityClass);

}
