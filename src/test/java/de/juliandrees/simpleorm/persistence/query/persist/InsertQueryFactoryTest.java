package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityManagerFactory;
import de.juliandrees.simpleorm.model.TestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public class InsertQueryFactoryTest {

    private EntityManager entityManager;

    @BeforeEach
    public void before() {
        entityManager = EntityManagerFactory.scanPackage(TestEntity.class, false);
        entityManager.onInitialize();
    }

    @Test
    public void onTest() {
        InsertQueryFactory insertQueryFactory = new InsertQueryFactory().insertInto(TestEntity.class).addValue("id", 1L).addValue("compatible", true);
        String sql = insertQueryFactory.toSql(entityManager);
        System.out.println(sql);
    }
    
}
