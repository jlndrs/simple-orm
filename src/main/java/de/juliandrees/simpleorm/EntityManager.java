package de.juliandrees.simpleorm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public class EntityManager implements Manager {

    private final List<Class<?>> entityClasses = new ArrayList<>();
    private final EntityAnalyzer analyzer = new EntityAnalyzer();

    EntityManager(List<Class<?>> entityClasses) {
        this.entityClasses.addAll(entityClasses);
    }

    @Override
    public void onInitialize() {
        entityClasses.forEach(analyzer::analyzeClass);
    }

    public List<Class<?>> getEntityClasses() {
        return entityClasses;
    }

    public List<MappedEntity> getMappedEntities() {
        return analyzer.getMappedEntities();
    }

    public Optional<MappedEntity> getMappedEntity(Class<?> entityClazz) {
        return getMappedEntities().stream().filter(mappedEntity -> mappedEntity.getEntityClass().equals(entityClazz)).findFirst();
    }

    public Optional<MappedEntity> getMappedEntity(String entityName) {
        return getMappedEntities().stream().filter(mappedEntity -> mappedEntity.getEntityName().equalsIgnoreCase(entityName)).findFirst();
    }

}
