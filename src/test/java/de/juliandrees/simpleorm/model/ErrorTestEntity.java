package de.juliandrees.simpleorm.model;

import de.juliandrees.simpleorm.annotation.EntityMapping;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@EntityMapping("errorTestEntity")
public class ErrorTestEntity {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
