package de.juliandrees.simpleorm.persistence.query;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
interface SqlQueryElement {

    String toSql();

    Object[] getParameters();

}
