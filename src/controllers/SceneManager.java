 package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;


 public class SceneManager
{
    private static Stage mainStage;

    public static void init (Stage stage) throws IOException
     {
        mainStage = stage;
        mainStage.setResizable(false);
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmLogin.fxml"), ResourceBundle.getBundle("login"));
        Scene loginScene = new Scene(root,300,200);
        mainStage.setScene(loginScene);
        mainStage.show();
    }

    public static Stage getMainStage() throws IOException
    {
        return mainStage;
    }

    public static void showCalendarScene() throws IOException
    {
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmCalendar.fxml"));
        Scene calendarScene = new Scene(root);
        mainStage.setScene(calendarScene);
        mainStage.show();
    }

    public static void showMonthlyAppointmentTypeReportScene() throws IOException
    {
        Stage reportStage = new Stage();
        reportStage.setResizable(false);
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmMonthlyAppointmentTypesReport.fxml"));
        Scene monthlyAppointmentTypeReport = new Scene(root);
        reportStage.setScene(monthlyAppointmentTypeReport);
        reportStage.show();
    }

    public static void showMonthlyAppointmentCountReportScene() throws IOException
    {
        Stage reportStage = new Stage();
        reportStage.setResizable(false);
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmMonthlyAppointmentCountByUserReport.fxml"));
        Scene monthlyAppointmentCountReport = new Scene(root);
        reportStage.setScene(monthlyAppointmentCountReport);
        reportStage.show();
    }

    public static void showWeeklyScheduleByUserReportScene() throws IOException
    {
        Stage reportStage = new Stage();
        reportStage.setResizable(false);
        Parent root = FXMLLoader.load(SceneManager.class.getResource("/views/frmConsultantSchedule.fxml"));
        Scene weeklyScheduleReport = new Scene(root);
        reportStage.setScene(weeklyScheduleReport);
        reportStage.show();
    }
}

