package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLConnection {
    //Driver, DB URL, credentials
    private static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String SQL_DB_URL = "jdbc:mysql://52.206.157.109:3306/U03HA0";
    private static final String SQL_USERNAME = "U03HA0";
    private static final String SQL_PASSWORD = "53687976576";



    private static Connection conn = null;
    private static Statement stmnt = null;

    static {
        try {
            Class.forName(SQL_DRIVER);
            conn = DriverManager.getConnection(SQL_DB_URL, SQL_USERNAME, SQL_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}
