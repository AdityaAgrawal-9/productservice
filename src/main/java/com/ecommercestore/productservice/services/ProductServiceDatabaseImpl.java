package com.ecommercestore.productservice.services;

import com.ecommercestore.productservice.models.Category;
import com.ecommercestore.productservice.models.Product;
import com.ecommercestore.productservice.repositories.CategoryRepository;
import com.ecommercestore.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@ConditionalOnProperty(name = "productServiceType", havingValue = "databaseProductService")
@Service("dbProductService")
public class ProductServiceDatabaseImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceDatabaseImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) {
        Category categoryForProduct = getCategoryForProduct(product.getCategory().getName());
        product.setCategory(categoryForProduct);
        Product savedProduct = productRepository.save(product);
        System.out.println("New Product has been Saved /n" + savedProduct.toString());
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product getSingleProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isEmpty()) {
            return null;
        } else {
//            for (Field field : Product.class.getDeclaredFields()) {
//                field.setAccessible(true);
//                try {
//                    Object newValue = field.get(product);
//                    if (newValue != null && !"id".equalsIgnoreCase(field.getName()) && !"Category".equals(field.getName()) ) {
//                        field.set(existingProduct.get(), newValue);
//                    }
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException("Failed to update field: " + field.getName(), e);
//                }
            if (product.getTitle() != null)
                existingProduct.get().setTitle(product.getTitle());
            if (product.getDescription() != null)
                existingProduct.get().setDescription(product.getDescription());
            if (product.getPrice() != null)
                existingProduct.get().setPrice(product.getPrice());
            if (product.getImageURL() != null) {
                existingProduct.get().setImageURL(product.getImageURL());
            }

            // Handle category carefully
            if (product.getCategory().getName() != null) {
                Optional<Category> category = categoryRepository.findByName(product.getCategory().getName());
                if(!category.isPresent()) {
                    categoryRepository.save(product.getCategory());
                    existingProduct.get().setCategory(product.getCategory());
                }else {
                    existingProduct.get().setCategory(category.get());
                }
            }
            productRepository.save(existingProduct.get());
            return existingProduct.get();
        }
    }


    @Override
    public Product replaceProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if(existingProduct.isEmpty()) {
            return null;
        }else{
            if (product.getTitle() != null)
                existingProduct.get().setTitle(product.getTitle());
            if (product.getDescription() != null)
                existingProduct.get().setDescription(product.getDescription());
            if (product.getPrice() != null)
                existingProduct.get().setPrice(product.getPrice());
            if (product.getImageURL() != null) {
                existingProduct.get().setImageURL(product.getImageURL());
            }

            // Handle category carefully
            if (product.getCategory().getName() != null) {
                Optional<Category> category = categoryRepository.findByName(product.getCategory().getName());
                if(!category.isPresent()) {
                    categoryRepository.save(product.getCategory());
                    existingProduct.get().setCategory(product.getCategory());
                }else {
                    existingProduct.get().setCategory(category.get());
                }
            }
            productRepository.save(existingProduct.get());
            return existingProduct.get();
        }
    }

    public Category getCategoryForProduct(String categoryName) {
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (category.isPresent()) {
            return category.get();
        }else{
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            categoryRepository.save(newCategory);
            return newCategory;
        }
    }
}
