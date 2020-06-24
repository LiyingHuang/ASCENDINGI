package org.liying.service;

import org.liying.model.Consumer;
import org.liying.model.Order;
import org.liying.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public Order save(Order order){
        return orderDao.save(order);
    }
    public List<Order> getOrders(){
        return orderDao.getOrders();
    }
    public Order getBy(Long id){
        return orderDao.getBy(id);
    }
    public boolean delete(Order order){
        return orderDao.delete(order);
    }
    public Order getBy(Consumer c){
        return orderDao.getBy(c);
    }
}
