package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.ParameterizedSqlQueryElement;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
class InsertInto implements ParameterizedSqlQueryElement<EntityScheme> {

    private final Class<?> entityClass;

    InsertInto(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public String toSql(EntityScheme parameter) {
        return "insert into " + parameter.getEntityName() + "";
    }
}
