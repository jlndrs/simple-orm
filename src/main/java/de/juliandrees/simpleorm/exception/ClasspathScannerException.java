package de.juliandrees.simpleorm.exception;

/**
 * Wrapper-exception for exceptions thrown during a classpath scan.
 *
 * @author Julian Drees
 * @since 29.04.2020
 */
public class ClasspathScannerException extends RuntimeException {

    public ClasspathScannerException(Throwable cause) {
        super(cause);
    }
}
