package org.liying.Service;

import org.liying.model.Consumer;
import org.liying.repository.ConsumerDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ConsumerService {
    @Autowired
    private ConsumerDao consumerDao;

    public Consumer save(Consumer consumer){
        return consumerDao.save(consumer);
    }
    public List<Consumer> getConsumers(){
        return consumerDao.getConsumers();
    }
    public Consumer getBy (Long id){
        return consumerDao.getBy(id);
    }
    public boolean delete (Consumer consumer){
        return consumerDao.delete(consumer);
    }
    public Consumer getConsumerEagerBy(Long id){
        return consumerDao.getConsumerEagerBy(id);
    }
}
