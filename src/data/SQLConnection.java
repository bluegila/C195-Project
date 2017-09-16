package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLConnection {

    //load the driver
    Class.forName("com.mysql.jdbc.Driver")
// create a connection


   private static Connection conn = null;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U03HA0", "U03HA0", "53687976576");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
