package de.juliandrees.simpleorm.persistence.sql;

import java.sql.SQLException;

/**
 * // TODO class description
 *
 * @author Julian Drees
 * @since 27.04.2020
 */
public interface SqlConnection {

    void onInitialize();

    boolean isConnected();

    void openConnection() throws SQLException;

    void closeConnection() throws SQLException;

}
