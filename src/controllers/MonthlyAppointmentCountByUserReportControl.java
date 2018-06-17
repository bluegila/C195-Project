package controllers;

import data.SQLReportsDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.Appointment;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class MonthlyAppointmentCountByUserReportControl  implements Initializable
{
    @FXML
    private GridPane gridTypes;
    @FXML
    private ChoiceBox<String> boxMonth;
    @FXML
    private ChoiceBox<String> boxYear;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblCount;

    public void initialize(URL location, ResourceBundle resources)
    {
        createMonthDropDown();
        createYearDropDown();
    }

    private void createMonthDropDown()
    {
        List<String> monthsList = new ArrayList<>();
        String[] months = new DateFormatSymbols().getMonths();
        for (String month : months)
            if(!month.equals(""))
                monthsList.add(month);
        boxMonth.setItems(FXCollections.observableArrayList(monthsList));
    }
    private void createYearDropDown()
    {
        List<String> yearsList = new ArrayList<>();
        for (int i = 2018; i <= 2035; i++)
        {
            yearsList.add(Integer.toString(i));
        }
        boxYear.setItems(FXCollections.observableArrayList(yearsList));
    }
    @FXML
    private void runApptTypeReport(ActionEvent actionEvent) throws SQLException {
        int month = boxMonth.getSelectionModel().getSelectedIndex();
        int year = Integer.parseInt(boxYear.getSelectionModel().getSelectedItem());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        ZonedDateTime zonedFirstOfMonth = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        Timestamp sqlFirstOfMonth = Timestamp.valueOf(zonedFirstOfMonth.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        ZonedDateTime zonedLastOfMonth = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        Timestamp sqlLastOfMonth = Timestamp.valueOf(zonedLastOfMonth.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        List<Appointment> appointmentCount = SQLReportsDAO.getApptsInRange(sqlFirstOfMonth, sqlLastOfMonth);


        lblUser.setText(LoginControl.getCurrentUser().getUsername());
        lblCount.setText(Integer.toString(appointmentCount.size()));

    }
}

