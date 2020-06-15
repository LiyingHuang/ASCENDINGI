package org.liying.repository;

import com.sun.tools.corba.se.idl.constExpr.Or;
import org.liying.model.Consumer;
import org.liying.model.Order;

import java.util.List;

public interface OrderDao{
    Order save(Order order); // @Before: save after add data
    List<Order> getOrders(); // @Test: get the object, then compare it's size with the expected
    Order getBy(Long id);
    boolean delete(Order order); // @After: delete the added data
    Order getOrderEagerBy(Long id);

    Order update(Order Order);
    boolean delete (String orderName);
    List<Order> getOrdersEager();
    Order getOrderByName(String orderName);
    Order getPlatformsAndOrdersBy(String orderName);
    List<Object[]> getShoppingPlatformAndConsumersAndOrders(String orderName);


}