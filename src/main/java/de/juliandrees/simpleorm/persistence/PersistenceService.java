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
     * Fetches all entities.
     *
     * @param entityClass class of the entities
     * @param <T> return type (entity type)
     * @return Entities
     */
    <T> List<T> loadAll(Class<T> entityClass);

    /**
     *
     */
    <T, E> List<T> find(String column, E value, Class<T> entityClass);

}
