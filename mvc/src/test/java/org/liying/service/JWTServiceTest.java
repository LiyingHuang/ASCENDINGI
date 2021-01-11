package org.liying.service;

import io.jsonwebtoken.Claims;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liying.ApplicationBootstrap;
import org.liying.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class JWTServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JWTService jwtService;
    //JWTService jwtService = new JWTService();
    @Autowired private UserService userService;

    private User u;

    @Before
    public void setUp(){
        u = new User();
        u.setId(10l);
        u.setName("liying");
    }
    @After
    public void tearDown(){
    }

    @Test
    public void generateTokenTest(){
        String token = jwtService.generateToken(u);
        System.out.println("Token : " + token);
        assertNotNull(token);
    }

    @Test
    public void generateTokenTest2(){
        User u = userService.getUserByName("dwang");
        String token = jwtService.generateToken(u);
        System.out.println("Token : " + token);
        assertNotNull(token);
    }

    @Test
    public void validateTokenFormat(){

        String token = jwtService.generateToken(u);
        Claims claims = jwtService.decryptJwtToken(token);
        Assert.assertEquals(claims.getSubject(),"liying");

        String[] array = token.split("\\.");
        assertNotNull(token);
        Assert.assertEquals(array.length, 3);
    }

    @Test
    public void decryptJwtToken(){
        String token = jwtService.generateToken(u);
        Claims claims = jwtService.decryptJwtToken(token);
        assertNotNull(token);
        Assert.assertEquals(claims.getSubject(),"liying");
    }
}
