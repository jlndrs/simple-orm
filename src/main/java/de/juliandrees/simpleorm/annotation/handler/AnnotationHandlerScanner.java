package de.juliandrees.simpleorm.annotation.handler;

import de.juliandrees.simpleorm.scanner.AbstractScanner;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 04.05.2020
 */
public class AnnotationHandlerScanner extends AbstractScanner {

    @Override
    protected boolean isSuitable(Class<?> clazz) {
        return AnnotationHandler.class.isAssignableFrom(clazz) && !clazz.equals(AnnotationHandler.class);
    }
}
