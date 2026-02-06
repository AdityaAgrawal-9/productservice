package com.ecommercestore.productservice.services;

import com.ecommercestore.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getSingleProduct(Long id);
    boolean deleteProduct(Long id);
    Product updateProduct(Product product);
    Product replaceProduct(Product product);

}
