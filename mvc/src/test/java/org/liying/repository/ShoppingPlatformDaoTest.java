package org.liying.repository;

import org.junit.Assert;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liying.ApplicationBootstrap;
import org.liying.model.Consumer;
import org.liying.model.ShoppingPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.*;

/*

spring集成junit
 1. 让SpringJunit负责创建spring容器，但是需要将配置文件名称告诉它
 2. 将需要进行测试的bean直接在测试类中进行注入

步骤：
1 导入spring集成junit坐标 （spring没有自带test）
2 使用@runwith
3 使用@ContextConfiguration指定配置文件或者配置类 （我们用的是@SpringBootTest）
4 用@Autowired注入需要测试的对象
5 创建测试方法进行测试

*/

@RunWith(SpringRunner.class)   // 声明找spring的内核跑测试，先完成一些东西，spring再去找junit
@SpringBootTest(classes = ApplicationBootstrap.class)  // 全注解方式：告诉它配置文件的字节码文件
// 3rd include OneToMany
// 4th include SPRING @Autowired
public class ShoppingPlatformDaoTest {
    @Autowired //shoppingPlatformDao = new ShoppingPlatformDaoImpl();
    private ShoppingPlatformDao shoppingPlatformDao;
    @Autowired
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
//        shoppingPlatformDao = new ShoppingPlatformDaoImpl();
        sp1 = shoppingPlatformDao.save(sp1);

//        consumerDao = new ConsumerDaoImpl();
        // save record in Many side
        c1 = new Consumer();
        c1.setAddress("c1_ADDRESS");
        c1.setName("c1_name");
        c1.setPhone("c1_phone12345");
        c1.setShoppingPlatform(sp1);
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
        int expectedNumberOfShoppingPlatform = 2;
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
