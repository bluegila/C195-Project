package data;

import controllers.LoginControl;
import models.Appointment;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class SQLReportsDAO
{
    public  static List<Appointment> getApptsInRange (Timestamp sqlStartDate, Timestamp sqlEndDate)throws SQLException
    {
        return getApptsInRange(LoginControl.getCurrentUser(),sqlStartDate,sqlEndDate);
    }

    public  static List<Appointment> getApptsInRange (User selectedUser, Timestamp sqlStartDate, Timestamp sqlEndDate)throws SQLException
    {
        List<Appointment> allAppointments = SQLAppointmentDAO.selectAppointment(selectedUser);
        List<Appointment> filteredAppointments = new ArrayList<>();
        for(Appointment appointment : allAppointments)
            if ((appointment.getSqlStartDateTime().after(sqlStartDate) || appointment.getSqlStartDateTime().equals(sqlStartDate))
                    && (appointment.getSqlEndDateTime().before(sqlEndDate) || appointment.getSqlEndDateTime().equals(sqlEndDate)))
            {
                filteredAppointments.add(appointment);
            }
        return filteredAppointments;
    }
}

