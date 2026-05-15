package assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import assignment.model.Order;
import assignment.service.AuthenticationService;
import assignment.service.OrderService;

@RestController
public class OrderController {
    
    private final OrderService orderService;
    private final AuthenticationService authenticationService;

    public OrderController(OrderService orderService, AuthenticationService authenticationService) {
        this.orderService = orderService;
        this.authenticationService = authenticationService;
    }

    // Creates a new Order
    @PostMapping("/orders")
    public Order placeOrder(@RequestHeader("Authorization") String apiKey, @RequestParam String productID, @RequestParam int orderQuantity) {
        if (!authenticationService.isCustomer(apiKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only customers can place orders.");
        }
        int customerID = authenticationService.getCustomerId(apiKey);
        return orderService.placeOrder(customerID, productID, orderQuantity);
    }

    // View Past Orders
    @GetMapping("/orders")
    public List<Order> viewOrders(@RequestHeader("Authorization") String apiKey) {
        if (authenticationService.isOperator(apiKey)) {
            return orderService.getAllOrders();
        }
        else if (authenticationService.isCustomer(apiKey)) {
            int customerID = authenticationService.getCustomerId(apiKey);
            return orderService.getOrdersByCustomerID(customerID);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Invalid API Key.");
        }
    }

    // Cancel Order
    @DeleteMapping("/orders/{orderID}")
    public void cancelOrder(@RequestHeader("Authorization") String apiKey, @PathVariable int orderID) {
        if (!authenticationService.isCustomer(apiKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only customers can cancel orders.");
        }
        int customerID = authenticationService.getCustomerId(apiKey);
        orderService.cancelOrder(customerID, orderID);
    }

    // Update Order Status
    @PutMapping("/orders/{orderID}")
    public Order updateOrderStatus(@RequestHeader("Authorization") String apiKey, @PathVariable int orderID, @RequestParam String status) {
        if (!authenticationService.isOperator(apiKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only operators can update order status.");
        }
        Order.OrderStatus newStatus;
        try {
            newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status. Use: NOT_SHIPPED, SHIPPED, OUT_OF_STOCK");
        }
        return orderService.updateOrderStatus(orderID, newStatus);
    }

    // Get Total Revenue of Customer
    @GetMapping("/orders/{customerID}/revenue")
    public double getCustomerRevenue(@RequestHeader("Authorization") String apiKey, @PathVariable int customerID) {
        if (!authenticationService.isOperator(apiKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only operators can view customer revenue.");
        }
        return orderService.getCustomerRevenue(customerID);
    }
}