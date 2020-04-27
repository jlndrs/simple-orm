package de.juliandrees.simpleorm.persistence;

/**
 * Service for entity sql handling.
 * See the default implementation {@link DefaultPersistenceService}.
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public interface PersistenceService {

    <T> void persist(T entity);

    <T> T find(Long id, Class<?> entityClass);

}
