package models;

public class Contact
{
    private int customerID;
    private String customerName;
    private Address objAddress;
    private boolean active;
  //  private int address;

    public Contact(String customerID, String customerName, Address objAddress, boolean active)
    {
        this.customerID = Integer.parseInt(customerID);
        this.customerName = customerName;
        this.objAddress = objAddress;
        this.active = active;
    }

    public Contact(String customerName, Address objAddress, boolean active)
    {
        this.customerName = customerName;
        this.objAddress = objAddress;
        this.active = active;
    }

    public Contact (String customerID, String customerName)
    {
        this.customerID = Integer.parseInt(customerID);
        this.customerName = customerName;
    }

    public String toString(){return customerName;}

    public int getCustomerID()
    {
        return customerID;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public Address getObjAddress()
    {
        return objAddress;
    }

    public boolean getActive()
    {
        return active;
    }
}