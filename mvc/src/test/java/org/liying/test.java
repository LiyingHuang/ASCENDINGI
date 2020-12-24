package org.liying;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class test {
    @Test
    public void sum(){
        int a = 1, b = 2;
        Assert.assertEquals(b,a+a);
    }
}
