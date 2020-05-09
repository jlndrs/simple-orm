package de.juliandrees.simpleorm.persistence.query;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public interface ParameterizedSqlQueryElement<T> {

    String toSql(T parameter);

}
