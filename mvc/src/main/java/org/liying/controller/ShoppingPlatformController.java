package org.liying.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.liying.model.ShoppingPlatform;
import org.liying.service.ShoppingPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // @Controller用于web层上实例bean--配合组件扫描一起用
//@RequestMapping(value = {"/shoppingPlatform","/shopping_platform","/shoppingPlatforms"}) //用于和下面的url进行拼接
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
    // requestmapping的作用就是把一个 请求的虚拟地址 映射到某一个具体的方法上
    // 用于建立url和处理请求方法之间的对应关系
    // http://localhost:8080/shoppingPlatform
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.GET)
    public List<ShoppingPlatform> getShoppingPlatforms(){
        logger.debug("I'm in shopping platform Controller GET sps."); // console
        return shoppingPlatformService.getShoppingPlatforms(); // 返回到页面
    }

    // GET /shoppingPlatform/1
    // RETRIEVE one sp by passing id
    @RequestMapping( value ="/shoppingPlatform/{Id}",method = RequestMethod.GET)
    public ShoppingPlatform getShoppingPlatformById(@PathVariable(name = "Id") Long id){
        logger.debug(" I'm in the shoppingPlatform controller get by " + id);
        return shoppingPlatformService.getBy(id);
    }

    // PATCH /shoppingPlatform/1 ? name=changeName
    // UPDATE name by passing id and changeName
    @RequestMapping(value ="/shoppingPlatform/{Id}",method = RequestMethod.PATCH,params = "name")
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


    // spring 可以自动封装一个ShoppingPlatform对象
    // POST /shoppingPlatform
    // CREATE sp by passing sp(request body)
    @RequestMapping(value = "/shoppingPlatform", method = RequestMethod.POST)
    public ShoppingPlatform createShoppingPlatform (@RequestBody ShoppingPlatform newObject){
        logger.debug("ShoppingPlatform:" + newObject.toString());
        //String msg = "The shopping platform was created.";
        ShoppingPlatform sp = shoppingPlatformService.save(newObject);  // 存入数据库
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
