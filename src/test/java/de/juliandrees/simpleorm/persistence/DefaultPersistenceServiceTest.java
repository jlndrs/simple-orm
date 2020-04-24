package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.EntityManager;
import de.juliandrees.simpleorm.model.TestEntity;
import org.junit.Before;
import org.junit.Test;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
public class DefaultPersistenceServiceTest {

    private EntityManager entityManager = EntityManager.scanPackage("de.juliandrees.simpleorm.model", true);
    private DefaultPersistenceService persistenceService;

    @Before
    public void before() {
        persistenceService = new DefaultPersistenceService(entityManager);
    }

    @Test
    public void findTest() {
        TestEntity testEntity = persistenceService.find(1L, TestEntity.class);
        System.out.println(testEntity);
    }

}
