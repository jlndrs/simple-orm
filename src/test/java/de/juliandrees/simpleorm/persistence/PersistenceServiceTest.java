package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.EntityManager;
import de.juliandrees.simpleorm.EntityManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 28.04.2020
 */
public class PersistenceServiceTest {

    private final EntityManager entityManager = EntityManagerFactory.scanPackage("de.juliandrees.simpleorm.model", false);
    private PersistenceService persistenceService;

    @Test
    public void noConstructorTest() {
        try {
            PersistenceService persistenceService = PersistenceServiceFactory.newPersistenceService(ErrorPersistenceService.class, entityManager, null);
            fail("expected a NoSuchMethodException");
        } catch (NoSuchMethodException ignored) { }
    }

    @After
    public void after() {
        if (persistenceService != null) {
            try {
                persistenceService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
