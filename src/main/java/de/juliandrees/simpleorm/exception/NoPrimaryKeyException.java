package de.juliandrees.simpleorm.exception;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 28.04.2020
 */
public class NoPrimaryKeyException extends RuntimeException {

    public NoPrimaryKeyException(String entityName) {
        super("the entity " + entityName + " has not primary key!");
    }
}
