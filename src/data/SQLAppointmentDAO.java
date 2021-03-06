package data;

import controllers.LoginControl;
import models.Appointment;
import models.Contact;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLAppointmentDAO
{
    private static Connection conn = SQLConnection.getConn();

    public static List<Appointment> selectAppointment(User currentUser) throws SQLException
    {
        String uname = currentUser.toString();
        List<Appointment> appointments = new ArrayList<>();
        PreparedStatement getAppointmentsFromDB = conn.prepareStatement
                ("SELECT appointment.customerId, appointment.location, appointment.description, appointment.contact, appointment.url, appointment.appointmentId, appointment.start, appointment.end, appointment.title, customer.customerName " +
                        "FROM appointment " +
                        "INNER JOIN customer ON customer.customerId = appointment.customerId " +
                        "WHERE appointment.createdBy = ?");
        getAppointmentsFromDB.setString(1, uname);
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

            Appointment appointment = new Appointment(appointmentId, objCustomer, title, description, location,  url, contact, start, end);

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

    public void updateAppointment(Appointment oldAppointment) throws SQLException
    {
        int appointmentId = oldAppointment.getAppointmentID();
        int customerId = oldAppointment.getObjCustomer().getCustomerID();
        String subject = oldAppointment.getTitle();
        String description = oldAppointment.getDescription();
        String location = oldAppointment.getLocation();
        String contact = oldAppointment.getContact();
        String url = oldAppointment.getUrl();
        Timestamp start = oldAppointment.getSqlStartDateTime();
        Timestamp end = oldAppointment.getSqlEndDateTime();

        String uname = LoginControl.getCurrentUser().toString();
        Timestamp saveDate = new Timestamp(Calendar.getInstance().getTime().getTime());

        PreparedStatement updateAppointmentStmnt = conn.prepareStatement
                ("UPDATE appointment SET customerId = ?, title = ?, description = ?, location = ?, contact = ?, url = ?, start = ?, end = ?, lastUpdateBy = ?, lastUpdate = ? WHERE appointmentId = ?");
        updateAppointmentStmnt.setInt(1, customerId);
        updateAppointmentStmnt.setString(2,subject);
        updateAppointmentStmnt.setString(3, description);
        updateAppointmentStmnt.setString(4, location);
        updateAppointmentStmnt.setString(5, contact);
        updateAppointmentStmnt.setString(6, url);
        updateAppointmentStmnt.setTimestamp(7, start);
        updateAppointmentStmnt.setTimestamp(8, end);
        updateAppointmentStmnt.setString(9, uname);
        updateAppointmentStmnt.setTimestamp(10, saveDate);
        updateAppointmentStmnt.setInt(11, appointmentId);

        updateAppointmentStmnt.execute();
    }
    public void deleteAppointment(Appointment oldAppointment) throws SQLException
    {
        int appointmentId = oldAppointment.getAppointmentID();

        PreparedStatement deleteAppointmentStmt = conn.prepareStatement
                ("DELETE FROM appointment WHERE appointmentId = ?");
        deleteAppointmentStmt.setInt(1,appointmentId);

        deleteAppointmentStmt.execute();
    }
}
