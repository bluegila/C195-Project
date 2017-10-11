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
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/frmLogin.fxml"));
        Scene scene = new Scene(root,300,200);
        // scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();

    }

    public static Stage getMainStage()
    {

        return mainStage;
    }
}
