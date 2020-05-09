package de.juliandrees.simpleorm.entity;

import de.juliandrees.simpleorm.exception.WrongAnnotationUsageException;

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
    private final List<EntityScheme> schemes = new ArrayList<>();

    EntityManager(List<Class<?>> entityClasses) {
        this.entityClasses.addAll(entityClasses);
    }

    @Override
    public void onInitialize() {
        EntitySchemeBuilder analyzer = new EntitySchemeBuilder();
        for (Class<?> entityClass : this.entityClasses) {
            try {
                schemes.add(analyzer.newEntityScheme(entityClass));
            } catch (WrongAnnotationUsageException e) {
                e.printStackTrace();
            }
        }
    }

    public List<EntityScheme> getEntitySchemes() {
        return this.schemes;
    }

    public EntityScheme getEntityScheme(Class<?> entityClass) {
        Optional<EntityScheme> optionalScheme =  getEntitySchemes().stream().filter(scheme -> scheme.getEntityClass().equals(entityClass)).findFirst();
        if (optionalScheme.isEmpty()) {
            throw new IllegalArgumentException(entityClass.getSimpleName() + " is not mapped");
        }
        return optionalScheme.get();
    }

    public Optional<EntityScheme> getEntityScheme(String entityName) {
        return getEntitySchemes().stream().filter(scheme -> scheme.getEntityName().equalsIgnoreCase(entityName)).findFirst();
    }

}
