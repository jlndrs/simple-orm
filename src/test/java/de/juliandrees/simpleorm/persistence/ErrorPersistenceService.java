package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.persistence.query.select.SelectQueryFactory;

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
    public void close() {

    }

    @Override
    public <T> T find(Long id, Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> T find(String column, Object value, Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> T find(Class<T> entityClass, SelectQueryFactory selectQueryFactory) {
        return null;
    }

    @Override
    public <T> List<T> loadAll(String column, Object value, Class<T> entityClass) {
        return null;
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass, SelectQueryFactory selectQueryFactory) {
        return null;
    }
}
