package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.persistence.query.ParameterQuery;
import de.juliandrees.simpleorm.persistence.query.SqlQueryElement;
import de.juliandrees.simpleorm.persistence.query.type.ConcatenationType;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;

import java.util.ArrayList;
import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public class Where implements SqlQueryElement, ParameterQuery {

    private final WhereClause firstNode;
    private WhereClause tail;

    private final List<Object> parameters = new ArrayList<>();

    Where(WhereClause firstNode) {
        this.firstNode = firstNode;
        this.tail = firstNode;
    }

    public static Where of(String column, Object value, EqualityType equalityType) {
        WhereClause whereClause = new WhereClause(column, value, equalityType);
        return new Where(whereClause);
    }

    public Where and(String column, Object value, EqualityType equalityType) {
        concat(column, value, equalityType, ConcatenationType.AND);
        return this;
    }

    public Where or(String column, Object value, EqualityType equalityType) {
        concat(column, value, equalityType, ConcatenationType.OR);
        return this;
    }

    void concat(String column, Object value, EqualityType equalityType, ConcatenationType concatenationType) {
        WhereClause whereClause = new WhereClause(column, value, equalityType);
        tail.setNext(whereClause);
        tail.setConcatenation(concatenationType);
        tail = whereClause;
    }

    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder(" where ");
        WhereClause node = firstNode;
        do {
            builder.append(node.toSql());
            parameters.add(node.getValue());
            if (node.getConcatenation() != null) {
                builder.append(" ").append(node.getConcatenation().getSql()).append(" ");
            }
        } while ((node = node.getNext()) != null);
        return builder.toString();
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray(new Object[0]);
    }

}
