package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControl //implements Initializable
{
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pwdPassword;

    public void login()
    {
        String username = txtUsername.getText();
        String password = pwdPassword.getText();


    }

    //User
    // SQLConnection
    // SQLStoredProc
}
