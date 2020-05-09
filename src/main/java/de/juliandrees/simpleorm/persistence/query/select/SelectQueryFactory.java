package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.ParameterQuery;
import de.juliandrees.simpleorm.persistence.query.ParameterizedSqlQueryElement;
import de.juliandrees.simpleorm.persistence.query.type.OrderType;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;

/**
 * Factory for sql select queries.
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
@Getter(AccessLevel.PACKAGE)
public final class SelectQueryFactory implements ParameterizedSqlQueryElement<EntityManager>, ParameterQuery {

    private SelectFrom selectFrom;
    private Where where;
    private OrderSpecifier orderBy;
    private LimitSpecifier limit;

    public SelectQueryFactory selectFrom(Class<?> entityClass) {
        this.selectFrom = new SelectFrom(entityClass);
        return this;
    }

    public SelectQueryFactory limit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit has to be greater than 0");
        }
        this.limit = new LimitSpecifier(limit);
        return this;
    }

    public SelectQueryFactory orderBy(String orderBy, OrderType orderType) {
        this.orderBy = new OrderSpecifier(orderBy, orderType);
        return this;
    }

    public SelectQueryFactory where(Where where) {
        this.where = where;
        return this;
    }

    @Override
    public String toSql(EntityManager parameter) {
        Objects.requireNonNull(selectFrom);

        EntityScheme entityScheme = parameter.getEntityScheme(selectFrom.getEntityClass());
        StringBuilder builder = new StringBuilder(selectFrom.toSql(entityScheme));
        if (where != null) {
            builder.append(where.toSql());
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
        if (where != null) {
            return where.getParameters();
        }
        return new Object[0];
    }
}
