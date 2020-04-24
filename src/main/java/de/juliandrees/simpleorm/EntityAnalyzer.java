package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EntityMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.annotation.PrimaryKeyColumn;
import de.juliandrees.simpleorm.annotation.SuperclassMapping;
import de.juliandrees.simpleorm.exception.MethodMappingException;
import de.juliandrees.simpleorm.type.MethodPrefix;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
class EntityAnalyzer {

    @Getter
    private List<MappedEntity> mappedEntities = new ArrayList<>();

    private HashMap<Class<?>, List<Class<?>>> classMapping = new HashMap<>();

    public EntityAnalyzer() { }

    public void analyzeClass(Class<?> clazz) {
        classMapping.put(clazz, this.getClassHierarchy(clazz));
        MappedEntity mappedEntity = new MappedEntity(clazz, this.determineEntityName(clazz));
        for (Method method : clazz.getMethods()) {
            if (!method.getName().toLowerCase().startsWith(MethodPrefix.GET.name().toLowerCase()) ||
                !this.isMappedColumn(method)) {
                continue;
            }

            Field field = this.getField(method, clazz);
            PrimaryKeyColumn pkAnnotation = field.getAnnotation(PrimaryKeyColumn.class);

            Method setter = this.getSetter(method, clazz);
            mappedEntity.addFieldMapping(field, setter, field.getType(), pkAnnotation != null);
        }
        mappedEntities.add(mappedEntity);
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
    public Method getSetter(Method getter, Class<?> clazz) {
        Optional<Method> optionalGetter = getMethod(this.getFieldName(getter), MethodPrefix.SET, clazz);
        if (optionalGetter.isEmpty()) {
            throw new MethodMappingException("No setter found for getter " + getter.getName() + " (" + clazz.getSimpleName() + ")");
        }
        return optionalGetter.get();
    }

    public Field getField(Method getter, Class<?> clazz) {
        String fieldName = this.getFieldName(getter);
        List<Class<?>> classes = classMapping.get(clazz);

        Field field = null;
        for (Class<?> mappingClass : classes) {
            try {
                field = mappingClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) { }
        }

        if (field == null) {
            throw new MethodMappingException("No field found for getter " + getter.getName() + " (" + clazz.getSimpleName() + ")");
        }
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

    public String determineEntityName(Class<?> clazz) {
        EntityMapping annotation = clazz.getAnnotation(EntityMapping.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Given class has no entity mapping");
        }
        String entityName = annotation.value();
        if (StringUtils.isBlank(entityName)) {
            entityName = clazz.getSimpleName();
        }
        return entityName;
    }

    public List<Class<?>> getClassHierarchy(Class<?> entityClass) {
        Objects.requireNonNull(entityClass);
        List<Class<?>> classes = new ArrayList<>();
        classes.add(entityClass);
        Class<?> superclass = entityClass;
        while ((superclass = superclass.getSuperclass()) != null) {
            if (superclass.getAnnotation(EntityMapping.class) != null || superclass.getAnnotation(SuperclassMapping.class) != null) {
                classes.add(superclass);
            } else {
                break;
            }
        }
        return classes;
    }

}
