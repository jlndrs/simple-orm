package de.juliandrees.simpleorm.annotation;

import de.juliandrees.simpleorm.type.EnumType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@Documented
@Target(ElementType.METHOD)
public @interface EnumMapping {

    EnumType type();

}
