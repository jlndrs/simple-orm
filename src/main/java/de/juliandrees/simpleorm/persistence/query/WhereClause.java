package de.juliandrees.simpleorm.persistence.query;

import de.juliandrees.simpleorm.persistence.query.type.ConcatenationType;
import de.juliandrees.simpleorm.persistence.query.type.EqualityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 08.05.2020
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class WhereClause implements SqlQueryElement {

    private final String column;
    private final Object value;
    private final EqualityType equalityType;

    @Setter
    private WhereClause next;

    @Setter
    private ConcatenationType concatenation;

    @Override
    public String toSql() {
        return String.format("%s %s ?", column, equalityType.getSql());
    }

    @Override
    public Object[] getParameters() {
        throw new UnsupportedOperationException("this method is not supported, please use getValue() instead");
    }

}
