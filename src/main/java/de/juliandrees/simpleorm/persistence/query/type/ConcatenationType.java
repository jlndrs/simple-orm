package de.juliandrees.simpleorm.persistence.query.type;

import lombok.Getter;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
@Getter
public enum ConcatenationType {

    AND("and"),
    OR("or");

    private final String sql;

    ConcatenationType(String sql) {
        this.sql = sql;
    }

}