package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.QueryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public class InsertQueryFactory implements QueryFactory {

    private InsertInto insertInto;
    private final List<InsertValue> insertValues = new ArrayList<>();
    private final List<Object> parameters = new ArrayList<>();

    public InsertQueryFactory insertInto(Class<?> entityClass) {
        this.insertInto = new InsertInto(entityClass);
        return this;
    }

    public InsertQueryFactory addValue(String column, Object value) {
        insertValues.add(new InsertValue(column, value));
        return this;
    }

    @Override
    public String toSql(EntityManager parameter) {
        Objects.requireNonNull(insertInto);
        if (insertValues.size() == 0) {
            throw new IllegalArgumentException("no insert properties added");
        }

        EntityScheme scheme = parameter.getEntityScheme(insertInto.getEntityClass());
        EntityScheme.PrimaryKeyPropertyMapping primaryKey = scheme.getPrimaryKeyMapping();
        InsertValue pkValue = new InsertValue("", null);
        for (InsertValue insertValue : insertValues) {
            if (insertValue.getColumn().equalsIgnoreCase(primaryKey.getDatabaseColumn())) {
                pkValue = insertValue;
            }
        }
        insertValues.remove(pkValue);
        return buildSql(scheme, primaryKey, insertValues, pkValue);
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray(new Object[0]);
    }

    final String buildSql(EntityScheme scheme, EntityScheme.PrimaryKeyPropertyMapping primaryKey, List<InsertValue> values, InsertValue primaryKeyValue) {
        StringBuilder columns = new StringBuilder();
        StringBuilder parameters = new StringBuilder();
        for (InsertValue value : values) {
            columns.append(value.getColumn()).append(", ");
            parameters.append("?").append(", ");
            this.parameters.add(value.getValue());
        }

        String newIdQuery = "(select max(" + primaryKeyValue.getColumn() + ") + 1 from " + scheme.getEntityName() + ")";
        return String.format("%s (%s, %s) VALUES (%s, %s);", insertInto.toSql(scheme), buildString(columns),
                primaryKeyValue.getColumn(), buildString(parameters), newIdQuery);
    }

    final String buildString(StringBuilder builder) {
        if (builder.length() < 2) {
            return builder.toString();
        }
        return builder.toString().substring(0, builder.toString().length() - 2);
    }
}
