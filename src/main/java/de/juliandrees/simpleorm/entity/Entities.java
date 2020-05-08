package de.juliandrees.simpleorm.entity;

import java.util.ArrayList;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
public class Entities<T> extends ArrayList<T> {

    public T first() {
        return getAt(0);
    }

    public T last() {
        return getAt(size() - 1);
    }

    T getAt(int index) {
        T indexElement = null;
        try {
            indexElement = get(index);
        } catch (IndexOutOfBoundsException ex) {
            // no handling, just return null
        }
        return indexElement;
    }

}
