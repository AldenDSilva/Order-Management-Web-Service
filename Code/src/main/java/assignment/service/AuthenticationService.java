package assignment.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import assignment.model.Customer;

@Service
public class AuthenticationService {
    
    // API Keys stored in an immutable map
    private final Map<String, String> apiKeys = Map.of(
        "CUST1KEY", "CUSTOMER",
        "CUST2KEY", "CUSTOMER",
        "CUST3KEY", "CUSTOMER",
        "OPER1KEY", "OPERATOR"
    );

    // Map of API keys to customer information
    private final Map<String, Customer> customers = Map.of(
        "CUST1KEY", new Customer(123, "John Doe", "john@gmail.com", "ABC Building"),
        "CUST2KEY", new Customer(456, "Sara Smith", "sara@gmail.com", "123 Building"),
        "CUST3KEY", new Customer(789, "Ali Khan", "ali@gmail.com", "XYZ Building")
    );

    // Returns true if the API key belongs to a customer
    public boolean isCustomer(String apiKey) {
        return "CUSTOMER".equals(apiKeys.get(apiKey));
    }

    // Returns true if the API key belongs to an operator
    public boolean isOperator(String apiKey) {
        return "OPERATOR".equals(apiKeys.get(apiKey));
    }

    // Gives the customer ID associated with the API key in the customers map
    public int getCustomerId(String apiKey) {
        Customer customer = customers.get(apiKey);
        if (customer == null) {
            throw new IllegalArgumentException("This API key does not belong to a customer.");
        }
        return customer.getCustomerID();
    }
}
