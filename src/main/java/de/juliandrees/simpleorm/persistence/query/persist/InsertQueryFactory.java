package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityManager;
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

        StringBuilder builder = new StringBuilder(insertInto.toSql(parameter.getEntityScheme(insertInto.getEntityClass()))).append(" ");
        StringBuilder parameters = new StringBuilder();
        for (InsertValue insertValue : insertValues) {
            builder.append(insertValue.getColumn()).append(", ");
            parameters.append("?").append(", ");
            this.parameters.add(insertValue.getValue());
        }
        String parameterList = parameters.toString().substring(0, parameters.toString().length() - 2);
        return builder.toString().substring(0, builder.toString().length() - 2) + ") VALUES (" + parameterList + ");";
    }

    @Override
    public Object[] getParameters() {
        return parameters.toArray(new Object[0]);
    }
}
