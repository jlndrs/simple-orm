package de.juliandrees.simpleorm.model;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.PrimaryKeyColumn;
import de.juliandrees.simpleorm.annotation.SuperclassMapping;

import java.io.Serializable;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
@SuperclassMapping
public abstract class BaseEntity implements Serializable {

    private Long id;

    @PrimaryKeyColumn
    @ColumnMapping
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
