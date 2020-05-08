package de.juliandrees.simpleorm.persistence.query;

import java.util.ArrayList;
import java.util.List;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
class WhereConcatenation implements SqlQueryElement {

    private final QueryFactory queryFactory;
    private final WhereClause firstNode;
    private WhereClause tail;

    private final List<Object> parameters = new ArrayList<>();

    public WhereConcatenation(QueryFactory queryFactory, WhereClause firstNode) {
        this.queryFactory = queryFactory;
        this.firstNode = firstNode;
        this.tail = firstNode;
    }

    public WhereConcatenation and(String column, WhereClause.EqualityComparator equalityComparator, Object value) {
        addClause(column, equalityComparator, value, WhereClause.ClauseConcatenation.AND);
        return this;
    }

    public WhereConcatenation or(String column, WhereClause.EqualityComparator equalityComparator, Object value) {
        addClause(column, equalityComparator, value, WhereClause.ClauseConcatenation.OR);
        return this;
    }

    void addClause(String column, WhereClause.EqualityComparator equalityComparator, Object value, WhereClause.ClauseConcatenation concatenation) {
        WhereClause where = new WhereClause(column, value, equalityComparator);
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
