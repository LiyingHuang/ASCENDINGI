package org.liying.service;

import org.liying.model.ShoppingPlatform;
import org.liying.repository.ShoppingPlatformDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


// 对dao的方法进行封装
@Service
// Store in Storage: ShoppingPlatformService shoppingPlatformService = new ShoppingPlatformService();
public class ShoppingPlatformService {
    @Autowired
    // storage: ShoppingPlatformDao sPD = new ShoppingPlatformDaoImpl();
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
    public ShoppingPlatform update(ShoppingPlatform shoppingPlatform){return shoppingPlatformDao.update(shoppingPlatform);}
    public boolean delete(String platformName) {return shoppingPlatformDao.delete(platformName);}
}
