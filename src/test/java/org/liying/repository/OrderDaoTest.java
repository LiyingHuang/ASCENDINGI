package org.liying.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liying.ApplicationBootstrap;
import org.liying.model.Consumer;
import org.liying.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)

public class OrderDaoTest {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ConsumerDao consumerDao;
    private Consumer c;
    private Order od1;
    @Before
    public void setUp(){
//        orderDao = new OrderDaoImpl();
        od1 = new Order();
        od1.setPaymentMethod("Cash");
        od1.setTotalAmount(100);

        c = new Consumer();
        c.setPhone("123");
        c.setName("emily");
        c.setAddress("add1");
//        c.setOrder(od1);
        od1.setConsumer(c);

        od1 = orderDao.save(od1);
        c = consumerDao.save(c);
    }
    @After
    public void tearDown(){
        consumerDao.delete(c);
        orderDao.delete(od1);
    }
    @Test
    public void getOrderTest(){
        List<Order> orders = orderDao.getOrders();
        int expectedNumberOfOrders = 1;
        Assert.assertEquals(expectedNumberOfOrders, orders.size());
    }
    @Test
    public void getByTest(){
        Order order = orderDao.getBy(c);
        Assert.assertNotNull(order);
//        Assert.assertEquals(order.getPaymentMethod(), od1.getPaymentMethod());
//        Assert.assertEquals(order.getConsumer().getName(),c.getName());
    }
}


/*
// 1st
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
    @Test
    public void getByTest(){

    }
}
 */