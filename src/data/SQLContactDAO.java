package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.*;
import controllers.LoginControl;

public class SQLContactDAO
{
    private static Connection conn = SQLConnection.getConn();

    public static List<Country> selectCountry() throws SQLException {
        List<Country> countries = new ArrayList<>();
        PreparedStatement getCountryFromDB = conn.prepareStatement
                ("SELECT * FROM country");
        ResultSet countryRS = getCountryFromDB.executeQuery();
        while(countryRS.next()) {
            String key = countryRS.getString(1);
            String value = countryRS.getString(2);
            Country country = new Country(key, value);
            countries.add(country);
        }
        return countries;
    }
    //#region selectContact
    public static List<Contact> selectContact() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        PreparedStatement getContactFromDB = conn.prepareStatement
                ("SELECT customer.customerId,customer.customerName, customer.addressId, address.address, address.address2, address.cityId, address.postalCode, address.phone, city.city, city.countryId, country.country, customer.active " +
                        "FROM customer "+
                "INNER JOIN address ON address.addressId = customer.addressId " +
                "INNER JOIN city ON city.cityId = address.cityId " +
                "INNER JOIN country ON country.countryId = city.countryId ");
        ResultSet contactRS = getContactFromDB.executeQuery();
        while(contactRS.next()) {
            String countryName = contactRS.getString(11);
            int countryID = contactRS.getInt(10);
            Country country = new Country(Integer.toString(countryID), countryName);

            String cityName = contactRS.getString(9);
            int cityID = contactRS.getInt(6);
            City city = new City(Integer.toString(cityID), cityName, country);

            int addressID = contactRS.getInt(3);
            String address = contactRS.getString(4);
            String address2 = contactRS.getString(5);
            String zip = contactRS.getString(7);
            String phone = contactRS.getString(8);
            Address objAddress = new Address(Integer.toString(addressID), address, address2, city, zip, phone);

            int customerID = contactRS.getInt(1);
            String customerName = contactRS.getString(2);
            boolean active = contactRS.getBoolean(12);
            Contact contact = new Contact(Integer.toString(customerID), customerName, objAddress, active);

            contacts.add(contact);
        }
        return contacts;
    }
    //#endregion

    //#region insertContact
    public void insertContact(Contact newContact) throws SQLException {
        boolean active = newContact.getActive();
        String contact = newContact.getCustomerName();
        Address objAddress = newContact.getObjAddress();
        String address = objAddress.getAddress();
        String address2 = objAddress.getAddress2();
        String zip = objAddress.getZip();
        String phone = objAddress.getPhone();
        City objCity = objAddress.getObjCity();
        String city = objCity.getCity();
        Country objCountry = objCity.getObjCountry();
        int countryID = objCountry.getCountryID();

        String uname = LoginControl.getCurrentUser().toString();
        java.sql.Date saveDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        PreparedStatement maxCityIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(cityid) FROM city");
        ResultSet maxCityIdRS = maxCityIdSelectStmnt.executeQuery();
        maxCityIdRS.next();
        int maxCityId = maxCityIdRS.getInt(1);

        PreparedStatement maxAddressIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(addressId) FROM address");
        ResultSet maxAddressIdRS = maxAddressIdSelectStmnt.executeQuery();
        maxAddressIdRS.next();
        int maxAddressId = maxAddressIdRS.getInt(1);

        PreparedStatement maxContactIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(customerId) FROM customer");
        ResultSet maxCustomerIdRS = maxContactIdSelectStmnt.executeQuery();
        maxCustomerIdRS.next();
        int maxCustomerId = maxCustomerIdRS.getInt(1);

        PreparedStatement cityInsertStmnt = conn.prepareStatement
                ("INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                        "SELECT * FROM (SELECT ? AS cityId, ? AS city, ? AS countryID, ? AS createDate, ? AS createdBy, ? AS lastUpDate, ? AS lastUpdateBy) AS cityTemp WHERE NOT EXISTS" +
                "(SELECT * FROM city WHERE city.city = ? AND city.countryId = ?)");
        cityInsertStmnt.setInt(1,++maxCityId);
        cityInsertStmnt.setString(2,city);
        cityInsertStmnt.setInt(3,countryID);
        cityInsertStmnt.setDate(4, saveDate);
        cityInsertStmnt.setString(5, uname);
        cityInsertStmnt.setDate(6, saveDate);
        cityInsertStmnt.setString(7, uname);
        cityInsertStmnt.setString(8,city);
        cityInsertStmnt.setInt(9,countryID);
        cityInsertStmnt.execute();

        PreparedStatement citySelectStmnt = conn.prepareStatement
                ("SELECT cityId FROM city WHERE city.city = ? AND city.countryId = ?");
        citySelectStmnt.setString(1,city);
        citySelectStmnt.setInt(2, countryID);
        ResultSet cityIDRS = citySelectStmnt.executeQuery();
        cityIDRS.next();

        PreparedStatement addressInsertStmnt = conn.prepareStatement
                ("INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "SELECT * FROM  (SELECT ? AS addressID,? AS address,? AS address2,? AS cityId,? AS postalCode,? AS phone, ? AS createDate, ? AS createdBy, ? AS lastUpdate, ? AS lastUpdateBy) AS addressTemp WHERE NOT EXISTS" +
                "(SELECT * FROM address WHERE address.address = ? AND address.cityId =?)");
        addressInsertStmnt.setInt(1, ++maxAddressId);
        addressInsertStmnt.setString(2,address);
        addressInsertStmnt.setString(3, address2);
        addressInsertStmnt.setInt(4, (cityIDRS.getInt(1)));
        addressInsertStmnt.setString(5, zip);
        addressInsertStmnt.setString(6, phone);
        addressInsertStmnt.setDate(7, saveDate);
        addressInsertStmnt.setString(8, uname);
        addressInsertStmnt.setDate(9, saveDate);
        addressInsertStmnt.setString(10, uname);

        addressInsertStmnt.setString(11,address);
        addressInsertStmnt.setInt(12, (cityIDRS.getInt(1)));
        addressInsertStmnt.execute();

        PreparedStatement addressSelectStmnt = conn.prepareStatement
                ("(SELECT * FROM address WHERE address.address = ? AND address2 = ? AND postalCode = ? AND phone = ? AND address.cityId =?)");
        addressSelectStmnt.setString(1,address);
        addressSelectStmnt.setString(2, address2);
        addressSelectStmnt.setString(3, zip);
        addressSelectStmnt.setString(4, phone);
        addressSelectStmnt.setInt(5,cityIDRS.getInt(1));
        ResultSet addressIDRS = addressSelectStmnt.executeQuery();
        addressIDRS.next();

        PreparedStatement contactInsertStmnt = conn.prepareStatement
                ("INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                        "SELECT * FROM (SELECT ? AS customerID, ? AS customerName,? AS addressId,? AS active, ? AS createDAte, ? AS createdBy, ? AS lastUpdate, ? AS lastUpdateBy) AS customerTemp WHERE NOT EXISTS" +
                        "(SELECT * FROM customer WHERE customer.customerName = ? AND customer.addressId = ?)");
        contactInsertStmnt.setInt(1, ++maxCustomerId);
        contactInsertStmnt.setString(2, contact);
        contactInsertStmnt.setInt(3,addressIDRS.getInt(1));
        contactInsertStmnt.setBoolean(4,active);
        contactInsertStmnt.setDate(5, saveDate);
        contactInsertStmnt.setString(6, uname);
        contactInsertStmnt.setDate(7, saveDate);
        contactInsertStmnt.setString(8, uname);
        contactInsertStmnt.setString(9, contact);
        contactInsertStmnt.setInt(10, addressIDRS.getInt(1));
        contactInsertStmnt.execute();
    }
    //#endregion

    //#region updateContact
    public void updateContact(Contact existingContact) throws SQLException
    {
        boolean active = existingContact.getActive();
        String contact = existingContact.getCustomerName();
        int contactId = existingContact.getCustomerID();
        Address objAddress = existingContact.getObjAddress();
        String address = objAddress.getAddress();
        String address2 = objAddress.getAddress2();
        String zip = objAddress.getZip();
        String phone = objAddress.getPhone();
        City objCity = objAddress.getObjCity();
        String city = objCity.getCity();
        Country objCountry = objCity.getObjCountry();
        int countryID = objCountry.getCountryID();

        String uname = LoginControl.getCurrentUser().toString();
        java.sql.Date saveDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        PreparedStatement maxCityIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(cityid) FROM city");
        ResultSet maxCityIdRS = maxCityIdSelectStmnt.executeQuery();
        maxCityIdRS.next();
        int maxCityId = maxCityIdRS.getInt(1);

        PreparedStatement maxAddressIdSelectStmnt = conn.prepareStatement(
                "SELECT MAX(addressId) FROM address");
        ResultSet maxAddressIdRS = maxAddressIdSelectStmnt.executeQuery();
        maxAddressIdRS.next();
        int maxAddressId = maxAddressIdRS.getInt(1);

        PreparedStatement contactIdSelectStmnt = conn.prepareStatement(
                "SELECT customerId FROM customer");
        ResultSet customerIdRS = contactIdSelectStmnt.executeQuery();
        customerIdRS.next();
        int customerId = customerIdRS.getInt(1);

        PreparedStatement cityUpdateStmnt = conn.prepareStatement
                ("INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                        "SELECT * FROM (SELECT ? AS cityId, ? AS city, ? AS countryID, ? AS createDate, ? AS createdBy, ? AS lastUpDate, ? AS lastUpdateBy) AS cityTemp WHERE NOT EXISTS" +
                        "(SELECT * FROM city WHERE city.city = ? AND city.countryId = ?)");
        cityUpdateStmnt.setInt(1,++maxCityId);
        cityUpdateStmnt.setString(2,city);
        cityUpdateStmnt.setInt(3,countryID);
        cityUpdateStmnt.setDate(4, saveDate);
        cityUpdateStmnt.setString(5, uname);
        cityUpdateStmnt.setDate(6, saveDate);
        cityUpdateStmnt.setString(7, uname);

        //WHERE NOT EXISTS parameters
        cityUpdateStmnt.setString(8,city);
        cityUpdateStmnt.setInt(9,countryID);
        cityUpdateStmnt.execute();

        PreparedStatement citySelectStmnt = conn.prepareStatement
                ("SELECT cityId FROM city WHERE city.city = ? AND city.countryId = ?");
        citySelectStmnt.setString(1,city);
        citySelectStmnt.setInt(2, countryID);
        ResultSet cityIDRS = citySelectStmnt.executeQuery();
        cityIDRS.next();

        PreparedStatement addressUpdateStmnt = conn.prepareStatement
                ("INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "SELECT * FROM  (SELECT ? AS addressID,? AS address,? AS address2,? AS cityId,? AS postalCode,? AS phone, ? AS createDate, ? AS createdBy, ? AS lastUpdate, ? AS lastUpdateBy) AS addressTemp WHERE NOT EXISTS" +
                        "(SELECT * FROM address WHERE address.address = ? AND address2 = ? AND postalCode = ? AND phone = ? AND address.cityId =?)");
        addressUpdateStmnt.setInt(1, ++maxAddressId);
        addressUpdateStmnt.setString(2,address);
        addressUpdateStmnt.setString(3, address2);
        addressUpdateStmnt.setInt(4, (cityIDRS.getInt(1)));
        addressUpdateStmnt.setString(5, zip);
        addressUpdateStmnt.setString(6, phone);
        addressUpdateStmnt.setDate(7, saveDate);
        addressUpdateStmnt.setString(8, uname);
        addressUpdateStmnt.setDate(9, saveDate);
        addressUpdateStmnt.setString(10, uname);

        //WHERE NOT EXISTS parameters
        addressUpdateStmnt.setString(11,address);
        addressUpdateStmnt.setString(12, address2);
        addressUpdateStmnt.setString(13, zip);
        addressUpdateStmnt.setString(14, phone);
        addressUpdateStmnt.setInt(15, (cityIDRS.getInt(1)));
        addressUpdateStmnt.execute();

        PreparedStatement addressSelectStmnt = conn.prepareStatement
                ("(SELECT * FROM address WHERE address.address = ? AND address2 = ? AND postalCode = ? AND phone = ? AND address.cityId =?)");
        addressSelectStmnt.setString(1,address);
        addressSelectStmnt.setString(2, address2);
        addressSelectStmnt.setString(3, zip);
        addressSelectStmnt.setString(4, phone);
        addressSelectStmnt.setInt(5,cityIDRS.getInt(1));
        ResultSet addressIDRS = addressSelectStmnt.executeQuery();
        addressIDRS.next();

        PreparedStatement contactUpdateStmnt = conn.prepareStatement
                ("UPDATE customer SET customerName = ?, active = ?, addressId = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId = ?");
        contactUpdateStmnt.setString(1, contact);
        contactUpdateStmnt.setInt(3,addressIDRS.getInt(1));
        contactUpdateStmnt.setBoolean(2,active);
        contactUpdateStmnt.setDate(4, saveDate);
        contactUpdateStmnt.setString(5, uname);
        contactUpdateStmnt.setInt(6, customerId);
        contactUpdateStmnt.execute();
    }
    //endregion


    //endregion

}
