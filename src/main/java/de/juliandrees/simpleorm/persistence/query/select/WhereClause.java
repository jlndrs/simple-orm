package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.persistence.query.SqlQueryElement;
import de.juliandrees.simpleorm.persistence.query.type.ConcatenationType;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
class WhereClause implements SqlQueryElement {

    private final Object value;
    private final String column;
    private final EqualityType equalityType;

    private WhereClause next;
    private ConcatenationType concatenation;

    WhereClause(String column, Object value, EqualityType equalityType) {
        this.column = column;
        this.value = value;
        this.equalityType = equalityType;
    }

    public WhereClause getNext() {
        return next;
    }

    public void setNext(WhereClause next) {
        this.next = next;
    }

    public ConcatenationType getConcatenation() {
        return concatenation;
    }

    public void setConcatenation(ConcatenationType concatenation) {
        this.concatenation = concatenation;
    }

    @Override
    public String toSql() {
        if (value instanceof String) {
            return String.format("lower(" + column + ") %s ?", equalityType.getSql());
        }
        return String.format(column + " %s ?", equalityType.getSql());
    }

    public Object getValue() {
        if (value instanceof String) {
            return ((String) value).toLowerCase();
        }
        return value;
    }
}
