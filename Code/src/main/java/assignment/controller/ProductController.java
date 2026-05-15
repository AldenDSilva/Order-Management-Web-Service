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

import assignment.model.Product;
import assignment.service.AuthenticationService;
import assignment.service.ProductService;

@RestController
public class ProductController {

    private final ProductService productService;
    private final AuthenticationService authenticationService;

    public ProductController(ProductService productService, AuthenticationService authenticationService) {
        this.productService = productService;
        this.authenticationService = authenticationService;
    }

    // Adds a new product to sales list
    @PostMapping("/products")
    public Product addProduct(@RequestHeader("Authorization") String apiKey, @RequestParam String productId, @RequestParam double retailPrice) {
        if (authenticationService.isOperator(apiKey)) {
            // Asks ProductService to add a new product to the sales list and gives the retail price from query parameter
            return productService.addProduct(productId, retailPrice);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only operators can add products.");
        }
    }

    // Gets all products in sales list
    @GetMapping("/products")
    public List<Product> viewProducts() {
        // Asks ProductService to get all products in sales list
        // Doesn't need authentication as anyone should be able to view products
        return productService.viewProducts();
    }

    // Updates the price of an existing product in sales list
    @PutMapping("/products/{productId}/price")
    public Product updateProductPrice (@RequestHeader("Authorization") String apiKey, @PathVariable String productId, @RequestParam double newPrice) {
        if (authenticationService.isOperator(apiKey)) {
            // Asks ProductService to update the price of a product in the sales list
            Product updatedProductPrice = productService.updateProductPrice(productId, newPrice);
            return updatedProductPrice;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only operators can update product prices.");
        }
    }
    
    // Removes a product from the sales list
    @DeleteMapping("/products/{productId}")
    public void removeProduct(@RequestHeader("Authorization") String apiKey, @PathVariable String productId){
        if (authenticationService.isOperator(apiKey)) {
            // Asks ProductService to remove a product from sales list
            productService.removeProduct(productId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCESS DENIED: Only operators can remove products.");
        }
    }
}