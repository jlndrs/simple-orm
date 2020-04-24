package de.juliandrees.simpleorm.model;

import de.juliandrees.simpleorm.annotation.EntityMapping;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@EntityMapping("testEntity")
public class TestEntity extends BaseEntity {

    private boolean compatible;

    public boolean isCompatible() {
        return compatible;
    }

    public void setCompatible(boolean compatible) {
        this.compatible = compatible;
    }
}
