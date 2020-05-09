package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.persistence.query.SqlQueryElement;
import de.juliandrees.simpleorm.persistence.query.type.OrderType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
class OrderSpecifier implements SqlQueryElement {

    private final String column;
    private final OrderType orderType;

    OrderSpecifier(String column, OrderType orderType) {
        this.column = column;
        this.orderType = orderType;
    }

    @Override
    public String toSql() {
        return String.format("order by %s %s", column, orderType.getSql());
    }
}
