package de.juliandrees.simpleorm.model;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EntityMapping;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
@EntityMapping
public class Currency extends BaseEntity {

    private String name;
    private String shortcut;

    @ColumnMapping
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ColumnMapping
    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
}
