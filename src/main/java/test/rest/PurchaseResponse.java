package test.rest;

public class PurchaseResponse
{
    private String id;
    private String name;
    private String period;
    private String message;

    public PurchaseResponse(String message)
    {
        id = name = period = "";
        this.message = message;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    public String getId()
    {
        return this.id;
    }

    public void setName(String name) { this.name = name; }
    public String getName()
    {
        return this.name;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }
    public String getPeriod()
    {
        return this.period;
    }

    public String getMessage()
    {
        return this.message;
    }
    public void setMessage(String message) { this.message = message; }
}
