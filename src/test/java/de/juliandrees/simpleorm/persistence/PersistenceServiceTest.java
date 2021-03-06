package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityManagerFactory;
import de.juliandrees.simpleorm.model.Currency;
import de.juliandrees.simpleorm.model.Transaction;
import de.juliandrees.simpleorm.model.TransactionType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 28.04.2020
 */
public class PersistenceServiceTest {

    private static EntityManager entityManager;
    private static PersistenceService persistenceService;

    @BeforeAll
    public static void start() {
        entityManager = EntityManagerFactory.scanPackage("de.juliandrees.simpleorm.model", false);
        persistenceService = PersistenceServiceFactory.newInstance(entityManager);
    }

    @AfterAll
    public static void stop() {
        try {
            persistenceService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noConstructorTest() {
        try {
            PersistenceService persistenceService = PersistenceServiceFactory.newPersistenceService(ErrorPersistenceService.class, entityManager, null);
            fail("expected a NoSuchMethodException");
        } catch (NoSuchMethodException ignored) { }
    }

    @Test
    public void constructorTest() throws Exception {
        PersistenceServiceFactory.newPersistenceService(DefaultPersistenceService.class, entityManager, null);
    }

    @Test
    public void findEntityTest() {
        Currency currency = persistenceService.find(1L, Currency.class);
        assertNotNull(currency);
    }

    @Test
    public void findNonExistingEntityTest() {
        Currency currency = persistenceService.find(Long.MAX_VALUE, Currency.class);
        assertNull(currency);
    }

    @Test
    public void loadAllTest() {
        List<Currency> currencies = persistenceService.loadAll(Currency.class);
        assertTrue(currencies.size() > 0);
    }

    @Test
    public void findByColumnTest() {
        Currency currency = persistenceService.find("shortcut", "test", Currency.class);
        assertNotNull(currency);
    }

    @Test
    public void persistEnumTest() {
        Transaction transaction = new Transaction();
        transaction.setAmount(1.0);
        transaction.setPrice(8320.5);
        transaction.setTransactionType(TransactionType.SELL);
        persistenceService.persist(transaction);
    }

    @Test
    public void findEntityReferenceTest() {
        Transaction transaction = persistenceService.find(1L, Transaction.class);
        assertNotNull(transaction);
    }

}
