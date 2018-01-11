package data;


import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLUserDAO
{
    public User getUserByUsername(String username){

        Connection conn = SQLConnection.getConn();
        try {
            PreparedStatement userSelectStmnt = conn.prepareStatement
                    ("SELECT userName, password FROM user WHERE userName =?");
            userSelectStmnt.setString(1,username);
            ResultSet userResultSet = userSelectStmnt.executeQuery();

            return new User(userResultSet.getString("userName"),userResultSet.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
