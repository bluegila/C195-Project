package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Calendar;

public class MonthlyAppointmentTypeReportControl
{
    @FXML
    private GridPane gridTypes;
    @FXML
    private ChoiceBox<Calendar> boxMonth;
    @FXML
    private TextField txtYearReport;
}
