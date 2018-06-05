package models;

import java.sql.Timestamp;

public class Appointment
{
    public int getAppointmentID() {
        return appointmentID;
    }

    public Contact getObjCustomer() {
        return objCustomer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getUrl() {
        return url;
    }

    public String getContact() {
        return contact;
    }

    public Timestamp getSqlStartDateTime() { return sqlStartDateTime; }

    public Timestamp getSqlEndDateTime() { return sqlEndDateTime; }

    private int appointmentID;
    private Contact objCustomer;
    private String title;
    private String description;
    private String location;
    private String url;
    private String contact;
    private Timestamp sqlStartDateTime;
    private Timestamp sqlEndDateTime;

    public Appointment(Contact objCustomer, String title, String description, String location, String url, String contact, Timestamp sqlStartDateTime, Timestamp sqlEndDateTime) {
        this.objCustomer = objCustomer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.url = url;
        this.contact = contact;
        this.sqlStartDateTime = sqlStartDateTime;
        this.sqlEndDateTime = sqlEndDateTime;
    }

    public Appointment(int appointmentID, Contact objCustomer, String title, String description, String location, String url, String contact, Timestamp sqlStartDateTime, Timestamp sqlEndDateTime) {
        this.appointmentID = appointmentID;
        this.objCustomer = objCustomer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.url = url;
        this.contact = contact;
        this.sqlStartDateTime = sqlStartDateTime;
        this.sqlEndDateTime = sqlEndDateTime;
    }

    public  Appointment(int appointmentID){
        this.appointmentID = appointmentID;
    }
}
