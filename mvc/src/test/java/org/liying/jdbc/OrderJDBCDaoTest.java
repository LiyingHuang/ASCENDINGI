package org.liying.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.liying.jdbc.OrderJDBC;
import org.liying.jdbc.OrderJDBCDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderJDBCDaoTest {
    private OrderJDBCDao orderJDBCDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp(){
        orderJDBCDao = new OrderJDBCDao();
    }
    @After
    public void tearDown(){
        orderJDBCDao = null;
    }
    @Test
    public void getShoppingPlatformTest(){
        logger.debug("Start unit test for orderJDBCDao ...");
        List<OrderJDBC> ordersList = orderJDBCDao.getOrders();
        assertEquals(0,ordersList.size());
    }
}
