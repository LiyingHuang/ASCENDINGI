package org.liying.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.liying.model.ShoppingPlatform;
import org.liying.service.ShoppingPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(value = {"/shoppingPlatform","/shopping_platform","/shoppingPlatforms"})
public class ShoppingPlatformController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShoppingPlatformService shoppingPlatformService;

    /*
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.GET)
    public void getShoppingPlatforms(){
        logger.debug("im in shoppingplatform Controller.");
    }*/

    // GET /shoppingPlatform
    // RETRIEVE all sps
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.GET)
    public List<ShoppingPlatform> getShoppingPlatforms(){
        logger.debug("I'm in shoppingplatform Controller GET sps.");
        return shoppingPlatformService.getShoppingPlatforms();
    }

    // GET /shoppingPlatform/1
    // RETRIEVE one sp by passing id
    @RequestMapping( value ="/shoppingPlatform/{Id}",method = RequestMethod.GET)
    public ShoppingPlatform getShoppingPlatformById(@PathVariable(name = "Id") Long id){
        logger.debug(" I'm in the shoppingPlatform controller get by " + id);
        return shoppingPlatformService.getBy(id);
    }

    // PATCH /shoppingPlatform/1 ?name=changeName
    // UPDATE name by passing id and changeName
    @RequestMapping(value ="/shoppingPlatform/{Id}",method = RequestMethod.PATCH)
    public ShoppingPlatform updateShoppingPlatformName(@PathVariable("Id") Long Id, @RequestParam("name") String name){
        logger.info("pass in variable id :" + Id.toString() + "name:" + name);
        ShoppingPlatform sp = shoppingPlatformService.getBy(Id);
        sp.setName(name);
        sp = shoppingPlatformService.update(sp);
        return sp;
    }

    /*
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.POST)
    public void creat(@RequestBody ShoppingPlatform newObject){
        logger.debug(newObject.toString());
    }
    */

    // POST /shoppingPlatform
    // CREATE sp by passing sp(request body)
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.POST)
    public ShoppingPlatform createShoppingPlatform (@RequestBody ShoppingPlatform newObject){
        logger.debug("ShoppingPlatform:" + newObject.toString());
        //String msg = "The shopping platform was created.";
        ShoppingPlatform sp = shoppingPlatformService.save(newObject);
        //if (sp == null) {msg = "The shopping platform was not created.";}
        return sp;
    }

    /*
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.DELETE)
    public void deleteById(ShoppingPlatform sp){
        logger.debug("In controller!");
    }
    */

    // DELETE /shoppingPlatform
    // DELETE by passing sp(request body)
    // Passing Variable by @RequestBody
    @RequestMapping(value = "/shoppingPlatform/{Id}", method = RequestMethod.DELETE)
    public boolean deleteById(@PathVariable(name ="Id") Long Id){
        logger.debug("In Shopping Platform Controller Delete ...");
        ShoppingPlatform sp = shoppingPlatformService.getBy(Id);
        logger.debug("Delete Shopping Platform: " + sp.toString());
        return shoppingPlatformService.delete(sp);
    }
}
