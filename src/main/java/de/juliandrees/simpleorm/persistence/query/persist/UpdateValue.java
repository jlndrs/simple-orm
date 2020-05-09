package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.persistence.query.ParameterQuery;
import de.juliandrees.simpleorm.persistence.query.SqlQueryElement;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public class UpdateValue implements SqlQueryElement, ParameterQuery {

    private final String column;
    private final Object value;

    public UpdateValue(String column, Object value) {
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
        return String.format("%s = ?", column);
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { value };
    }
}
