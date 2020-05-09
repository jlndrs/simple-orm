package de.juliandrees.simpleorm.persistence.query;

import de.juliandrees.simpleorm.persistence.query.type.ConcatenationType;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;

import java.util.ArrayList;
import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
public class WhereConcatenation implements SqlQueryElement {

    private final QueryFactory queryFactory;
    private final WhereClause firstNode;
    private WhereClause tail;

    private final List<Object> parameters = new ArrayList<>();

    public WhereConcatenation(QueryFactory queryFactory, WhereClause firstNode) {
        this.queryFactory = queryFactory;
        this.firstNode = firstNode;
        this.tail = firstNode;
    }

    public WhereConcatenation and(String column, EqualityType equalityType, Object value) {
        addClause(column, equalityType, value, ConcatenationType.AND);
        return this;
    }

    public WhereConcatenation or(String column, EqualityType equalityType, Object value) {
        addClause(column, equalityType, value, ConcatenationType.OR);
        return this;
    }

    void addClause(String column, EqualityType equalityType, Object value, ConcatenationType concatenation) {
        WhereClause where = new WhereClause(column, value, equalityType);
        tail.setNext(where);
        tail.setConcatenation(concatenation);
        tail = where;
    }

    public QueryFactory create() {
        return queryFactory;
    }

    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder();
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
