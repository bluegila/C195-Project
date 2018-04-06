package data;

import controllers.LoginControl;
import models.Appointment;
import models.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class SQLAppointmentDAO
{
    private static Connection conn = SQLConnection.getConn();
    public void insertAppointment(Appointment newAppointment) throws SQLException
    {
        Contact customerName = newAppointment.getObjCustomer();
        String title = newAppointment.getTitle();
        String description = newAppointment.getDescription();
        String location = newAppointment.getLocation();
        String url = newAppointment.getUrl();
        String contact = newAppointment.getContact();
        ZonedDateTime startTime = newAppointment.getStartDate();
        ZonedDateTime endTime = newAppointment.getEndDate();

        String uname = LoginControl.getCurrentUser().toString();
        java.sql.Date saveDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        PreparedStatement maxAppointmentIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(appointmentid) FROM appointment");
        ResultSet maxAppointmentIdRS = maxAppointmentIdSelectStmnt.executeQuery();
        maxAppointmentIdRS.next();
        int maxAppointmentId = maxAppointmentIdRS.getInt(1);
    }

}
