package Model;


public class User
{
    private String username;
    private String password;
   // private boolean active;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public boolean verify(String password)
    {
        return this.password.equals(password);
    }
}
