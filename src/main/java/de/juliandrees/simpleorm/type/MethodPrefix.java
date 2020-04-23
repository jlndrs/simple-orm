package de.juliandrees.simpleorm.type;

import java.util.Optional;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
public enum MethodPrefix {

    GET,
    SET,
    IS;

    public static Optional<MethodPrefix> getMethodPrefix(String methodName) {
        for (MethodPrefix prefix : values()) {
            if (methodName.toLowerCase().startsWith(prefix.name().toLowerCase())) {
                return Optional.of(prefix);
            }
        }
        return Optional.empty();
    }

}
