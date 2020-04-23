package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.annotation.EntityMapping;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public class EntityScanner {

    private final ClassLoader classLoader = EntityScanner.class.getClassLoader();

    EntityScanner() {
        Objects.requireNonNull(classLoader);
    }

    public List<Class<?>> scanPackage(String packageName, boolean recursive) throws Exception {
        File baseDirectory = this.getPackageAsDirectory(packageName.replace('.', '/'));
        List<Class<?>> entityClasses = new ArrayList<>(this.getEntityClasses(baseDirectory, packageName));
        if (recursive) {
            for (File directory : this.getSubDirectories(baseDirectory)) {
                entityClasses.addAll(this.scanPackage(packageName + "." + directory.getName(), recursive));
            }
        }
        return entityClasses;
    }

    private File getPackageAsDirectory(String packageName) throws URISyntaxException {
        URL resource = classLoader.getResource(packageName);
        if (resource == null) {
            throw new IllegalArgumentException("directory named " + packageName + " not found on classpath");
        }
        return new File(resource.toURI());
    }

    private List<Class<?>> getEntityClasses(File directory, String packageName) throws Exception {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new NullPointerException("files array can not be null");
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File file : files) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                Class<?> clazz = Class.forName(this.buildFullClassName(packageName, fileName));
                EntityMapping entityMapping = clazz.getAnnotation(EntityMapping.class);
                if (entityMapping == null) {
                    continue;
                }
                classes.add(clazz);
            }
        }
        return classes;
    }

    private String buildFullClassName(String packageName, String className) {
        return packageName + "." + className.replace(".class", "");
    }

    private List<File> getSubDirectories(File baseDirectory) {
        File[] files = baseDirectory.listFiles();
        if (files == null) {
            throw new NullPointerException("files array can not be null");
        }
        List<File> directories = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                directories.add(file);
            }
        }
        return directories;
    }
}
