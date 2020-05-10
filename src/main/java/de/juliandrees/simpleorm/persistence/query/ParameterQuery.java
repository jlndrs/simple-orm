package de.juliandrees.simpleorm.persistence.query;

/**
 * Interface für ein parametrisiertes Sql-Query Element.
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public interface ParameterQuery {

    Object[] getParameters();

}
