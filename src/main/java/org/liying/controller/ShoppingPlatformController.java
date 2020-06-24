package org.liying.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingPlatformController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.GET)
    public void getShoppingPlatforms(){
        logger.debug("im in shoppingplatform Controller.");
    }
    @RequestMapping( value ="/shoppingPlatform/{Id}",method = RequestMethod.GET)
    public  void getShoppingPlatformById(@PathVariable(name = "Id")Long id){
        logger.debug(" i am in the shoppingPlatform controller get by "+id);
    }
}
