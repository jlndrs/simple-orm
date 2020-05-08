package de.juliandrees.simpleorm.entity.errormodel;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EntityMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.model.BaseEntity;
import de.juliandrees.simpleorm.model.TransactionType;
import de.juliandrees.simpleorm.type.EnumType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
@EntityMapping
public class NoEnumReturnTypeEntity extends BaseEntity {

    private TransactionType transactionType;

    @EnumMapping(type = EnumType.NAME)
    @ColumnMapping
    public int getTransactionType() {
        return 0;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
