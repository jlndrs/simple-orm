package de.juliandrees.simpleorm.scanner;

import de.juliandrees.simpleorm.exception.ClasspathScannerException;

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
 * @since 29.04.2020
 */
public abstract class AbstractScanner {

    private final ClassLoader classLoader = AbstractScanner.class.getClassLoader();

    public AbstractScanner() {
        Objects.requireNonNull(classLoader);
    }

    protected abstract boolean isSuitable(Class<?> clazz);

    private List<Class<?>> getClasses(File directory, String packageName) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new NullPointerException("files array can not be null");
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File file : files) {
            if (!file.isDirectory()) {
                String fileName = file.getName();
                Class<?> clazz = Class.forName(buildFullClassName(packageName, fileName));
                if (!isSuitable(clazz)) {
                    continue;
                }
                classes.add(clazz);
            }
        }
        return classes;
    }

    public List<Class<?>> scanPackage(String packageName, boolean recursive) throws ClasspathScannerException {
        try {
            File baseDirectory = this.getPackageAsDirectory(packageName.replace('.', '/'));
            List<Class<?>> entityClasses = new ArrayList<>(getClasses(baseDirectory, packageName));
            if (recursive) {
                for (File directory : this.getSubDirectories(baseDirectory)) {
                    entityClasses.addAll(this.scanPackage(packageName + "." + directory.getName(), recursive));
                }
            }
            return entityClasses;
        } catch (URISyntaxException | ClassNotFoundException ex) {
            throw new ClasspathScannerException(ex);
        }
    }

    private File getPackageAsDirectory(String packageName) throws URISyntaxException {
        URL resource = classLoader.getResource(packageName);
        if (resource == null) {
            throw new IllegalArgumentException("directory named " + packageName + " not found on classpath");
        }
        return new File(resource.toURI());
    }

    protected String buildFullClassName(String packageName, String className) {
        return packageName + "." + className.replace(".class", "");
    }

    protected List<File> getSubDirectories(File baseDirectory) {
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
