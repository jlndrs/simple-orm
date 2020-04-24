package de.juliandrees.simpleorm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityMapping {

    String value() default "";

}
