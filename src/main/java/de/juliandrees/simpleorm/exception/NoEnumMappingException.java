package de.juliandrees.simpleorm.exception;

import java.lang.reflect.Field;

/**
 * Wird geworfen, wenn versucht wird, ein Feld mit{@link de.juliandrees.simpleorm.annotation.EnumMapping} zu mappen.
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public class NoEnumMappingException extends RuntimeException {

    public NoEnumMappingException(Field field) {
        super("Mapping a non enum-field with @EnumMapping is not allowed! (" + field.getName() + ")");
    }
}
