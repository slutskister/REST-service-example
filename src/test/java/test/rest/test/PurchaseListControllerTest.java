package test.rest.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.rest.PurchaseListController;
import test.rest.PurchaseRequest;
import test.rest.PurchaseResponse;

import java.sql.SQLException;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PurchaseListControllerTest
{
    private PurchaseListController controller = null;

    @Before
    public void init()
    {
        controller = new PurchaseListController();
    }

    @After
    public void release()
    {
        controller = null;
    }

    @Test
    public void testGetList()
    {
        String list = null;
        list = controller.getPurchaseList();
        assertTrue(list != null && !list.isEmpty());
    }

    @Test
    public void testAddListItem()
    {
        String id = "1000";
        String name = "test_purchase";
        String period = "1";
        PurchaseRequest rq = new PurchaseRequest(id, name, period);

        controller.addPurchase(id, rq);
        PurchaseResponse rs = controller.getPurchase(id, rq);
        controller.dropPurchase(id, rq);

        assertTrue(rs.getId().equals(id) && rs.getName().equals(name) && rs.getPeriod().equals(period));
    }

    @Test
    public void testUpdateListItem()
    {
        String id = "1000";
        String name = "test_purchase";
        String period = "1";
        String new_period = "0";
        PurchaseRequest rq = new PurchaseRequest(id, name, period);

        controller.addPurchase(id, rq);
        rq.setPeriod(new_period);
        controller.setPeriod(id, rq);
        PurchaseResponse rs = controller.getPurchase(id, rq);
        assertTrue(rs.getPeriod().equals(new_period));

        rs = controller.dropPurchase(id, rq);
        assertTrue(rs.getMessage().equals("OK"));
    }

    @Test
    public void testDeleteListItem()
    {
        String id = "1000";
        String name = "test_purchase";
        String period = "1";
        PurchaseRequest rq = new PurchaseRequest(id, name, period);

        controller.addPurchase(id, rq);
        PurchaseResponse rs = controller.getPurchase(id, rq);
        assertTrue(rs.getId().equals(id) && rs.getName().equals(name) && rs.getPeriod().equals(period));

        rs = controller.dropPurchase(id, rq);
        assertTrue(rs.getMessage().equals("OK"));
    }
}
