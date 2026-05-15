package assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import assignment.model.Product;

// To create a database repository for for the product table using Product as the entity and String as the primary key type
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}