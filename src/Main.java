import controllers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void init()
    {
       // System.out.println("Inside init()");
    }

    @Override
    public void start(Stage mainStage) throws Exception{
        try {
            SceneManager.init(mainStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // public void stop()
    public static void main(String[] args) {

        launch(args);

    }
}

