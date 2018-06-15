package controllers;

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
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
            setCurrentUser(user);
            SceneManager.getCalendarScene();
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
        alert.setTitle(ResourceBundle.getBundle("login").getString("alertTitle"));
        alert.setHeaderText(null);
        alert.setContentText(ResourceBundle.getBundle("login").getString("alertContents"));
        alert.showAndWait();
    }
    public void cancel(ActionEvent actionEvent) throws IOException
    {
        SceneManager.getMainStage().close();
    }
}