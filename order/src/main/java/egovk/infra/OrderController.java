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
// @RequestMapping(value="/orders")
public class OrderController {

    @Resource(name = "orderService")
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() throws Exception {
        // Get all orders via OrderService
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public Optional<Order> getOrderById(@PathVariable String orderId)
        throws Exception {
        // Get a order by ID via OrderService
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) throws Exception {
        // Create a new order via OrderService
        return orderService.createOrder(order);
    }

    @PutMapping("/orders/{id}")
    public Order updateOrder(
        @PathVariable String orderId,
        @RequestBody Order order
    ) throws Exception {
        // Update an existing order via OrderService
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrder(@PathVariable String orderId) throws Exception {
        // Delete a order via OrderService
        orderService.deleteOrder(orderId);
    }

    @RequestMapping(
        value = "/orders/{id}/acceptorder",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Order acceptOrder(
        @PathVariable(value = "id") String orderId,
        @RequestBody AcceptOrderCommand acceptOrderCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        return orderService.acceptOrder(acceptOrderCommand);
    }

    @RequestMapping(
        value = "/orders/{id}/rejectorder",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Order rejectOrder(
        @PathVariable(value = "id") String orderId,
        @RequestBody RejectOrderCommand rejectOrderCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        return orderService.rejectOrder(rejectOrderCommand);
    }
}
