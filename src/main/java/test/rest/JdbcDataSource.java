package test.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class JdbcDataSource
{
    private Connection m_connect = null;

    public Iterator<HashMap<String, String>> m_iterator = null;

    public JdbcDataSource()
    {
    }
    
    public Connection Connect(String src_type, String conn_str) throws SQLException
    {
        String driver = GetJdbcDriverName(src_type);
        
        try
        {
            // Load JDBC driver
            Class.forName(driver);
            
            // Create the specified connection
            m_connect = DriverManager.getConnection(GetJdbcConnetionString(src_type) + conn_str);
        }
        catch(ClassNotFoundException ex)
        {
            String msg = String.format("Failed to find JDBC driver (%s)", driver);
            SQLException jdbc_ex = new SQLException(msg);
            jdbc_ex.setStackTrace(ex.getStackTrace());
            throw jdbc_ex;
        }
        
        return m_connect;
    }

    public LinkedList<HashMap<String, String>> getPurchaseList() throws SQLException
    {
        String sql = "select Name,Period from Purchases";
        LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();

        ResultSet rs = m_connect.createStatement().executeQuery(sql);
        while(rs.next())
        {
            HashMap<String, String> record = new HashMap<>();

            // Save the row
            for(int j=0; j < rs.getMetaData().getColumnCount(); j++)
            {
                record.put(rs.getMetaData().getColumnName(j + 1), rs.getString(j + 1));
            }

            // Add the row to the list of rows
            list.add(record);
        }

        rs.close();

        return list;
    }

    public HashMap<String, String> getPurchase(String id) throws SQLException
    {
        String sql = String.format("select ID,Name,Period from Purchases where ID=%s", id);
        HashMap<String, String> row = new HashMap<String, String>();

        ResultSet rs = m_connect.createStatement().executeQuery(sql);
        if(rs.next())
        {
            // Save the row
            for(int j=0; j < rs.getMetaData().getColumnCount(); j++)
            {
                row.put(rs.getMetaData().getColumnName(j + 1), rs.getString(j + 1));
            }
        }

        rs.close();

        return row;
    }

    public void addPurchase(String id, String name, String period) throws SQLException
    {
        String sql = String.format("insert into Purchases(ID,Name,Period) values(%s,'%s',%s)", id, name, period);

        m_connect.createStatement().executeQuery(sql);
    }

    public void setPeriod(String id, String period) throws SQLException
    {
        String sql = String.format("update Purchases set Period='%s' where ID=%s", period, id);

        m_connect.createStatement().executeQuery(sql);
    }

    public void dropPurchase(String id) throws SQLException
    {
        String sql = String.format("delete Purchases where ID=%s", id);

        m_connect.createStatement().executeQuery(sql);
    }

    public void release()
    {
        try
        {
            // Close the connection
            if(m_connect != null)
              m_connect.close();
        }
        catch(SQLException ex)
        {
        }
        finally
        {
            m_connect = null;
        }
    }
        
    public static String GetJdbcDriverName(String src_type)
    {
        String name = null;
        
        switch(src_type.toUpperCase())
        {
            case "CSV":
                name = "org.relique.jdbc.csv.CsvDriver";
                break;
                
            case "ORACLE":
                name = "oracle.jdbc.OracleDriver";
                break;
        }
        
        return (name != null) ? name : src_type;
    }
    
    public static String GetJdbcConnetionString(String src_type)
    {
        String connect = null;
        
        switch(src_type.toUpperCase())
        {
            case "CSV":
                connect = "jdbc:relique:csv:/";
                break;
                
            case "ORACLE":
                connect = "jdbc:oracle:thin:";
                break;
        }
        
        return (connect != null) ? connect : "";
    }
}
