package utilities;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import models.Appointment;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Timer
{
    public static void addAppointmentTimer(Appointment appointment)
    {
        if (appointment.getSqlStartDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).isAfter(ZonedDateTime.now()))
        {
            long timeDistance = appointment.getSqlStartDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).toEpochSecond() - ZonedDateTime.now().toEpochSecond();
            long timeDelay = timeDistance - (15 * 60);
            if (timeDelay > 0)
            {
                Platform.runLater(() ->
                {
                    PauseTransition transition = new PauseTransition(Duration.seconds(timeDelay));
                    transition.setOnFinished(event -> verifyAppointmentReminderAlert(appointment));
                    transition.play();
                });
            }
            else
                verifyAppointmentReminderAlert(appointment);
        }
    }

    private static void verifyAppointmentReminderAlert(Appointment appointment)
    {
        if (appointment.getSqlStartDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).toEpochSecond() - ZonedDateTime.now().toEpochSecond() <= (15 * 60))
            alertAppointmentReminder(appointment);
    }

    private static void alertAppointmentReminder(Appointment appointment)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointment");
        alert.setHeaderText(null);
        alert.setContentText("You have an appointment in 15 minutes or less with: " + appointment.getObjCustomer().getCustomerName() + ".");

        alert.show();
    }

}
