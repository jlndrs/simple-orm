package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.model.TestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        this.entityManager = mock(EntityManager.class);
        when(entityManager.getEntityScheme(TestEntity.class)).thenReturn(new EntityScheme(TestEntity.class, "testEntity"));
    }

    @Test
    public void onTest() {
        InsertQueryFactory insertQueryFactory = new InsertQueryFactory().insertInto(TestEntity.class).addValue("id", 1L);
        String sql = insertQueryFactory.toSql(entityManager);
        System.out.println(sql);
    }
    
}
