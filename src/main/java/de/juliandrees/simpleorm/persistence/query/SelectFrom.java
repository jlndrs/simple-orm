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
class SelectFrom implements SqlQueryElement {

    private final String entityName;

    @Override
    public String toSql() {
        return "select * from " + entityName;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
