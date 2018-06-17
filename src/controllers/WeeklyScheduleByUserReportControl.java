package controllers;

import data.SQLReportsDAO;
import data.SQLUserDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Appointment;
import models.User;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class WeeklyScheduleByUserReportControl  implements Initializable
{
    @FXML
    private GridPane gridTypes;
    @FXML
    private ChoiceBox<User> boxUser;

    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            createUserDropDown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUserDropDown() throws SQLException {

        boxUser.setItems(FXCollections.observableArrayList(SQLUserDAO.getAllUsers()));
    }

    @FXML
    private void runReport(ActionEvent actionEvent) throws SQLException
    {

        gridTypes.getChildren().clear();

        Calendar calendar = Calendar.getInstance();

        ZonedDateTime now = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        Timestamp sqlFirstOfMonth = Timestamp.valueOf(now.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        calendar.add(Calendar.DATE, 7);
        ZonedDateTime weekFromNow = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        Timestamp sqlLastOfMonth = Timestamp.valueOf(weekFromNow.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        List<Appointment> appointmentTypes = SQLReportsDAO.getApptsInRange(boxUser.getSelectionModel().getSelectedItem(), sqlFirstOfMonth, sqlLastOfMonth);

        Label dateHeader = new Label ("Date");
        dateHeader.setFont(Font.font("Open Sans", FontWeight.BOLD, 12));
        dateHeader.setStyle("-fx-text-alignment:center;-fx-alignment: center;");

        Label startHeader = new Label ("Start Time");
        startHeader.setFont(Font.font("Open Sans", FontWeight.BOLD, 12));
        startHeader.setStyle("-fx-text-alignment:center;-fx-alignment: center;");

        Label endHeader = new Label ("End Time");
        endHeader.setFont(Font.font("Open Sans", FontWeight.BOLD, 12));
        endHeader.setStyle("-fx-text-alignment:center;-fx-alignment: center;");

        Label subjectHeader = new Label ("Subject");
        subjectHeader.setFont(Font.font("Open Sans", FontWeight.BOLD, 12));
        subjectHeader.setStyle("-fx-text-alignment:center;-fx-alignment: center;");

        Label descHeader = new Label ("Description");
        descHeader.setFont(Font.font("Open Sans", FontWeight.BOLD, 12));
        descHeader.setStyle("-fx-text-alignment:center;-fx-alignment: center;");

        gridTypes.addRow(0,dateHeader, startHeader, endHeader, subjectHeader, descHeader);

        for(int i = 0; i < appointmentTypes.size();i++)
        {
            Label lblDate = new Label(appointmentTypes.get(i).getSqlStartDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/YYYY")));
            Label lblStartTime = new Label(appointmentTypes.get(i).getSqlStartDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("h:mm a")));
            Label lblEndTime = new Label(appointmentTypes.get(i).getSqlEndDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("h:mm a")));
            Label lblSubject = new Label(appointmentTypes.get(i).getTitle());
            Label lblDesc = new Label(appointmentTypes.get(i).getDescription());
            gridTypes.addRow(i+1,lblDate,lblStartTime,lblEndTime,lblSubject,lblDesc);
        }

    }
}





