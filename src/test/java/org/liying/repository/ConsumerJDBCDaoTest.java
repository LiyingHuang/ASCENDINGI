package org.liying.repository;
import org.liying.jdbc.ConsumerJDBCDao;
import org.liying.model.Consumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ConsumerDaoTest {
    private ConsumerJDBCDao consumerJDBCDao;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Before
    public void setUp(){
        consumerJDBCDao = new ConsumerJDBCDao();
    }
    @After
    public void tearDown(){
        consumerJDBCDao  = null;
    }
    @Test
    public void getShoppingPlatformTest(){
        logger.debug("Start unit test for shoppingPlatformJDBCDao ...");
        List<Consumer> consumers = consumerJDBCDao.getConsumers();
        assertEquals(0,consumers.size());
    }
}
