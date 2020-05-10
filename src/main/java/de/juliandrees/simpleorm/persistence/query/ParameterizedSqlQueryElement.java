package de.juliandrees.simpleorm.persistence.query;

/**
 * Interface für ein Sql-Query Element, welches einen generischen Parameter
 * benötigt, um die Query zu bauen.
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
public interface ParameterizedSqlQueryElement<T> {

    String toSql(T parameter);

}
