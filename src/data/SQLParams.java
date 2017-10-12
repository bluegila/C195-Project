package data;


public class SQLParams
{
    private String name;
    private Object value;
    private int type;

    public SQLParams(String name, Object value, int type)
    {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public Object getValue()
    {
        return value;
    }

    public int getType()
    {
        return type;
    }

}
