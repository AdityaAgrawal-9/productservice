package com.ecommercestore.productservice.repositories;

import com.ecommercestore.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Update is also done using save()
    // If the product you try to save has an ID:
    // JPA will see if a product with that ID exists:
    // If no -> Insert
    // If yes -> Update
    @Override
    Product save(Product product);

    @Override
    void delete(Product product);

    List<Product> findAll();

    @Override
    Optional<Product> findById(Long aLong);
}
