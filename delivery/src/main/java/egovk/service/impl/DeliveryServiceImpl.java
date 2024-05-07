package egovk.service.impl;

import egovk.domain.CompleteDeliveryCommand;
import egovk.domain.CreateDeliveryCommand;
import egovk.domain.Delivery;
import egovk.domain.DeliveryRepository;
import egovk.service.DeliveryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("deliveryService")
@Transactional
public class DeliveryServiceImpl
    extends EgovAbstractServiceImpl
    implements DeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        DeliveryServiceImpl.class
    );

    @Autowired
    DeliveryRepository deliveryRepository;

    @Override
    public List<Delivery> getAllDeliveries() throws Exception {
        // Get all deliveries
        return StreamSupport
            .stream(deliveryRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Delivery> getDeliveryById(String deliveryId)
        throws Exception {
        // Get a delivery by ID
        return deliveryRepository.findById(deliveryId);
    }

    @Override
    public Delivery createDelivery(Delivery delivery) throws Exception {
        // Create a new delivery
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) throws Exception {
        // Update an existing delivery via DeliveryService
        if (deliveryRepository.existsById(delivery.getDeliveryId())) {
            return deliveryRepository.save(delivery);
        } else {
            throw processException("info.nodata.msg");
        }
    }

    @Override
    public void deleteDelivery(String deliveryId) throws Exception {
        // Delete a delivery
        deliveryRepository.deleteById(deliveryId);
    }

    @Override
    public Delivery completeDelivery(
        CompleteDeliveryCommand completeDeliveryCommand
    ) throws Exception {
        // You can implement logic here, or call the domain method of the Delivery.

        /** Option 1-1:  implement logic here     
            Delivery delivery = new Delivery();
            delivery.setUserId(event.getUserId());

            deliveryRepository.save(delivery);   
        */

        Optional<Delivery> optionalDelivery = deliveryRepository.findById(
            completeDeliveryCommand.getDeliveryId()
        );

        if (optionalDelivery.isPresent()) {
            Delivery delivery = optionalDelivery.get();

            // business Logic....
            delivery.completeDelivery(completeDeliveryCommand);
            deliveryRepository.save(delivery);

            return delivery;
        } else {
            throw processException("info.nodata.msg");
        }
    }
}
