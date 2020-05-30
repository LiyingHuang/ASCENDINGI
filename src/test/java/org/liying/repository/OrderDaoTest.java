package org.liying.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.liying.model.Order;
import org.liying.model.ShoppingPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDaoTest {
    private OrderDao oderJDBCDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp(){
        oderJDBCDao = new OrderDao();
    }
    @After
    public void tearDown(){
        oderJDBCDao = null;
    }
    @Test
    public void getShoppingPlatformTest(){
        logger.debug("Start unit test for orderJDBCDao ...");
        List<Order> ordersList = oderJDBCDao.getOrders();
        assertEquals(0,ordersList.size());
    }
}
