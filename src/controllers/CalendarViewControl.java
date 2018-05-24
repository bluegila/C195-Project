package controllers;

//region Imports
import data.SQLAppointmentDAO;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import models.*;
import data.SQLContactDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.*;

import javafx.event.ActionEvent;
import utilities.TimeValidation;


//endregion

//region @FXML
public class CalendarViewControl implements Initializable
{
    @FXML
    private Pane root;
    @FXML
    private GridPane calLabel;
    @FXML
    private GridPane calGrid;
    @FXML
    private Text lblSunday;
    @FXML
    private Text lblMonday;
    @FXML
    private Text lblTuesday;
    @FXML
    private Text lblWednesday;
    @FXML
    private Text lblThursday;
    @FXML
    private Text lblFriday;
    @FXML
    private Text lblSaturday;
    @FXML
    private GridPane calDaysMonth;
    @FXML
    private GridPane calWeek;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtZip;
    @FXML
    private ChoiceBox<Country> boxCountry;
    @FXML
    private TextField txtPhone;
    @FXML
    private Label lblCustomerID;
    @FXML
    private CheckBox boxActive;
    @FXML
    private ListView<Contact> listContacts;
    @FXML
    private Button btnContactOK;
    @FXML
    private TabPane tabContacts;
    @FXML
    private ChoiceBox<Contact> boxCustomer;
    @FXML
    private TextField txtSubject;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtURL;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtAppointmentLength;
    @FXML
    private Label lblApptId;
 //endregion

