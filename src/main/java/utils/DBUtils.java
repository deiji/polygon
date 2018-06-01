package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    private static final Logger logger = LogManager.getLogger(DBUtils.class);
    /**
     * Method that creates connection to DB
     * @param ip - parameter which indicates IP address of DB
     * @return - method returns created connection to DB
     */
    public static Connection createConnection(String ip){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection =  DriverManager.getConnection(
                    "jdbc:oracle:thin:@10.1.32."+ ip + ":1521/ctginst1",
                    "maximo",
                    "maximo");
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException se) {
            logger.error("error while creating connection: " + se.getMessage());
            return null;
        } catch (ClassNotFoundException cnfe){
            logger.error("error while creating connection: " + cnfe.getMessage());
            return null;
        } catch (Exception e){
            logger.error("some other error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Method that executes specified SQL and log output to log file
     * @param sqlName - SQL title which is being executed
     * @param sql - SQL which is being executed
     */
    public static void executeSQL(String sqlName, String sql){
        boolean executed = false;
        Statement st = null;
        Connection connection = null;
        try{
            connection = createConnection("242");
            st = connection.createStatement();
            st.execute(sql);
            executed = true;
            logger.debug("executed successfuly " + sqlName);
        } catch(SQLException se){
            logger.error("exception while executing sql " + sqlName + " " + se.getMessage());
        } catch (Exception e) {
            logger.error("other exception " + e.getMessage());
        } finally {
            try {
                if(executed){
                    connection.commit();
                } else {
                    connection.rollback();
                }
                if(connection != null)
                    connection.close();
            } catch (Exception ee) {
                logger.error("exception while closing statement " + ee.getMessage());
            }
        }
    }
}
