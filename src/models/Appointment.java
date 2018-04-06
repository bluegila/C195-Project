package models;

import java.time.ZonedDateTime;

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

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    private int appointmentID;
    private Contact objCustomer;
    private String title;
    private String description;
    private String location;
    private String url;
    private String contact;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public Appointment(Contact objCustomer, String title, String description, String location, String url, String contact, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.objCustomer = objCustomer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.url = url;
        this.contact = contact;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Appointment(int appointmentID, Contact objCustomer, String title, String description, String location, String url, String contact, ZonedDateTime startDate, ZonedDateTime endDate) {
        this.appointmentID = appointmentID;
        this.objCustomer = objCustomer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.url = url;
        this.contact = contact;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
