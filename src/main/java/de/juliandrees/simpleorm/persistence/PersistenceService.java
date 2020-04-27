package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.model.BaseEntity;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public interface PersistenceService {

    <T> void persist(T entity);

    <T> T find(Long id, Class<?> entityClass);

}
