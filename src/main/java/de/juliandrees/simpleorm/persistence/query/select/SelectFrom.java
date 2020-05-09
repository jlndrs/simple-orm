package de.juliandrees.simpleorm.persistence.query.select;

import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.ParameterizedSqlQueryElement;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
class SelectFrom implements ParameterizedSqlQueryElement<EntityScheme> {

    private final Class<?> entityClass;

    SelectFrom(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public String toSql(EntityScheme parameter) {
        return "select * from " + parameter.getEntityName();
    }
}
