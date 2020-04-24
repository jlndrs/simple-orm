package de.juliandrees.simpleorm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicator for primary key mapping.
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKeyColumn {
}
