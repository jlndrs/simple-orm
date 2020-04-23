package de.juliandrees.simpleorm;

import java.util.ArrayList;
import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public class EntityManager implements Manager {

    private final List<Class<?>> entityClasses = new ArrayList<>();
    private final EntityAnalyzer analyzer = new EntityAnalyzer();

    private EntityManager(List<Class<?>> entityClasses) {
        this.entityClasses.addAll(entityClasses);
    }

    @Override
    public void onInitialize() {
        entityClasses.forEach(analyzer::analyzeClass);
    }

    public static EntityManager scanPackage(Class<?> classInPackage, boolean recursive) {
        return scanPackage(classInPackage.getPackageName(), recursive);
    }

    public static EntityManager scanPackage(String packageName, boolean recursive) {
        EntityScanner scanner = new EntityScanner();
        try {
            EntityManager entityManager = new EntityManager(scanner.scanPackage(packageName, recursive));
            entityManager.onInitialize();
            return entityManager;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Class<?>> getEntityClasses() {
        return entityClasses;
    }
}
