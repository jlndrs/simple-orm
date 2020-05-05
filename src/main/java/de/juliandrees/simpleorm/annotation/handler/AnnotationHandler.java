package de.juliandrees.simpleorm.annotation.handler;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 04.05.2020
 */
@Getter
public abstract class AnnotationHandler {

    private Class<? extends Annotation> annotationType;

    public AnnotationHandler(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    protected abstract void handle(Method getter, Annotation annotation);
}
