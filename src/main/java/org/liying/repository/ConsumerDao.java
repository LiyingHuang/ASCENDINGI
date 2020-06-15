package org.liying.repository;
import org.liying.model.Consumer;
import java.util.List;
public interface ConsumerDao {
    Consumer save(Consumer consumer);   // create
    List<Consumer> getConsumers();      // retrieve
    Consumer getBy (Long id);           // update
    boolean delete (Consumer consumer); // delete
    Consumer getConsumerEagerBy(Long id);

    Consumer update(Consumer consumer);
    boolean delete (String consumerName);
    List<Consumer> getConsumersEager();
    Consumer getConsumerByName(String consumerName);
    Consumer getConsumerAndOrdersBy(String consumerName);
    List<Object[]> getShoppingPlatformAndConsumersAndOrders(String consumerName);
}
