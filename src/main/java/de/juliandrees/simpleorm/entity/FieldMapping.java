package de.juliandrees.simpleorm.entity;

import de.juliandrees.simpleorm.type.PropertyType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Ein
 *
 * @author Julian Drees
 * @since 13.05.2020
 */
public class FieldMapping implements Serializable {

    private final Class<?> fieldType;
    private final String fieldName;
    private final Field field;
    private final Method getter;
    private final Method setter;
    private final PropertyType propertyType;

    public FieldMapping(Field field, Method getter, Method setter, PropertyType propertyType) {
        this.fieldType = field.getType();
        this.fieldName = field.getName();
        this.field = field;
        this.getter = getter;
        this.setter = setter;
        this.propertyType = propertyType;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Field getField() {
        return field;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }
}
