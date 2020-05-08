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

    public Optional<EntityScheme> getEntityScheme(Class<?> entityClazz) {
        return getEntitySchemes().stream().filter(scheme -> scheme.getEntityClass().equals(entityClazz)).findFirst();
    }

    public Optional<EntityScheme> getEntityScheme(String entityName) {
        return getEntitySchemes().stream().filter(scheme -> scheme.getEntityName().equalsIgnoreCase(entityName)).findFirst();
    }

}
