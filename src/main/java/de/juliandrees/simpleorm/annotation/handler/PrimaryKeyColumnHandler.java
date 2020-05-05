package de.juliandrees.simpleorm.annotation.handler;

import de.juliandrees.simpleorm.annotation.PrimaryKeyColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 04.05.2020
 */
public class PrimaryKeyColumnHandler extends AnnotationHandler {

    public PrimaryKeyColumnHandler() {
        super(PrimaryKeyColumn.class);
    }

    @Override
    protected void handle(Method getter, Annotation annotation) {
        Class<?> type = getter.getReturnType();
        boolean isLong = type.equals(Long.class) || type.equals(long.class);
        if (!isLong) {
            throw new IllegalArgumentException("@PrimaryKeyColumn is only applicable to Long");
        }
    }
}
