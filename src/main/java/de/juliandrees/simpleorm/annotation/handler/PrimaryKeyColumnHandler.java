package de.juliandrees.simpleorm.annotation.handler;

import de.juliandrees.simpleorm.annotation.PrimaryKeyColumn;
import de.juliandrees.simpleorm.exception.WrongAnnotationUsageException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 04.05.2020
 */
public class PrimaryKeyColumnHandler extends AnnotationHandler<Method> {

    public PrimaryKeyColumnHandler() {
        super(PrimaryKeyColumn.class);
    }

    @Override
    protected void handle(Method getter, Annotation annotation) throws WrongAnnotationUsageException {
        Class<?> type = getter.getReturnType();
        boolean isLong = type.equals(Long.class) || type.equals(long.class);
        if (!isLong) {
            throw new WrongAnnotationUsageException("@PrimaryKeyColumn is only applicable to Long");
        }
    }
}
