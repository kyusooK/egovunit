package egovk.service;

import egovk.domain.*;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders() throws Exception;
    Optional<Order> getOrderById(String orderId) throws Exception;
    Order createOrder(Order order) throws Exception;
    Order updateOrder(Order order) throws Exception;
    void deleteOrder(String orderId) throws Exception;

    Order acceptOrder(AcceptOrderCommand acceptOrderCommand) throws Exception;
    Order rejectOrder(RejectOrderCommand rejectOrderCommand) throws Exception;
}
