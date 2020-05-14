package de.juliandrees.simpleorm.model;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EntityMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.type.EnumType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 23.04.2020
 */
@EntityMapping("testEntity")
public class TestEntity extends BaseEntity {

    private boolean compatible;
    private TransactionType transactionType;

    @ColumnMapping
    public boolean isCompatible() {
        return compatible;
    }

    public void setCompatible(boolean compatible) {
        this.compatible = compatible;
    }

    @ColumnMapping("transactionType")
    @EnumMapping(type = EnumType.NAME, enumClass = TransactionType.class)
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
