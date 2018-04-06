package models;

public class User
{
    private String username;
    private String password;
    private User objUser;

    public User getObjUser() {
        return objUser;
    }

    //private String active; NEED TO VERIFY IT IS AN ACTIVE USER

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public boolean verify(String password)
    {
        return this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
