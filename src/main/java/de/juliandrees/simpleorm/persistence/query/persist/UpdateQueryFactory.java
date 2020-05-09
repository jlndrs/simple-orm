package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityManager;
import de.juliandrees.simpleorm.persistence.query.QueryFactory;
import de.juliandrees.simpleorm.persistence.query.select.Where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public class UpdateQueryFactory implements QueryFactory {

    private UpdateTable updateTable;
    private final List<UpdateValue> updateValues = new ArrayList<>();
    private Where where;

    public UpdateQueryFactory updateTable(Class<?> entityClass) {
        this.updateTable = new UpdateTable(entityClass);
        return this;
    }

    public UpdateQueryFactory setValue(String column, Object value) {
        this.updateValues.add(new UpdateValue(column, value));
        return this;
    }

    public UpdateQueryFactory where(Where where) {
        this.where = where;
        return this;
    }

    @Override
    public String toSql(EntityManager parameter) {
        Objects.requireNonNull(updateTable);
        Objects.requireNonNull(where);
        if (updateValues.size() == 0) {
            throw new IllegalArgumentException("no update properties added");
        }
        StringBuilder builder = new StringBuilder(updateTable.toSql(parameter.getEntityScheme(updateTable.getEntityClass())));
        for (UpdateValue value : updateValues) {
            builder.append(value.toSql()).append(", ");
        }
        String sql = builder.toString().substring(0, builder.toString().length() - 2);
        sql += where.toSql() + ";";
        return sql;
    }

    @Override
    public Object[] getParameters() {
        List<Object> updatedValues = updateValues.stream().map(UpdateValue::getValue).collect(Collectors.toList());
        List<Object> whereValues = Arrays.asList(where.getParameters());
        updatedValues.addAll(whereValues);
        return updatedValues.toArray(Object[]::new);
    }
}
