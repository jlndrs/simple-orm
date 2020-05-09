package de.juliandrees.simpleorm.persistence.query;

import de.juliandrees.simpleorm.persistence.query.type.EqualityType;
import de.juliandrees.simpleorm.persistence.query.type.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
public class QueryFactoryTest {

    private QueryFactory queryFactory;

    @BeforeEach
    public void before() {
        this.queryFactory = QueryFactory.selectFrom("testEntity");
    }

    @Test
    public void onLimitTest() {
        queryFactory.limit(10);
        assertEquals("limit 10", queryFactory.getLimit().toSql());
    }

    @Test
    public void onOrderByTest() {
        queryFactory.orderBy("id", OrderType.ASCENDING);
        assertEquals("order by id asc", queryFactory.getOrderBy().toSql());
    }

    @Test
    public void fullQueryFactoryTest() {
        QueryFactory factory = queryFactory.where("name", EqualityType.EQUALS, "jlndrs").or("name", EqualityType.LIKE, "%jlndrs").create().limit(10).orderBy("id", OrderType.ASCENDING);

        assertEquals("select * from testEntity where name = ? or name like ? order by id asc limit 10;", factory.toSql());
        assertEquals(2, factory.getParameters().length);
    }

}
