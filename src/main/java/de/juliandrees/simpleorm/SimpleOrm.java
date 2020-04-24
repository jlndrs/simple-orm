package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.model.Currency;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public class SimpleOrm {

    public static void main(String... args) {
        EntityManager entityManager = EntityManager.scanPackage(Currency.class, false);
    }

}
