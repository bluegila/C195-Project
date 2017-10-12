package data;


import Model.User;

public class SQLUserDAO
{
    User getUserName(String username){
        SQLStoredProc cmd = new SQLStoredProc("getUser");
    } //throws RuntimeException;
}
