package de.juliandrees.simpleorm.annotation.handler;

import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.exception.WrongAnnotationUsageException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 07.05.2020
 */
public class EnumMappingHandler extends AnnotationHandler<Method> {

    public EnumMappingHandler() {
        super(EnumMapping.class);
    }

    @Override
    protected void handle(Method getter, Annotation annotation) throws WrongAnnotationUsageException {
        if (!getter.getReturnType().isEnum()) {
            throw new WrongAnnotationUsageException("return type is not an enum! (" + getter.getName() + ")");
        }
    }
}
