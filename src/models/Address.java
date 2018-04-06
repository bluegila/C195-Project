package models;

public class Address
{
    private int addressID;
    private String address;
    private String address2;
    private City objCity;
    private String zip;
    private String phone;

    public Address(String addressID, String address, String address2, City objCity, String zip, String phone)
    {
        this.addressID = Integer.parseInt(addressID);
        this.address = address;
        this.address2 = address2;
        this.objCity = objCity;
        this.zip = zip;
        this.phone = phone;
    }
    public Address(String address, String address2, City objCity, String zip, String phone)
    {
        this.address = address;
        this.address2 = address2;
        this.objCity = objCity;
        this.zip = zip;
        this.phone = phone;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public City getObjCity() {
        return objCity;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }
}
