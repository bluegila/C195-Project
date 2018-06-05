//package utilities;
//
//import models.Appointment;
//
//import java.time.ZonedDateTime;
//
//public class Timer
//{
//    private static void addAppointmentTimer(Appointment appointment)
//    {
//        if (appointment.getSqlStartDateTime().isAfter(ZonedDateTime.now()))
//        {
//            long timeDistance = appointment.getSqlStartDateTime().toEpochSecond() - ZonedDateTime.now().toEpochSecond();
//            long delay = timeDistance - (15 * 60);
//            if (delay > 0)
//            {
//                Platform.runLater(() ->
//                {
//                    PauseTransition transition = new PauseTransition(Duration.seconds(delay));
//                    transition.setOnFinished(event -> displayAppointmentReminder(appointment));
//                    transition.play();
//                });
//            }
//            else
//                displayAppointmentReminder(appointment);
//        }
//    }
//
//    private static void displayAppointmentReminder(Appointment appointment)
//    {
//        if (appointment.getStartDate().toEpochSecond() - ZonedDateTime.now().toEpochSecond() <= (15 * 60))
//            Dialog.displayPopupMessage(LocaleManager.getProperty("upcomingAppointmentTitle"),
//                    LocaleManager.getProperty("upcomingAppointmentMessage") + ": " + appointment.getTitle());
//    }
//}
