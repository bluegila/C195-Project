 package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class SceneManager
{
    private static Stage mainStage;

    public static void init (Stage stage) throws IOException
     {
        mainStage = stage;
      //  mainStage.setTitle
        mainStage.setResizable(false);
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmLogin.fxml"));
        Scene loginScene = new Scene(root,300,200);
        mainStage.setScene(loginScene);
        mainStage.show();
    }

    public static Stage getMainStage() throws IOException
    {
        return mainStage;
    }

    public static void getCalendarScene() throws IOException
    {
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmCalendar.fxml"));
        Scene calendarScene = new Scene(root);
        mainStage.setScene(calendarScene);
        mainStage.show();
    }


}

