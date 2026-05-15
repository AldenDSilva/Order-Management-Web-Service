package assignment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    private String productID;
    private String productCategory;
    private String productDescription;
    private double productPrice;
    private boolean isAvailable;

    public Product() {
    }

    public Product(String id, String category, String desc, double price) {
        this.productID = id;
        this.productCategory = category;
        this.productDescription = desc;
        this.productPrice = price;
        this.isAvailable = true;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setProductPrice(double newPrice) {
        this.productPrice = newPrice;
    }
}