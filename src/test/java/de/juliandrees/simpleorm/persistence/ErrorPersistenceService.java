package de.juliandrees.simpleorm.persistence;

import java.io.IOException;
import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 28.04.2020
 */
public class ErrorPersistenceService implements PersistenceService {

    @Override
    public <T> void persist(T entity) {

    }

    @Override
    public <T> T find(Long id, Class<?> entityClass) {
        return null;
    }

    @Override
    public <T> List<T> loadAll(Class<?> entityClass) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
