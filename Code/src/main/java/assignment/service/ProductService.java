package assignment.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import assignment.dto.WSSProduct;
import assignment.model.Product;
import assignment.repository.ProductRepository;

@Service
public class ProductService {
    
    private final RestTemplate restTemplate;
    private final ProductRepository repository;

    public ProductService(ProductRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // Adds a new product to sales list
    public Product addProduct(String productId, double retailPrice) {

        // makes sure price is greater than 0
        if (retailPrice <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be greater than 0.");
        }
        
        // Checks if product already exists in the database
        if (repository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exists: " + productId);
        }
        // Hard-coded WSS URL for product details
        String url = "https://pmaier.eu.pythonanywhere.com/wss/product/" + productId;
        
        // Gets JSON from WSS, of a product and maps it to WSSProduct object
        WSSProduct wssProduct = restTemplate.getForObject(url, WSSProduct.class);

        // If the product does not exist in WSS
        if (wssProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in WSS: " + productId);
        }

        // Creates new Product object from WSSProduct object
        Product product = new Product(wssProduct.id, wssProduct.category, wssProduct.description, retailPrice);

        // Saves the new Product object to the H2 database sales list and returns it
        return repository.save(product);
    }

    // Gets all products in sales list
    public List<Product> viewProducts() {
        // Gets all products from the database and returns them as a list
        return repository.findAll();
    }

    // Removes a product from the sales list
    public void removeProduct(String productId) {
        // Deletes a product based on given productId from the database
        repository.deleteById(productId);
    }

    // Updates the price of an existing product
    public Product updateProductPrice(String productId, double newPrice) {
        // Finds the product by productId
        Product product = repository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        // makes sure price is greater than 0
        if (newPrice <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be greater than 0.");
        }
        product.setProductPrice(newPrice);
        return repository.save(product);
    }
}