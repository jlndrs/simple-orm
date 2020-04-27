package de.juliandrees.simpleorm.persistence;

import de.juliandrees.simpleorm.type.QueryType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
public interface QueryBuilder {

    String buildQuery();

    QueryType getQueryType();

}
