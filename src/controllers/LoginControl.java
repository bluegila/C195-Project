package controllers;

import javafx.scene.control.Dialog;
import models.User;
import data.SQLUserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginControl
{
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pwdPassword;

    private static User currentUser;

    public void login() throws IOException
    {
        String username = txtUsername.getText();
        String password = pwdPassword.getText();

        SQLUserDAO userSQL = new SQLUserDAO();

        User user;
        user = userSQL.getUserByUsername(username);

        if (user == null)
        {
            badLogon();
        }
        else if (user.verify(password))
        {
            SceneManager.getMainStage().close();
            SceneManager.getCalendarScene();
            setCurrentUser(user);
            loginLog();
        }
        else
        {
            badLogon();
        }
    }
    private void loginLog()
    {
        File loginLogFile = new File(System.getProperty("user.dir") + "/login_log.txt");
        try
        {
            loginLogFile.createNewFile();
            FileWriter writer = new FileWriter(loginLogFile, true);
            String log = getCurrentUser().getUsername() + " successfully logged in at " + LocalDateTime.now().format(
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n";
            writer.append(log);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static User getCurrentUser()
    {
        return currentUser;
    }

    private static void setCurrentUser(User user)
    {
        currentUser = user;
    }

    private void badLogon()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bad Username or Password");
        alert.setHeaderText(null);
        alert.setContentText("Please check you username and password and try again.");
        alert.showAndWait();
    }
    public void cancel(ActionEvent actionEvent) throws IOException
    {
        SceneManager.getMainStage().close();
    }
}
