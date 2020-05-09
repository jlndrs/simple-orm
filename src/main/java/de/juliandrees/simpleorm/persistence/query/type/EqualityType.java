package de.juliandrees.simpleorm.persistence.query.type;

import lombok.Getter;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 09.05.2020
 */
@Getter
public enum EqualityType {

    EQUALS("="),
    LIKE("like");

    private final String sql;

    EqualityType(String sql) {
        this.sql = sql;
    }
}
