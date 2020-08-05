package org.liying.controller;

import org.liying.model.Order;
import org.liying.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderService orderService;
    // GET /order
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<Order> getOrders(){
        logger.debug("I'm in Order Controller GET Orders ...");
        return orderService.getOrders();
    }
    // GET /order/{Id}
    @RequestMapping(value = "/order/{Id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable(name = "Id") Long id){
        logger.debug("I'm in Order Controller GET OrdersById ...");
        return orderService.getBy(id);
    }
    // PATCH/UPDATE(paymentMethod by id) /order/{Id}
    @RequestMapping(value = "/order/{Id}", method = RequestMethod.PATCH)
    public Order updatePaymentMethodById(@PathVariable(name = "Id") Long id, @RequestParam(name = "payMethod") String payMethod){
        logger.debug("I'm in Order Controller UPDATE PaymentMethodById ...");
        Order order = orderService.getBy(id);
        order.setPaymentMethod(payMethod);
        order = orderService.update(order);
        return order;
    }
    // POST/CREATE /order
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Order newOrder){
        logger.debug("I'm in Order Controller CREATE/POST Order ..." );
        Order order = orderService.save(newOrder);
        logger.debug("Create Order : " + order.toString());
        return order;
    }
    // DELETE /order/{Id}
    @RequestMapping(value = "/order/{Id}", method = RequestMethod.DELETE)
    public boolean deleteOrderById(@PathVariable(name = "Id") Long id){
        logger.debug("I'm in Order Controller DELETE Order ..." );
        Order order = orderService.getBy(id);
        logger.debug("Delete Order : " + order.toString());
        return orderService.delete(order);
    }
}
