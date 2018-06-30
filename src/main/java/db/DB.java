package db;

import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static Connection connection = null;
    private static PreparedStatement astLdapUsersStatement = null;
    private static Statement statement = null;

    /**
     * Constructor that initializes statements and creates DB connection
     * @param ip - IP address of DB
     * @throws SQLException
     */
    public DB(String ip) throws SQLException {
        connection = DBUtils.createConnection(ip);
        statement = connection.createStatement();
    }

    /**
     * Method for getting connection to DB
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Method for getting statement that can be used for any CRUD operation in DB
     * @return statement
     */
    public Statement getStatement() {
        return statement;
    }
}
