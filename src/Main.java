import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void init(){
        System.out.println("Inside init()");
    }



    @Override
    public void start(Stage loginStage) throws Exception{
        try {
            System.out.println("Inside start()");
            Parent root = FXMLLoader.load(getClass().getResource("loginView2.fxml"));
            Scene scene = new Scene(root,300,200);
            scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
            loginStage.setScene(scene);
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        System.out.println("Inside stop()");
    }
    public static void main(String[] args) {
        System.out.println("Launching JavaFX");
        launch(args);
    }
}

