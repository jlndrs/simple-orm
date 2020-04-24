package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.exception.MethodMappingException;
import de.juliandrees.simpleorm.type.MethodPrefix;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
class EntityAnalyzer {

    private List<MappedEntity> mappedEntities = new ArrayList<>();

    public EntityAnalyzer() { }

    public void analyzeClass(Class<?> clazz) {
        MappedEntity mappedEntity = new MappedEntity(clazz);
        for (Method method : clazz.getMethods()) {
            if (!method.getName().toLowerCase().startsWith(MethodPrefix.GET.name().toLowerCase()) ||
                !this.isMappedColumn(method)) {
                continue;
            }

            Field field = this.getField(method, clazz);
            if (field == null) {
                throw new MethodMappingException("No field found for getter " + method.getName() + " (" + clazz.getSimpleName() + ")");
            }

            Optional<Method> setter = this.getSetter(method, clazz);
            if (setter.isEmpty()) {
                throw new MethodMappingException("No setter found for getter " + method.getName() + " (" + clazz.getSimpleName() + ")");
            }
            mappedEntity.addFieldMapping(field, setter.get(), field.getType());
        }
    }

    /**
     * Nimmt die set-Methode, die als erste passende in der Methodenliste angeführt wird.
     * Methoden überladen funktioniert bei diesem Prinzip nicht und sollte
     * bei ORMs auf nicht angewendet werden.
     *
     * @param getter die passende get-Methode
     * @param clazz die Klasse, aus der die Methode geladen werden soll
     * @return ein entsprechender Getter
     */
    public Optional<Method> getSetter(Method getter, Class<?> clazz) {
        return getMethod(this.getFieldName(getter), MethodPrefix.SET, clazz);
    }

    public Field getField(Method getter, Class<?> clazz) {
        String fieldName = this.getFieldName(getter);
        Class<?> check = clazz;
        Field field = null;
        do {
            try {
                field = check.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) { }
        } while (field == null && (check = check.getSuperclass()) != null);
        return field;
    }

    public MappedEntity getMappedEntity(Class<?> entityClass) {
        return mappedEntities.stream().filter(mappedEntity -> mappedEntity.getEntityClass().equals(entityClass)).findFirst().orElseThrow();
    }

    public boolean isMappedColumn(Method method) {
        for (Class<? extends Annotation> annotationClass : getAnnotations()) {
            Annotation annotation = method.getAnnotation(annotationClass);
            if (annotation != null) {
                return true;
            }
        }
        return false;
    }

    private List<Class<? extends Annotation>> getAnnotations() {
        return Arrays.asList(ColumnMapping.class, EnumMapping.class);
    }

    public String getFieldName(Method getter) {
        if (!getter.getName().toLowerCase().startsWith(MethodPrefix.GET.name().toLowerCase())) {
            throw new IllegalArgumentException("requires getter, but another method type is given.");
        }

        final String getterName = getter.getName();
        String fieldName = getterName.substring(MethodPrefix.GET.name().length());
        return Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    public Optional<Method> getMethod(String fieldName, MethodPrefix methodPrefix, Class<?> clazz) {
        final String methodName = methodPrefix.name().toLowerCase() + fieldName; // case-sensitive doesnt matter
        for (Method method : clazz.getMethods()) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

}
