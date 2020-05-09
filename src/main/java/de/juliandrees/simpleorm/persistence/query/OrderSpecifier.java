package de.juliandrees.simpleorm.persistence.query;

import de.juliandrees.simpleorm.persistence.query.type.OrderType;
import lombok.RequiredArgsConstructor;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
@RequiredArgsConstructor
class OrderSpecifier implements SqlQueryElement {

    private final String column;
    private final OrderType orderType;

    @Override
    public String toSql() {
        return String.format("order by %s %s", column, orderType.getSql());
    }

    @Override
    public Object[] getParameters() {
        throw new UnsupportedOperationException("parameters are not supposed to be passed in the order by clause.");
    }


}
