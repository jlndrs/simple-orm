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

    <T> void persist(T entity);

    <T> T find(Long id, Class<?> entityClass);

    <T> List<T> loadAll(Class<?> entityClass);

}
