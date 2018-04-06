package models;

public class Country
{
    private int countryID;
    private String country;

    public Country (String countryID, String country)
    {
        this.countryID = Integer.parseInt(countryID);
        this.country = country;
    }

    public int getCountryID() {
        return countryID;
    }

    public String toString(){
        return country;
    }

    public String getCountry() {
       return country;
    }
}
