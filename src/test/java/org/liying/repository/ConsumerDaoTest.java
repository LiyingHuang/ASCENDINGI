package org.liying.repository;

import com.sun.tools.corba.se.idl.constExpr.Not;
import org.liying.model.Consumer;
import org.liying.model.Order;
import org.liying.model.ShoppingPlatform;
import org.liying.repository.ConsumerDao;
import org.liying.repository.ConsumerDaoImpl;
import org.liying.repository.ShoppingPlatformDao;
import org.liying.repository.ShoppingPlatformDaoImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;


public class ConsumerDaoTest {
    private Consumer c1;
    private Order od1;
    private Order od2;
    private ConsumerDao consumerDao;
    private OrderDao orderDao;

    @Before
    public  void init(){
        // save record in OneSide (Consumer side)
        c1 = new Consumer();
        c1.setName("C1");
        c1.setPhone("110");
        c1.setAddress("111 frow");
        consumerDao = new ConsumerDaoImpl();
        c1 = consumerDao.save(c1);

        // save records in ManySide (Order side)
        od1 = new Order();
        od1.setTotalAmount(100);
        od1.setPaymentMethod("credit card 1");

        od2 = new Order();
        od2.setTotalAmount(200);
        od2.setPaymentMethod("credit card 2");

        orderDao = new OrderDaoImpl();
        orderDao.save(od1);
        orderDao.save(od2);
    }
    @After
    public  void tearDown(){
        orderDao.delete(od1);
        orderDao.delete(od2);
        consumerDao.delete(c1);
    }
    @Test
    public  void getConsumerTest(){
        List<Consumer> consumers = consumerDao.getConsumers();
        int expectNumerOfConsumer = 1;
        assertEquals(expectNumerOfConsumer,consumers.size());
    }
    @Test
    public void getConsumerEagerByTest(){
        Consumer consumer = consumerDao.getConsumerEagerBy(c1.getId());
        assertNotNull(consumer);
        assertEquals(consumer.getName(), c1.getName());
        assertTrue(consumer.getOrders().size() > 0); // 1 ??
    }
}

/*
public class ConsumerDaoTest {
    private Consumer c1;
    private ConsumerDao consumerdao;
    @Before
    public  void init(){
        consumerdao = new ConsumerDaoImpl();
        c1 = new Consumer();
        c1.setName("C1");
        c1.setPhone("110");
        c1.setAddress("111 frow");
        c1=consumerdao.save(c1);
    }
    @After
    public  void tearDown(){
        consumerdao.delete(c1);
    }
    @Test
    public  void getConsumerTest(){
        List<Consumer> consumers = consumerdao.getConsumers();
        int expectNumerOfConsumer = 1;
        Assert.assertEquals(expectNumerOfConsumer,consumers.size());
    }
}
*/
