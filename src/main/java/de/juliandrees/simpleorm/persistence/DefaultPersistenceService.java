package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.model.BaseEntity;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public class DefaultPersistenceService implements PersistenceService {

    @Override
    public <T extends BaseEntity> void persist(T entity) {

    }

    @Override
    public <T> T find(Long id, Class<? extends BaseEntity> entityClass) {
        return null;
    }
}
