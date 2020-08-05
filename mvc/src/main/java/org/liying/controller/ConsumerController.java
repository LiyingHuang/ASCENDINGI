package org.liying.controller;

import org.liying.model.Consumer;
import org.liying.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//@RequestMapping(value = {"/consumer","/consumers"})
public class ConsumerController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConsumerService consumerService;

    // POST /consumer
    @RequestMapping(value = "/consumer", method = RequestMethod.POST)
    public Consumer createConsumer(@RequestBody Consumer newConsumer){
        logger.debug("I'm in Consumer Controller Creat ...");
        Consumer consumer = consumerService.save(newConsumer);
        return consumer;
    }

    // GET  /Consume
    @RequestMapping(value = "/consumer", method = RequestMethod.GET)
    public List<Consumer> getConsumers(){
        logger.debug("I'm in Consumer Controller...");
        return consumerService.getConsumers();
    }

    // GET /Consumer/94
    @RequestMapping(value = "/consumer/{Id}", method = RequestMethod.GET)
    public Consumer getConsumerById(@PathVariable(name = "Id") Long Id){
        logger.debug("I'm in Consumer Controller Get By...");
        return consumerService.getConsumerEagerBy(Id);
    }

    // PATCH /consumer/{Id}
    // UPDATE customerName by passing id and changeName
    @RequestMapping(value = "/consumer/{Id}", method = RequestMethod.PATCH)
    public Consumer updateConsumer(@PathVariable(name = "Id") Long Id, @RequestParam("name") String name){
        logger.debug("I'm in Consumer Controller Update By Consumer..."+ Id + name);
        Consumer consumer = consumerService.getBy(Id);
        consumer.setName(name);
        consumer = consumerService.update(consumer);
        return consumer;
    }

    // DELETE(by id) /consumer/{Id}
    @RequestMapping(value = "/consumer/{Id}", method = RequestMethod.DELETE)
    public boolean deleteConsumer(@PathVariable Long Id){
        logger.debug("I'm in Consumer Controller Delete ...");
        Consumer consumer = consumerService.getBy(Id);
        logger.debug("Delete Shopping Platform: " + consumer.toString());
        return consumerService.delete(consumer);
    }

}
