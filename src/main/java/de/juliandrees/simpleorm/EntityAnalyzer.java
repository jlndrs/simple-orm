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

            try {
                Field field = this.getField(method, clazz);
                if (field == null) {
                    throw new MethodMappingException("No field found for getter " + method.getName() + " (" + clazz.getSimpleName() + ")");
                }

                Method setter = this.getSetter(method, clazz, field.getType());
                if (setter == null) {
                    throw new MethodMappingException("No setter found for getter " + method.getName() + " (" + clazz.getSimpleName() + ")");
                }
                mappedEntity.addFieldMapping(field, setter, field.getType());
            } catch (NoSuchFieldException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Method getSetter(Method getter, Class<?> clazz, Class<?> fieldType) throws NoSuchMethodException {
        String setterName = getter.getName().replace(MethodPrefix.GET.name().toLowerCase(),
            MethodPrefix.SET.name().toLowerCase());
        return clazz.getDeclaredMethod(setterName, fieldType);
    }

    public Field getField(Method getter, Class<?> clazz) throws NoSuchFieldException {
        String fieldName = this.getFieldName(getter);
        return clazz.getDeclaredField(fieldName);
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

}
