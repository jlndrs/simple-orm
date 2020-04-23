package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.exception.MethodMappingException;
import de.juliandrees.simpleorm.type.MethodPrefix;

import java.lang.annotation.Annotation;
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
            if (!this.isMappedColumn(method)) {
                continue;
            }
            Optional<MethodPrefix> optionalPrefix = MethodPrefix.getMethodPrefix(method.getName());
            if (optionalPrefix.isEmpty()) {
                throw new MethodMappingException("Method " + method.getName() + " (" + clazz.getSimpleName() + ") has no regular prefix");
            }

            String fieldName = method.getName().substring(optionalPrefix.get().name().length() - 1);
            System.out.println(fieldName);
        }
    }

    public Method getSetter(Method method, Class<?> clazz) {
        Optional<MethodPrefix> optionalPrefix = MethodPrefix.getMethodPrefix(method.getName());
        if (optionalPrefix.isEmpty()) {
            throw new MethodMappingException("Method " + method.getName() + " (" + clazz.getSimpleName() + ") has no regular prefix");
        }
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

}
