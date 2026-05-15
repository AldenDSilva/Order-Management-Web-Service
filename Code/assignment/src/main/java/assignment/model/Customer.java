package assignment.model;

public class Customer {
    private final int customerID;
    private final String name;
    private final String email;
    private final String postalAddress;

    public Customer(int customerID, String name, String email, String postalAddress) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.postalAddress = postalAddress;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return name;
    }

    public String getCustomerEmail() {
        return email;
    }  

    public String getPostalAddress() {
        return postalAddress;
    }
}