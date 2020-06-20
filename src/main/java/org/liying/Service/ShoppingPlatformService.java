package org.liying.Service;

import org.liying.model.ShoppingPlatform;
import org.liying.repository.ShoppingPlatformDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShoppingPlatformService {
    @Autowired
    private ShoppingPlatformDao shoppingPlatformDao;

    public ShoppingPlatform save(ShoppingPlatform platform){return shoppingPlatformDao.save(platform);}
    public List<ShoppingPlatform> getShoppingPlatforms(){
        return shoppingPlatformDao.getShoppingPlatforms();
    }
    public ShoppingPlatform getBy(Long Id){return shoppingPlatformDao.getBy(Id);}
    public boolean delete(ShoppingPlatform platform){
        return shoppingPlatformDao.delete(platform);
    }
    public ShoppingPlatform getShoppingPlatformsEagerBy(Long id){
        return shoppingPlatformDao.getShoppingPlatformsEagerBy(id);
    }

    public boolean delete(String platformName) {return shoppingPlatformDao.delete(platformName);}


}
