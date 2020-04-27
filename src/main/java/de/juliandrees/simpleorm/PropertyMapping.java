package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.type.PropertyType;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
@Getter
public class PropertyMapping {

    private final Class<?> fieldType;
    private final String fieldName;
    private final Method getter;
    private final Method setter;
    private final PropertyType propertyType;

    PropertyMapping(Class<?> fieldType, String fieldName, Method getter, Method setter, PropertyType propertyType) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.getter = getter;
        this.setter = setter;
        this.propertyType = propertyType;
    }

}
