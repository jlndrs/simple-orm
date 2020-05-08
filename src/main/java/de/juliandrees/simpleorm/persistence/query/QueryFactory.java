package de.juliandrees.simpleorm.persistence.query;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
@Getter(AccessLevel.PACKAGE)
public final class QueryFactory implements SqlQueryElement {

    private SelectFrom selectFrom;
    private WhereConcatenation whereConcatenation;
    private OrderSpecifier orderBy;
    private LimitSpecifier limit;

    private QueryFactory(SelectFrom selectFrom) {
        this.selectFrom = selectFrom;
    }

    public QueryFactory limit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit has to be greater than 0");
        }
        this.limit = new LimitSpecifier(limit);
        return this;
    }

    public QueryFactory orderBy(String orderBy, OrderSpecifier.OrderType orderType) {
        this.orderBy = new OrderSpecifier(orderBy, orderType);
        return this;
    }

    public WhereConcatenation where(String column, WhereClause.EqualityComparator comparator, Object value) {
        WhereClause whereClause = new WhereClause(column, value, comparator);
        this.whereConcatenation = new WhereConcatenation(this, whereClause);
        return whereConcatenation;
    }

    @Override
    public String toSql() {
        StringBuilder builder = new StringBuilder(selectFrom.toSql());
        if (whereConcatenation != null) {
            builder.append(" where ").append(whereConcatenation.toSql());
        }

        if (orderBy != null) {
            builder.append(" ").append(orderBy.toSql());
        }

        if (limit != null) {
            builder.append(" ").append(limit.toSql());
        }
        return builder.append(";").toString();
    }

    @Override
    public Object[] getParameters() {
        return whereConcatenation.getParameters(); // only clause to allow parameter passing
    }

    public static QueryFactory selectFrom(String entityName) {
        return new QueryFactory(new SelectFrom(entityName));
    }
}
