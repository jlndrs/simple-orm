package de.juliandrees.simpleorm.persistence.query.persist;

import de.juliandrees.simpleorm.entity.EntityScheme;
import de.juliandrees.simpleorm.persistence.query.ParameterizedSqlQueryElement;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 10.05.2020
 */
public class UpdateTable implements ParameterizedSqlQueryElement<EntityScheme> {

    private Class<?> entityClass;

    UpdateTable(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public String toSql(EntityScheme parameter) {
        return "update " + parameter.getEntityName() + " set ";
    }
}
