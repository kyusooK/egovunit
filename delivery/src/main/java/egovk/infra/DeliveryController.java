package egovk.infra;

import egovk.domain.*;
import egovk.service.*;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping(value="/deliveries")
public class DeliveryController {

    @Resource(name = "deliveryService")
    private DeliveryService deliveryService;

    @GetMapping("/deliveries")
    public List<Delivery> getAllDeliveries() throws Exception {
        // Get all deliveries via DeliveryService
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/deliveries/{id}")
    public Optional<Delivery> getDeliveryById(@PathVariable String deliveryId)
        throws Exception {
        // Get a delivery by ID via DeliveryService
        return deliveryService.getDeliveryById(deliveryId);
    }

    @PostMapping("/deliveries")
    public Delivery createDelivery(@RequestBody Delivery delivery)
        throws Exception {
        // Create a new delivery via DeliveryService
        return deliveryService.createDelivery(delivery);
    }

    @PutMapping("/deliveries/{id}")
    public Delivery updateDelivery(
        @PathVariable String deliveryId,
        @RequestBody Delivery delivery
    ) throws Exception {
        // Update an existing delivery via DeliveryService
        return deliveryService.updateDelivery(delivery);
    }

    @DeleteMapping("/deliveries/{id}")
    public void deleteDelivery(@PathVariable String deliveryId)
        throws Exception {
        // Delete a delivery via DeliveryService
        deliveryService.deleteDelivery(deliveryId);
    }

    @RequestMapping(
        value = "/deliveries/{id}/completedelivery",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Delivery completeDelivery(
        @PathVariable(value = "id") String deliveryId,
        @RequestBody CompleteDeliveryCommand completeDeliveryCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        return deliveryService.completeDelivery(completeDeliveryCommand);
    }
}
