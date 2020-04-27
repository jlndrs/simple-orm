package de.juliandrees.simpleorm;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 26.04.2020
 */
public final class EntityManagerFactory {

    private EntityManagerFactory() { }

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

}
