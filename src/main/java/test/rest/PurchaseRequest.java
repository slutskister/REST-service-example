package test.rest;

public class PurchaseRequest
{
    private String id;
    private String name;
    private String period;

    public PurchaseRequest(String id, String name, String period)
    {
        this.id = id;
        this.name = name;
        this.period = period;
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
}
