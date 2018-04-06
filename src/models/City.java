package models;

public class City
{
    private int cityID;
    private String city;
    private Country objCountry;
   // private int countryID;

    public City(String cityID, String city, Country country)
    {
        this.cityID = Integer.parseInt(cityID);
        this.city = city;
        this.objCountry = country;
    }
    public City(String city, Country country)
    {
        this.city = city;
        this.objCountry = country;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCity() {
        return city;
    }

    public Country getObjCountry() {
        return objCountry;
    }
}
