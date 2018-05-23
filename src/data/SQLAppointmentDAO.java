package data;

import controllers.LoginControl;
import models.Appointment;
import models.Contact;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLAppointmentDAO
{
    private static Connection conn = SQLConnection.getConn();

    public static List<Appointment> selectAppointment() throws SQLException
    {
        List<Appointment> appointments = new ArrayList<>();
        PreparedStatement getAppointmentsFromDB = conn.prepareStatement
                ("SELECT appointment.customerId, appointment.location, appointment.description, appointment.contact, appointment.url, appointment.appointmentId, appointment.start, appointment.end, appointment.title, customer.customerName " +
                        "FROM appointment " +
                        "INNER JOIN customer ON customer.customerId = appointment.customerId");
        ResultSet appointmentsRS = getAppointmentsFromDB.executeQuery();
        while(appointmentsRS.next())
        {
            String customerName = appointmentsRS.getString(10);
            int customerId = appointmentsRS.getInt(1);
            Contact objCustomer = new Contact(Integer.toString(customerId),customerName);
            String location = appointmentsRS.getString(2);
            String description = appointmentsRS.getString(3);
            String contact = appointmentsRS.getString(4);
            String url = appointmentsRS.getString(5);
            int appointmentId = appointmentsRS.getInt(6);
            Timestamp start = appointmentsRS.getTimestamp(7);
            Timestamp end = appointmentsRS.getTimestamp(8);
            String title = appointmentsRS.getString(9);

            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime zdtStart = start.toLocalDateTime().atZone(ZoneId.of("UTC"));
            ZonedDateTime localStart = zdtStart.withZoneSameInstant(zid);
            ZonedDateTime zdtEnd = end.toLocalDateTime().atZone((ZoneId.of("UTC")));
            ZonedDateTime localEnd = zdtEnd.withZoneSameInstant(zid);

          //  ZonedDateTime tempLocalStart = ZonedDateTime.from(localStart);
         //   long longDiffInMinutes = ChronoUnit.MINUTES.between(localStart, localEnd);
         //   int appointmentLength = (int) longDiffInMinutes;

            Appointment appointment = new Appointment(appointmentId, objCustomer, title, description, location, contact, url, localStart, localEnd);

            appointments.add(appointment);
        }
        return appointments;
    }
    public void insertAppointment(Appointment newAppointment) throws SQLException
    {
        int customerID = newAppointment.getObjCustomer().getCustomerID();
        String title = newAppointment.getTitle();
        String description = newAppointment.getDescription();
        String location = newAppointment.getLocation();
        String url = newAppointment.getUrl();
        String contact = newAppointment.getContact();

      //  LocalDateTime localDateTime = newAppointment.getStartDate().toLocalDateTime();
        Timestamp startTimeStamp =  newAppointment.getSqlStartDateTime();

       // LocalDateTime localEndTime = newAppointment.getEndDate().toLocalDateTime();
        Timestamp endTimeStamp = newAppointment.getSqlEndDateTime();

        String uname = LoginControl.getCurrentUser().toString();
        java.sql.Timestamp saveDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        PreparedStatement maxAppointmentIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(appointmentid) FROM appointment");
        ResultSet maxAppointmentIdRS = maxAppointmentIdSelectStmnt.executeQuery();
        maxAppointmentIdRS.next();
        int maxAppointmentId = maxAppointmentIdRS.getInt(1);

        PreparedStatement appointmentInsertStmnt = conn.prepareStatement
                ("INSERT INTO appointment (appointmentId, customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                        "SELECT * FROM (SELECT ? AS appointmentId, ? AS customerId, ? AS title,? AS description,? AS location,? AS contact, ? AS url, ? AS start, ? AS end, ? AS createDate, ? AS createdBy, ? AS lastUpDate, ? AS lastUpdateBy) AS appointmentTemp  WHERE NOT EXISTS" +
                        "(SELECT * FROM appointment WHERE (appointment.start BETWEEN ? AND ?) AND (appointment.end BETWEEN ? AND ?))");
        appointmentInsertStmnt.setInt(1,++maxAppointmentId);
        appointmentInsertStmnt.setInt(2, customerID);
        appointmentInsertStmnt.setString(3, title);
        appointmentInsertStmnt.setString(4, description);
        appointmentInsertStmnt.setString(5, location);
        appointmentInsertStmnt.setString(6, contact);
        appointmentInsertStmnt.setString(7, url);
        appointmentInsertStmnt.setTimestamp(8, startTimeStamp);
        appointmentInsertStmnt.setTimestamp(9, endTimeStamp);
        appointmentInsertStmnt.setTimestamp(10, saveDate);
        appointmentInsertStmnt.setString(11, uname);
        appointmentInsertStmnt.setTimestamp(12, saveDate);
        appointmentInsertStmnt.setString(13, uname);

        appointmentInsertStmnt.setTimestamp(14, startTimeStamp);
        appointmentInsertStmnt.setTimestamp(15, endTimeStamp);
        appointmentInsertStmnt.setTimestamp(16, startTimeStamp);
        appointmentInsertStmnt.setTimestamp(17, endTimeStamp);

        appointmentInsertStmnt.execute();
    }

}
