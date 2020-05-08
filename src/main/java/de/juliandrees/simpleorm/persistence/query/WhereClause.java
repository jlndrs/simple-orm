package de.juliandrees.simpleorm.persistence.query;

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
    private final EqualityComparator equalityComparator;

    @Setter
    private WhereClause next;

    @Setter
    private ClauseConcatenation concatenation;

    @Override
    public String toSql() {
        return String.format("%s %s ?", column, equalityComparator.getSql());
    }

    @Override
    public Object[] getParameters() {
        throw new UnsupportedOperationException("this method is not supported, please use getValue() instead");
    }

    @Getter
    public enum EqualityComparator {

        EQUALS("equals ="),
        LIKE("like");

        private final String sql;

        EqualityComparator(String sql) {
            this.sql = sql;
        }
    }

    @Getter
    public enum ClauseConcatenation {

        AND("and"),
        OR("or");

        private final String sql;

        ClauseConcatenation(String sql) {
            this.sql = sql;
        }

    }

}
