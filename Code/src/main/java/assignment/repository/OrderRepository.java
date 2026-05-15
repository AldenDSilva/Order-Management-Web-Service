package assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import assignment.model.Order;

// To create a database repository for for the order table using Order as the entity and Integer as the primary key type
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Find orders by customer ID
    List<Order> findOrdersByCustomerID(int customerID);

    // Sum total revenue amount of a customers shipped orders (A.I Generated Query)
    @Query("SELECT SUM(o.orderQuantity * p.productPrice) " +
           "FROM Order o JOIN Product p ON o.productID = p.productID " +
           "WHERE o.customerID = :customerID AND o.status = assignment.model.Order.OrderStatus.SHIPPED")
    Double getTotalRevenueByCustomer(@Param("customerID") int customerID);
}