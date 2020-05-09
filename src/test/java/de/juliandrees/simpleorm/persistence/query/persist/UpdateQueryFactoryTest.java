package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.model.TestEntity;
import de.juliandrees.simpleorm.persistence.query.select.Where;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 10.05.2020
 */
public class UpdateQueryFactoryTest {

    private EntityManager entityManager;

    @BeforeEach
    public void before() {
        entityManager = mock(EntityManager.class);
        when(entityManager.getEntityScheme(TestEntity.class)).thenReturn(new EntityScheme(TestEntity.class, "testEntity"));
    }

    @Test
    public void onTest() {
        UpdateQueryFactory updateQueryFactory = new UpdateQueryFactory().updateTable(TestEntity.class).setValue("shortcut", "BTC").setValue("description", "descp").where(Where.of("id", 1L, EqualityType.EQUALS));
        System.out.println(updateQueryFactory.toSql(entityManager));
    }

}
