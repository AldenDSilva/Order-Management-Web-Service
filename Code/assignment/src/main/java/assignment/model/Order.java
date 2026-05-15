package assignment.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER") // 'ORDER' is a reserved keyword in SQL, AI found my error and suggested to use this line
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates a Unique order Id, this line was suggested by AI
    private int orderID;
    private int customerID;
    private String productID;
    private int orderQuantity;
    public enum OrderStatus { SHIPPED, NOT_SHIPPED, OUT_OF_STOCK }
    @Enumerated(EnumType.STRING) // This line was suggested by AI
    private OrderStatus status;

    public Order() {
    }

    public Order(int customerID, String productID, int orderQuantity) {
        this.customerID = customerID;
        this.productID = productID;
        this.orderQuantity = orderQuantity;
        this.status = OrderStatus.NOT_SHIPPED;
    }

    public int getOrderID() { 
        return orderID; 
    }

    public int getCustomerID() { 
        return customerID; 
    }
    public String getProductID() { 
        return productID; 
    }

    public int getOrderQuantity() { 
        return orderQuantity; 
    }

    public OrderStatus getStatus() { 
        return status; 
    }

    public void setStatus(OrderStatus status) { 
        this.status = status; 
    }
}