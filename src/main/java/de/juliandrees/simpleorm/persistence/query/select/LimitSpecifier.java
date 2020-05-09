package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.persistence.query.SqlQueryElement;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
class LimitSpecifier implements SqlQueryElement {

    private final int limit;

    LimitSpecifier(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toSql() {
        return "limit " + limit;
    }
}