    //region @init
    @Override
    public void initialize(URL location, ResourceBundle resources) //from Initializable
    {
        buildWeekView(Calendar.getInstance());
        buildMonthView(Calendar.getInstance());
        lblCustomerID.setText("0");
        lblApptId.setText("0");
        try {
            List<Country> countries = SQLContactDAO.selectCountry();
            boxCountry.setItems(FXCollections.observableArrayList(countries));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Contact> contacts = SQLContactDAO.selectContact();
            listContacts.setItems(FXCollections.observableArrayList(contacts));
            boxCustomer.setItems(FXCollections.observableArrayList(contacts));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    public void onTodayClick() //TODO This Method
    {
        buildWeekView(Calendar.getInstance());
        buildMonthView(Calendar.getInstance());
    }

    private void onMonthClick(Calendar calendar,int monthDiff)
    {
        calendar.add(Calendar.MONTH,monthDiff);
        calGrid.getChildren().clear();
        calLabel.getChildren().clear();
        buildMonthView(calendar);
    }

    //region buildMonthView
    private void buildMonthView(Calendar calendar)
    {
        Calendar monthCalendar = (Calendar) calendar.clone();
        int year = calendar.get(Calendar.YEAR);

        String strYear = Integer.toString(year);//set year
        String strMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());

        Button decreaseArrowYear = new Button("<");
        decreaseArrowYear.setOnAction(event -> onMonthClick(monthCalendar,-12));
        decreaseArrowYear.setCursor(Cursor.HAND);

        Button increaseArrowYear = new Button(">");
        increaseArrowYear.setOnAction(event ->  onMonthClick(monthCalendar,12));
        increaseArrowYear.setCursor(Cursor.HAND);

        Button decreaseArrowMonth = new Button("<");
        decreaseArrowMonth.setOnAction((event) -> onMonthClick(monthCalendar,-1));
        decreaseArrowMonth.setCursor(Cursor.HAND);

        Button increaseArrowMonth = new Button(">");
        increaseArrowMonth.setOnAction((event) -> onMonthClick(monthCalendar,1));
        increaseArrowMonth.setCursor(Cursor.HAND);

        Label calYear = new Label(strYear);
        Label calMonth = new Label(strMonth);
        calLabel.add(calYear,1,0);
        calLabel.add(calMonth,1,1);
        calYear.setPrefWidth(200.0);
        calMonth.setPrefWidth(200.0);
        calLabel.add(decreaseArrowYear,0,0);
        calLabel.add(decreaseArrowMonth,0,1);
        calLabel.add(increaseArrowYear,2,0);
        calLabel.add(increaseArrowMonth,2,1);

        final String cssButtonDefault =
                "-fx-background-color:transparent;-fx-text-alignment:center;-fx-alignment:center;-fx-text-fill:#8b4d4e;-fx-font-weight: bold;-fx-font-size: 11pt; ";
        final String cssCalLabel =
                "-fx-background-color:transparent;-fx-text-alignment:center;-fx-alignment:center;-fx-text-fill:#8b4d4e;-fx-font-weight: bold; -fx-font-size: 14pt; ";

        for (int row = 0; row < 6; row++) // month row iterator max 6 weeks
        {
            for (int col = 0; col < 7; col++)//weekday columns
            {
                String dateOfMonth = Integer.toString(calendar.get(Calendar.DATE));
                Button dateButton = new Button(dateOfMonth);
                dateButton.setPrefWidth(40.0);
                calMonth.setStyle(cssCalLabel);
                calYear.setStyle(cssCalLabel);
                decreaseArrowMonth.setStyle(cssButtonDefault);
                decreaseArrowYear.setStyle(cssButtonDefault);
                increaseArrowMonth.setStyle(cssButtonDefault);
                increaseArrowYear.setStyle(cssButtonDefault);
                dateButton.setStyle(cssButtonDefault);
                calGrid.add(dateButton,col,row);
                calendar.add(Calendar.DATE,1);
            }
        }

        for (int col = 0; col < 7; col++)
        {
            calendar.set(Calendar.DAY_OF_WEEK, col + 1);
            String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
            Label dayOfWeekLabel = new Label(dayOfWeek);
            dayOfWeekLabel.setPrefWidth(40.0);
            dayOfWeekLabel.setStyle(cssButtonDefault);
            calDaysMonth.add(dayOfWeekLabel,col,0);
        }

        try
        {
            List<Appointment> selectAllAppointmentsSelectedWeek = SQLAppointmentDAO.selectAppointment();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }



    }
    //endregion

    private void buildWeekView(Calendar calendar)
    {
        Calendar calendarWeek = calendar;
        calendarWeek.get(Calendar.WEEK_OF_YEAR);
        calendarWeek.set(Calendar.DAY_OF_WEEK,1);
        final double PX_PER_HOUR = 50d;
        final double START_HOUR = 5d;
        final double XLAYOUT_OFFSET = 300d;
        final double PX_PER_DAY = 125d;
        final double YLAYOUT_OFFSET = 490d;
     //   TimeZone timezone = TimeZone.getTimeZone(ZoneId.systemDefault());

        try {
            List<Appointment> appointments = SQLAppointmentDAO.selectAppointment();
            int comparator = FXCollections.observableArrayList(appointments).size();
            for (int j = 0; j <comparator; j++)
            {
                final Appointment currentAppointment = appointments.get(j);
                ZonedDateTime localStartTime = appointments.get(j).getSqlStartDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                double appointmentStart = (double) localStartTime.getHour() +
                        (localStartTime.getMinute() / 60d);
                ZonedDateTime localEndTime = appointments.get(j).getSqlEndDateTime().toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
                double appointmentEnd = (double) localEndTime.getHour() +
                        (localEndTime.getMinute() / 60d);
                int appointmentDayofWeek = localStartTime.getDayOfWeek().getValue();
                double appointmentLength = appointmentEnd - appointmentStart;
                final String cssButtonDefault =
                        "-fx-background-color: #c6b1b1;-fx-text-alignment:center;-fx-alignment:center;-fx-text-fill:#8b4d4e;-fx-font-size: 9pt; -fx-border-color: #8b4d4e; -fx-border-width: 2px; ";

                if (localStartTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == calendarWeek.get(Calendar.WEEK_OF_YEAR))
                {
                    Button btnAppointment = new Button(appointments.get(j).getObjCustomer().getCustomerName());
                    btnAppointment.setPrefWidth(125.0);
                    btnAppointment.setPrefHeight(appointmentLength * PX_PER_HOUR);
                    btnAppointment.setLayoutX((appointmentStart - START_HOUR) * (PX_PER_HOUR) + XLAYOUT_OFFSET);
                    btnAppointment.setLayoutY((appointmentDayofWeek - 1d) * (PX_PER_DAY) + YLAYOUT_OFFSET);
                    btnAppointment.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
                    btnAppointment.setOnAction(event -> loadAppointment(currentAppointment));
                    int row = (int)((appointmentStart - START_HOUR) * 2) + 1;
                    int rowspan = (int)((appointmentLength) * 2);
                    calWeek.add(btnAppointment,(appointmentDayofWeek + 1),row,1,rowspan);
                    btnAppointment.setStyle(cssButtonDefault);
                    btnAppointment.toFront();
                    btnAppointment.setCursor(Cursor.HAND);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int row = 1; row < 30; row++)
        {

            for (int amHour = 5; amHour < 12 & row < 15; amHour++, row++)
            {
                String strAMHour = Integer.toString(amHour) + ":00 AM";
                Label hourLabel = new Label();
                hourLabel.setText(strAMHour);
                calWeek.add(hourLabel,0,row);
                hourLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 14));
                hourLabel.setStyle("-fx-text-alignment:center;-fx-alignment: center;-fx-label-padding: 5px;;-fx-pref-width: 100px;");
                row++;
            }

            String strNoon = "12:00 PM";
            Label noonLabel = new Label();
            noonLabel.setText(strNoon);
            calWeek.add(noonLabel,0,row);
            noonLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 14));
            noonLabel.setStyle("-fx-text-alignment:center;-fx-alignment: center;-fx-label-padding: 5px;-fx-pref-width: 100px;");
            row = row + 2;

            for (int pmHour = 1; row < 30; pmHour++, row++)
            {
                String strPMHour = Integer.toString(pmHour) + ":00 PM";
                Label hourLabel = new Label();
                hourLabel.setText(strPMHour);
                calWeek.add(hourLabel,0,row);
                hourLabel.setFont(Font.font("Open Sans", FontWeight.BOLD, 14));
                hourLabel.setStyle("-fx-text-alignment:center;-fx-alignment: center;-fx-label-padding: 5px;-fx-pref-width: 100px;");
                row++;
            }
        }
        for (int i = 0; i < 7; i++)
        {
            //add appt data too
            int dow = calendarWeek.get(Calendar.DAY_OF_WEEK );
            if (dow == 1)
            {
                lblSunday.setText("Sunday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblSunday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            else if (dow == 2)
            {
                lblMonday.setText("Monday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblMonday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            else if (dow == 3)
            {
                lblTuesday.setText("Tuesday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblTuesday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            else if (dow == 4)
            {
                lblWednesday.setText("Wednesday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblWednesday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            else if (dow == 5)
            {
                lblThursday.setText("Thursday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblThursday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            else if (dow == 6)
            {
                lblFriday.setText("Friday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblFriday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            else
            {
                lblSaturday.setText("Saturday " + calendarWeek.get(Calendar.DAY_OF_MONTH));
                lblSaturday.setFont(Font.font("Open Sans", FontWeight.BOLD, 16));
            }
            calendarWeek.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    private void clearContactForm()
    {
        txtName.clear();
        txtAddress.clear();
        txtAddress2.clear();
        txtCity.clear();
        txtZip.clear();
        txtPhone.clear();
        lblCustomerID.setText("0");
    }

    @FXML
    private void loadContact(ActionEvent actionEvent) throws SQLException {
        Contact selectedContact = listContacts.getSelectionModel().getSelectedItem();
        int tempCustomerID = selectedContact.getCustomerID();
        lblCustomerID.setText(Integer.toString(tempCustomerID));
        tabContacts.getSelectionModel().select(1);

        txtName.setText(selectedContact.getCustomerName());
        txtAddress.setText(selectedContact.getObjAddress().getAddress());
        txtAddress2.setText(selectedContact.getObjAddress().getAddress2());
        txtCity.setText(selectedContact.getObjAddress().getObjCity().getCity());
        txtZip.setText(selectedContact.getObjAddress().getZip());
        txtPhone.setText(selectedContact.getObjAddress().getPhone());
        boxCountry.getSelectionModel().select(selectedContact.getObjAddress().getObjCity().getObjCountry().getCountryID());
    }

    @FXML
    private void clearContact(ActionEvent actionEvent) throws IOException, SQLException
    {
        clearContactForm();
    }

    @FXML
    private void saveContact(ActionEvent actionEvent) throws IOException, SQLException
    {
        if (fieldsCustomerValidate())
        {
            String contactName = txtName.getText();
            String contactAddress = txtAddress.getText();
            String contactAddress2 = txtAddress2.getText();
            String contactCity = txtCity.getText();
            String contactZip = txtZip.getText();
            Country objCountry = boxCountry.getSelectionModel().getSelectedItem();
            String contactPhone = txtPhone.getText();
            Boolean contactActive = boxActive.isSelected();

            String contactID = lblCustomerID.getText();

            SQLContactDAO contactSQL = new SQLContactDAO();

            City city = new City(contactCity,objCountry);
            Address address = new Address(contactAddress,contactAddress2, city, contactZip, contactPhone);
            Contact contact = new Contact(contactID,contactName, address, contactActive);

            if (lblCustomerID.getText().equals("0"))
            {
                try {
                    contactSQL.insertContact(contact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    contactSQL.updateContact(contact);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try
        {
            List<Contact> contacts = SQLContactDAO.selectContact();
            listContacts.setItems(FXCollections.observableArrayList(contacts));
            boxCustomer.setItems(FXCollections.observableArrayList(contacts));

            clearContactForm();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private boolean fieldsCustomerValidate()
    {
        if (txtName.getText().trim().equals("") ||
            txtAddress.getText().trim().equals("") ||
            txtCity.getText().trim().equals("") ||
            txtZip.getText().trim().equals("") ||
            txtPhone.getText().trim().equals("") )
        {
            alertMissingInformation();
        }
        else return true;
       return false; //why do I need this?
    }

    private void loadAppointment(Appointment appointment)
    {
        tabContacts.getSelectionModel().select(2);
        boxCustomer.getSelectionModel().select(appointment.getObjCustomer());
        txtSubject.setText(appointment.getTitle());
        txtDescription.setText(appointment.getDescription());
        txtLocation.setText(appointment.getLocation());
        txtContact.setText(appointment.getContact());
        txtURL.setText(appointment.getUrl());
        datePicker.setValue(appointment.getSqlStartDateTime().toLocalDateTime().toLocalDate());
        txtStartTime.setText(appointment.getSqlStartDateTime().toLocalDateTime().toLocalTime().toString());

        long apptLengthMS = appointment.getSqlEndDateTime().getTime() - appointment.getSqlStartDateTime().getTime();
        long apptLengthMins = apptLengthMS / 60000;
        txtAppointmentLength.setText(Long.toString(apptLengthMins));
        lblApptId.setText(Integer.toString(appointment.getAppointmentID()));
    }

    @FXML
    private void addAppointment(ActionEvent actionEvent) throws IOException, SQLException
    {
        if (fieldsAppointmentValidate()) {
            Contact customer = boxCustomer.getSelectionModel().getSelectedItem();
            String subject = txtSubject.getText();
            String description = txtDescription.getText();
            String location = txtLocation.getText();
            String url = txtURL.getText();
            String contact = txtContact.getText();
            LocalDate date = datePicker.getValue();
            String start = txtStartTime.getText();
            int appointmentLength = 0;
            try
            {
                appointmentLength = Integer.parseInt(txtAppointmentLength.getText());
            }
            catch(NumberFormatException ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Appointment Length");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid appointment Length");
                alert.showAndWait();
            }

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
            LocalTime timePart = LocalTime.parse(start, timeFormatter);
            LocalDateTime startDateTime = LocalDateTime.of(date, timePart);
            LocalDateTime endDateTime = startDateTime.plusMinutes(appointmentLength);
            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime zonedStartDateTime = startDateTime.atZone(zid);
            ZonedDateTime zonedEndDateTime = endDateTime.atZone(zid);
            Timestamp sqlStartDateTime = Timestamp.valueOf(LocalDateTime.ofInstant(zonedStartDateTime.toInstant(), ZoneOffset.UTC));
            Timestamp sqlEndDateTime = Timestamp.valueOf(LocalDateTime.ofInstant(zonedEndDateTime.toInstant(), ZoneOffset.UTC));
            SQLAppointmentDAO appointmentSQL = new SQLAppointmentDAO();

            Appointment appointment = new Appointment(customer, subject, description, location, url, contact, sqlStartDateTime, sqlEndDateTime);

            if (lblApptId.getText().equals("0"))
            {
                try {
                    appointmentSQL.insertAppointment(appointment);
                    buildWeekView(Calendar.getInstance());
                    clearAppointmentForm();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            /*
            else
            {
                try {
                    appointmentSQL.updateAppointment(appointment);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            */
        }
    }

    @FXML
    private void clearAppointment(ActionEvent actionEvent) throws IOException, SQLException
    {
        clearAppointmentForm();
    }

    private void clearAppointmentForm()
    {
        boxCustomer.setValue(null);
        txtSubject.clear();
        txtDescription.clear();
        txtLocation.clear();
        txtURL.clear();
        txtContact.clear();
        datePicker.setValue(null);
        txtStartTime.clear();
        txtAppointmentLength.clear();
        lblApptId.setText("0");
    }

    private boolean fieldsAppointmentValidate()
    {
        if (boxCustomer.getSelectionModel().getSelectedItem() == null ||
                txtSubject.getText().trim().equals("") ||
                txtDescription.getText().trim().equals("") ||
                txtLocation.getText().trim().equals("") ||
                datePicker.getValue() == null ||
                txtStartTime.getText().trim().equals("") ||
                txtAppointmentLength.getText().trim().equals(""))
        {
            alertMissingInformation();
        }
        else
        {
           TimeValidation timeValidation = new TimeValidation();
           String time = txtStartTime.getText().trim();
            if (timeValidation.timeValidate(time)){
                return true;
            }
        }
        return false; //why do I need this?
    }

    private void alertMissingInformation()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Blank Field(s)");
        alert.setHeaderText(null);
        alert.setContentText("At least one field is empty.  Please check the input fields and try to Save again.");
        alert.showAndWait();
        //      .filter(response -> response == ButtonType.OK)
        //    .ifPresent(response -> formatSystem());
       // return false;
    }


}
