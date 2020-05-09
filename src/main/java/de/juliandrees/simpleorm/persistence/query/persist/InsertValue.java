package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.persistence.query.ParameterQuery;
import de.juliandrees.simpleorm.persistence.query.SqlQueryElement;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
class InsertValue implements SqlQueryElement, ParameterQuery {

    private final String column;
    private final Object value;

    InsertValue(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toSql() {
        return column;
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { value };
    }
}
