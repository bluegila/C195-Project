package controllers;

import Model.User;
import data.SQLUserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControl
{
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pwdPassword;

    public void login()
    {
        String username = txtUsername.getText();
        String password = pwdPassword.getText();

        SQLUserDAO userSQL = new SQLUserDAO();

        User user = userSQL.getUserByUserName(username);
    }
    //Call SQLUserDAO, this will in turn create SQLConnection and imports User and calls SQLStoredProc
    // SQLStoredProc uses SQLParams to make SQL parameters easier

}
