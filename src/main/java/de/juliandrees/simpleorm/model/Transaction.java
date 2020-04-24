package de.juliandrees.simpleorm.model;

import de.juliandrees.simpleorm.annotation.ColumnMapping;
import de.juliandrees.simpleorm.annotation.EntityMapping;
import de.juliandrees.simpleorm.annotation.EnumMapping;
import de.juliandrees.simpleorm.type.EnumType;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 24.04.2020
 */
@EntityMapping
public class Transaction extends BaseEntity {

    private Currency currency;
    private double amount;
    private double price;
    private TransactionType transactionType;

    @ColumnMapping
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @ColumnMapping
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @ColumnMapping
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @EnumMapping(type = EnumType.NAME)
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
