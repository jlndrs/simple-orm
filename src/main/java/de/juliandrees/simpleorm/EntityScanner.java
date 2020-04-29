package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.annotation.EntityMapping;
import de.juliandrees.simpleorm.scanner.AbstractScanner;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
class EntityScanner extends AbstractScanner {

    @Override
    protected boolean isSuitable(Class<?> clazz) {
        return clazz.getAnnotation(EntityMapping.class) != null;
    }
}
