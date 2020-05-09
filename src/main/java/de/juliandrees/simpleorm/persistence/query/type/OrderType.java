package de.juliandrees.simpleorm.persistence.query.type;

import lombok.Getter;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
@Getter
public enum OrderType {

    ASCENDING("asc"),
    DESCENDING("desc");

    private final String sql;

    OrderType(final String sql) {
        this.sql = sql;
    }

}