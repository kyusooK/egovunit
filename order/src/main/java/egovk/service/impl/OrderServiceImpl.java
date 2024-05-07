package egovk.service.impl;

import egovk.domain.AcceptOrderCommand;
import egovk.domain.Order;
import egovk.domain.OrderRepository;
import egovk.domain.RejectOrderCommand;
import egovk.service.OrderService;
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

@Service("orderService")
@Transactional
public class OrderServiceImpl
    extends EgovAbstractServiceImpl
    implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        OrderServiceImpl.class
    );

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() throws Exception {
        // Get all orders
        return StreamSupport
            .stream(orderRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> getOrderById(String orderId) throws Exception {
        // Get a order by ID
        return orderRepository.findById(orderId);
    }

    @Override
    public Order createOrder(Order order) throws Exception {
        // Create a new order
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) throws Exception {
        // Update an existing order via OrderService
        if (orderRepository.existsById(order.getOrderId())) {
            return orderRepository.save(order);
        } else {
            throw processException("info.nodata.msg");
        }
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        // Delete a order
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order acceptOrder(AcceptOrderCommand acceptOrderCommand)
        throws Exception {
        // You can implement logic here, or call the domain method of the Order.

        /** Option 1-1:  implement logic here     
            Order order = new Order();
            order.setUserId(event.getUserId());

            orderRepository.save(order);   
        */

        Optional<Order> optionalOrder = orderRepository.findById(
            acceptOrderCommand.getOrderId()
        );

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // business Logic....
            order.acceptOrder(acceptOrderCommand);
            orderRepository.save(order);

            return order;
        } else {
            throw processException("info.nodata.msg");
        }
    }

    @Override
    public Order rejectOrder(RejectOrderCommand rejectOrderCommand)
        throws Exception {
        // You can implement logic here, or call the domain method of the Order.

        /** Option 1-1:  implement logic here     
            Order order = new Order();
            order.setUserId(event.getUserId());

            orderRepository.save(order);   
        */

        Optional<Order> optionalOrder = orderRepository.findById(
            rejectOrderCommand.getOrderId()
        );

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // business Logic....
            order.rejectOrder(rejectOrderCommand);
            orderRepository.save(order);

            return order;
        } else {
            throw processException("info.nodata.msg");
        }
    }
}
