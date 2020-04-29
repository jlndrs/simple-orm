package de.juliandrees.simpleorm.annotation;

import de.juliandrees.simpleorm.type.EnumType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicator and Descriptor for simple-orm to map this method to a database field.
 * Only applicable on getters with a return type of type {@link Enum}.
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumMapping {

    EnumType type();

}


