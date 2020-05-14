package de.juliandrees.simpleorm.entity.property;

import de.juliandrees.simpleorm.entity.FieldMapping;
import de.juliandrees.simpleorm.persistence.EntityPersistence;

import java.lang.annotation.Annotation;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
public abstract class PropertyMapping {

    private final FieldMapping fieldMapping;
    private final Annotation[] annotations;

    public PropertyMapping(FieldMapping fieldMapping) {
        this.fieldMapping = fieldMapping;
        this.annotations = fieldMapping.getGetter().getDeclaredAnnotations();
    }

    public FieldMapping getFieldMapping() {
        return fieldMapping;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public abstract void onInitialize();

    public Object getStorableValue(Object value, EntityPersistence persistence) {
        return value;
    }

    public Object getEntityValue(Object value, EntityPersistence persistence) {
        return value;
    }

    public Class<?> getFieldType() {
        return fieldMapping.getFieldType();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equalsIgnoreCase(annotationClass.getName())) {
                return (T) annotation;
            }
        }
        return null;
    }
}
