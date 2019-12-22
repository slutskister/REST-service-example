package test.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

@RestController
@RequestMapping("/purchases")
public class PurchaseListController
{
    private final String m_db_type = "ORACLE"; //CSV";
    private final String m_conn_str = ""; //"/db";
    private JdbcDataSource m_ds = new JdbcDataSource();

    public PurchaseListController()
    {
        try
        {
            m_ds.Connect(m_db_type, new File(".").getAbsolutePath() + m_conn_str);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping("/list")
    public String getPurchaseList()
    {
        String resp = "";

        try
        {
            LinkedList<HashMap<String, String>> list = m_ds.getPurchaseList();
            Iterator<HashMap<String, String>> iter_rec = list.iterator();

            while(iter_rec.hasNext())
            {
                HashMap<String, String> row = iter_rec.next();

                resp += row.get("Name");
                resp += (row.get("Period").equals("0") ? "" : " (периодически)");

                if(iter_rec.hasNext())
                  resp += ", ";
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return resp;
    }

    @PostMapping("/add")
    public PurchaseResponse addPurchase(@RequestParam(required = true) String id, @RequestBody PurchaseRequest item)
    {
        PurchaseResponse rs = new PurchaseResponse("");

        try
        {
            m_ds.addPurchase(id, item.getName(), item.getPeriod());
            rs.setMessage("OK");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return rs;
    }

    @GetMapping("/get")
    public PurchaseResponse getPurchase(@RequestParam(required = true) String id, @RequestBody PurchaseRequest item)
    {
        PurchaseResponse rs = new PurchaseResponse("");

        try
        {
            HashMap<String, String> row = m_ds.getPurchase(id);
            rs.setId(row.get("ID"));
            rs.setName(row.get("Name"));
            rs.setPeriod(row.get("Period"));
            rs.setMessage("OK");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return rs;
    }

    @PutMapping("/period")
    public PurchaseResponse setPeriod(@RequestParam(required = true) String id, @RequestBody PurchaseRequest item)
    {
        PurchaseResponse rs = new PurchaseResponse("");

        try
        {
            m_ds.setPeriod(id, item.getPeriod());
            rs.setMessage("OK");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return rs;
    }

    @DeleteMapping("/drop")
    public PurchaseResponse dropPurchase(@RequestParam(required = true) String id, @RequestBody PurchaseRequest item)
    {
        PurchaseResponse rs = new PurchaseResponse("");

        try
        {
            m_ds.dropPurchase(id);
            rs.setMessage("OK");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return rs;
    }
}
