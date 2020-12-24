package org.liying.jdbc;
import org.liying.jdbc.ShoppingPlatformJDBC;
import org.liying.jdbc.ShoppingPlatformJDBCDao;
import org.liying.model.ShoppingPlatform;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ShoppingPlatformJDBCDaoTest {
    private ShoppingPlatformJDBCDao shoppingPlatformJDBCDao;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp(){
        shoppingPlatformJDBCDao = new ShoppingPlatformJDBCDao();
    }
    @After
    public void tearDown(){
        shoppingPlatformJDBCDao = null;
    }
    @Test
    public void getShoppingPlatformTest(){
        logger.debug("Start unit test for shoppingPlatformJDBCDao ...");
        List<ShoppingPlatformJDBC> shoppingPlatformList = shoppingPlatformJDBCDao.getShoppingPlatforms();
        assertEquals(1,shoppingPlatformList.size()) ;
        // getShoppingPlatform() return List
        //assertEquals(shoppingPlatformDao.getShoppingPlatform().size());
    }
}
