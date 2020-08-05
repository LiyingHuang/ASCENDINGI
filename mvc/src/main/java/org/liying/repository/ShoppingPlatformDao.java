package org.liying.repository;

import org.liying.model.ShoppingPlatform;
import java.util.List;

// DAO implemented by Hibernate (Query + SessionFactory + Session)
// Create the ShoppingPlatformDao interface
public interface ShoppingPlatformDao{
    // List: interface type;
    // <ShoppingPlatform>: DataStructure;
    // getShoppingPlatforms: MethodName

    // create retrieve update delete
    //CRUD
    ShoppingPlatform save(ShoppingPlatform shoppingPlatform);
    List<ShoppingPlatform> getShoppingPlatforms();  //Lazy, only sp, not the customer
    ShoppingPlatform getBy(Long id);
    boolean delete(ShoppingPlatform platform);

    ShoppingPlatform getShoppingPlatformsEagerBy(Long id);// Eager, sp with all the customers

    ShoppingPlatform update (ShoppingPlatform shoppingPlatform);
    boolean delete(String platformName);
    List<ShoppingPlatform> getShoppingPlatformsEager();
    ShoppingPlatform getShoppingPlatformByName(String platformName);

    ShoppingPlatform getShoppingPlatformAndConsumersBy(String platformName);
    List<Object[]> getShoppingPlatformAndConsumersAndOrders(String platformName);
}

