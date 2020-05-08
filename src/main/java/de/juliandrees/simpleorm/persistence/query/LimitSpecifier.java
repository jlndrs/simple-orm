package de.juliandrees.simpleorm.persistence.query;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class LimitSpecifier implements SqlQueryElement {

    private final int limit;

    @Override
    public String toSql() {
        return "limit " + limit;
    }

    @Override
    public Object[] getParameters() {
        throw new UnsupportedOperationException("the limit query element is not supposed to contain parameters");
    }
}
