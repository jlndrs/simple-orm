package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.EntityManager;
import de.juliandrees.simpleorm.MappedEntity;
import de.juliandrees.simpleorm.model.BaseEntity;

import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public class DefaultPersistenceService implements PersistenceService {

    private final EntityManager entityManager;

    public DefaultPersistenceService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T extends BaseEntity> void persist(T entity) {

    }

    @Override
    public <T> T find(Long id, Class<? extends BaseEntity> entityClass) {
        Optional<MappedEntity> optionalEntity = entityManager.getMappedEntity(entityClass);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("entity class not mapped: " + entityClass.getName());
        }
        try {
            return (T) entityClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
