package de.juliandrees.simpleorm;

import de.juliandrees.simpleorm.model.Currency;
import de.juliandrees.simpleorm.persistence.PersistenceService;
import de.juliandrees.simpleorm.persistence.PersistenceServiceFactory;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public class SimpleOrm {

    public static void main(String... args) {
        EntityManager entityManager = EntityManagerFactory.scanPackage(Currency.class, false);
        PersistenceService persistenceService = PersistenceServiceFactory.newInstance(entityManager);
        Currency currency = persistenceService.find(1L, Currency.class);
    }

}
