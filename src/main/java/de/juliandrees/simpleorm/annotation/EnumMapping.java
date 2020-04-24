package de.juliandrees.simpleorm.annotation;

import de.juliandrees.simpleorm.type.EnumType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Indicator and Descriptor for simple-orm to map this method to a database field.
 * Only applicable on getters with a return type of type {@link Enum}.
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Documented
@Target(ElementType.METHOD)
public @interface EnumMapping {

    EnumType type();

}