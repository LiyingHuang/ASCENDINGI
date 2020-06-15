package org.liying.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.liying.model.Order;

import java.util.List;

public class OrderDaoTest {
    private OrderDao orderDao;
    private Order od1;
    @Before
    public void setUp(){
        orderDao = new OrderDaoImpl();
        od1 = new Order();
        od1.setPaymentMethod("Cash");
        od1.setTotalAmount(100);
        od1 = orderDao.save(od1);
    }
    @After
    public void tearDown(){orderDao.delete(od1);}
    @Test
    public void getOrderTest(){
        List<Order> orders = orderDao.getOrders();
        int expectedNumberOfOrders = 1;
        Assert.assertEquals(expectedNumberOfOrders, orders.size());
    }
}
