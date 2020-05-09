package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.model.TestEntity;
import de.juliandrees.simpleorm.persistence.query.select.SelectQueryFactory;
import de.juliandrees.simpleorm.persistence.query.select.Where;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;
import de.juliandrees.simpleorm.persistence.query.type.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
public class SelectQueryFactoryTest {

    private SelectQueryFactory selectQueryFactory;
    private EntityManager entityManager;

    @BeforeEach
    public void before() {
        this.selectQueryFactory = new SelectQueryFactory().selectFrom(TestEntity.class);
        this.entityManager = mock(EntityManager.class);
        when(entityManager.getEntityScheme(TestEntity.class)).thenReturn(new EntityScheme(TestEntity.class, "testEntity"));
    }

    @Test
    public void onLimitTest() {
        selectQueryFactory.limit(10);
        assertEquals("limit 10", selectQueryFactory.getLimit().toSql());
    }

    @Test
    public void onOrderByTest() {
        selectQueryFactory.orderBy("id", OrderType.ASCENDING);
        assertEquals("order by id asc", selectQueryFactory.getOrderBy().toSql());
    }

    @Test
    public void fullQueryFactoryTest() {
        SelectQueryFactory factory = selectQueryFactory.where(Where.of("name", "jlndrs", EqualityType.EQUALS).or("name", "%jlndrs", EqualityType.LIKE)).limit(10).orderBy("id", OrderType.ASCENDING);

        assertEquals("select * from testEntity where lower(name) = ? or lower(name) like ? order by id asc limit 10;", factory.toSql(entityManager));
        assertEquals(2, factory.getParameters().length);
    }

}
