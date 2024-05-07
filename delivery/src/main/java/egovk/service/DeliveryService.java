package egovk.service;

import egovk.domain.*;
import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    List<Delivery> getAllDeliveries() throws Exception;
    Optional<Delivery> getDeliveryById(String deliveryId) throws Exception;
    Delivery createDelivery(Delivery delivery) throws Exception;
    Delivery updateDelivery(Delivery delivery) throws Exception;
    void deleteDelivery(String deliveryId) throws Exception;

    Delivery completeDelivery(CompleteDeliveryCommand completeDeliveryCommand)
        throws Exception;
}
