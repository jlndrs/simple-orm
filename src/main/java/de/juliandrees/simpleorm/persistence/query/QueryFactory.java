package de.juliandrees.simpleorm.persistence.query;

import de.juliandrees.simpleorm.entity.EntityManager;

/**
 * Interface für eine Factory, die eine Sql Query bereitstellt.
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public interface QueryFactory extends ParameterizedSqlQueryElement<EntityManager>, ParameterQuery {

}
