package de.juliandrees.simpleorm.exception;

/**
 * Wird geworfen, wenn ein Fehler beim mappen einer Methode auftritt.
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public class MethodMappingException extends RuntimeException {

    public MethodMappingException(String message) {
        super(message);
    }
}
