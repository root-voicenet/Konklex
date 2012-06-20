package org.apollo.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.CachedRowSet;

import snaq.db.ConnectionPool;

import com.mysql.jdbc.Driver;
import com.sun.rowset.CachedRowSetImpl;

/**
 * A utility class which contains mysql-related methods.
 * @author Steve
 */
public final class MysqlUtil {

    /**
     * The driver.
     */
    private static Driver driver;

    /**
     * The logger for this class.
     */
    private static final Logger logger = Logger.getLogger(MysqlUtil.class.getName());

    /**
     * The pool of mysql connections.
     */
    private static ConnectionPool connections;

    /**
     * Opens the mysql connection.
     * @throws SQLException If the server could not connect.
     */
    public static void open() {
	if (driver == null)
	    try {
		driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
		connections = new ConnectionPool("konklex", 3, 5, 10, 110, "jdbc:mysql://", "", ""); // Edit
	    } catch (final Exception e) {
		logger.log(Level.SEVERE, "Error while starting the Jdbs instance.", e);
	    }
    }

    /**
     * Executes a query for the mysql database.
     * @param query The query to execute.
     * @return The cached result.
     * @throws SQLException May a exception occur, it will return.
     */
    public static CachedRowSet query(String query) throws SQLException {
	Connection connection = null;
	final CachedRowSet rowset = new CachedRowSetImpl();
	try {
	    connection = connections.getConnection();
	    if (connection != null) {
		final Statement stmt = connection.createStatement();
		if (query.toLowerCase().startsWith("select")) {
		    rowset.populate(stmt.executeQuery(query));
		    return rowset;
		} else
		    stmt.executeUpdate(query);
	    }
	} catch (final SQLException e) {
	} finally {
	    try {
		connection.close();
	    } catch (final SQLException e) {
	    }
	}
	return rowset;
    }
}