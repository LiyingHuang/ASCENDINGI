package org.liying.repository;

import org.junit.Assert;
import org.junit.*;
import org.junit.Test;
import org.liying.model.Consumer;
import org.liying.model.ShoppingPlatform;
import java.util.List;

import static org.junit.Assert.*;

// 3rd include OneToMany
public class ShoppingPlatformDaoTest {

    private ShoppingPlatformDao shoppingPlatformDao;
    private ConsumerDao consumerDao;
    private ShoppingPlatform sp1;
    private Consumer c1;
    private Consumer c2;

    @Before
    public void setUp(){
        // save record in One side
        sp1 = new ShoppingPlatform();
        sp1.setName("sp1");
        sp1.setShippingMethod("sp1_fedex");
        sp1.setWebsite("www.sp1.com");
        shoppingPlatformDao = new ShoppingPlatformDaoImpl();
        sp1 = shoppingPlatformDao.save(sp1);

        // save record in Many side
        c1 = new Consumer();
        c1.setAddress("c1_ADDRESS");
        c1.setName("c1_name");
        c1.setPhone("c1_phone12345");
           //  use sp_id as the FK of the class sp
        c1.setShoppingPlatform(sp1);
        consumerDao = new ConsumerDaoImpl();
        consumerDao.save(c1);

        c2 = new Consumer();
        c2.setAddress("c2_ADDRESS");
        c2.setName("c2_name");
        c2.setPhone("c2_phone12345");
        c2.setShoppingPlatform(sp1);
        consumerDao.save(c2);
    }
    @After
    public void tearDown(){
        // delete record in Many side
        consumerDao.delete(c1);
        consumerDao.delete(c2);
        // delete record in One side
        shoppingPlatformDao.delete(sp1);
    }
    @Test
    public void getShoppingPlatformsTest(){
        List<ShoppingPlatform> shoppingPlatforms = shoppingPlatformDao.getShoppingPlatforms();
        int expectedNumberOfShoppingPlatform = 5;
        Assert.assertEquals(expectedNumberOfShoppingPlatform, shoppingPlatforms.size());
    }

    @Test
    public void getShoppingPlatformEagerByTest(){
        ShoppingPlatform shoppingPlatform = shoppingPlatformDao.getShoppingPlatformsEagerBy(sp1.getId());
        assertNotNull(shoppingPlatform); //?
        assertEquals(shoppingPlatform.getName(), sp1.getName());
        assertTrue(shoppingPlatform.getConsumers().size() > 0);
    }
}
/*
// 1st
public class ShoppingPlatformDaoTest2 {
    private ShoppingPlatformDao shoppingPlatformDao;
    @Before
    public void setUp(){
        shoppingPlatformDao = new ShoppingPlatformDaoImpl();
    }
    @Test
    public void getShoppingPlatformsTest(){
        List<ShoppingPlatform> shoppingPlatforms = shoppingPlatformDao.getShoppingPlatforms();
        int expectedNumberOfShoppingPlatform = 4;
        Assert.assertEquals(expectedNumberOfShoppingPlatform, shoppingPlatforms.size());
    }
}

// 2nd 
public class ShoppingPlatformDaoTest {
    private ShoppingPlatformDao shoppingPlatformDao;
    private ShoppingPlatform sp1;
    @Before
    public void setUp(){
        shoppingPlatformDao = new ShoppingPlatformDaoImpl();
        sp1 = new ShoppingPlatform();
        sp1.setName("sp1");
        sp1.setShippingMethod("sp1_fedex");
        sp1.setWebsite("www.sp1.com");
        sp1 = shoppingPlatformDao.save(sp1);
    }
    @After
    public void tearDown(){
        shoppingPlatformDao.delete(sp1);
    }
    @Test
    public void getShoppingPlatformsTest(){
        List<ShoppingPlatform> shoppingPlatforms = shoppingPlatformDao.getShoppingPlatforms();
        int expectedNumberOfShoppingPlatform = 5;
        Assert.assertEquals(expectedNumberOfShoppingPlatform, shoppingPlatforms.size());
    }

}
*/
