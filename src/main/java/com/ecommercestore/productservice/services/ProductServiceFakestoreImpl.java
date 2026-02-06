package com.ecommercestore.productservice.services;

import com.ecommercestore.productservice.dtos.FakestoreCreateProductRequestDto;
import com.ecommercestore.productservice.dtos.FakestoreCreateProductResponseDto;
//import com.store.productservice.dtos.FakestoreGetAllProductsResponseDto;
import com.ecommercestore.productservice.models.Category;
import com.ecommercestore.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
//@ConditionalOnProperty(name = "productServiceType", havingValue = "fakeStoreProductService")
public class ProductServiceFakestoreImpl implements ProductService {
    private RestTemplate restTemplate;

    public ProductServiceFakestoreImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        FakestoreCreateProductRequestDto request = new FakestoreCreateProductRequestDto();
//        request.setId(1);
        request.setTitle(product.getTitle());
        request.setPrice(product.getPrice());
        request.setDescription(product.getDescription());
        request.setCategory(product.getCategory().getName());
//        request.setId(product.getId());
        request.setImage(product.getImageURL());

        FakestoreCreateProductResponseDto response = restTemplate.postForObject("https://fakestoreapi.com/products",
                request,
                FakestoreCreateProductResponseDto.class);

        Product productReceived = new Product();
        productReceived.setId(response.getId());
        productReceived.setTitle(response.getTitle());
        productReceived.setPrice(response.getPrice());
        productReceived.setDescription(response.getDescription());
        Category category = new Category();
        category.setName(response.getCategory());
        productReceived.setCategory(category);
        productReceived.setImageURL(response.getImage());

        return productReceived;
    }

    @Override
    public List<Product> getAllProducts() {
        FakestoreCreateProductResponseDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakestoreCreateProductResponseDto[].class);
        List<Product> allProductsReceived = new ArrayList<>();
//        for(FakestoreCreateProductResponseDto product : response.getAllProductsReturnedFromFakestore()) {
        for(int i=0;i<response.length;i++) {
            Product productReceived = new Product();
            productReceived.setId(response[i].getId());
            productReceived.setTitle(response[i].getTitle());
            productReceived.setPrice(response[i].getPrice());
            productReceived.setDescription(response[i].getDescription());
            Category category = new Category();
            category.setName(response[i].getCategory());
            productReceived.setCategory(category);
//            productReceived.setCategory(response[i].getCategory());
            productReceived.setImageURL(response[i].getImage());
            allProductsReceived.add(productReceived);
        }
        return allProductsReceived;
    }

    @Override
    public Product getSingleProduct(Long id) {
        FakestoreCreateProductResponseDto response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+id,
                FakestoreCreateProductResponseDto.class);
        if(response == null) {
            return null;
        }
        Product productReceived = new Product();
        productReceived.setId(response.getId());
        productReceived.setTitle(response.getTitle());
        productReceived.setPrice(response.getPrice());
        productReceived.setDescription(response.getDescription());
        Category category = new Category();
        category.setName(response.getCategory());
        productReceived.setCategory(category);
//        productReceived.setCategory(response.getCategory());
        productReceived.setImageURL(response.getImage());
        return productReceived;
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            restTemplate.delete("https://fakestoreapi.com/products/"+id);
             // if no exception, assume success
        } catch (HttpClientErrorException ex) {
            // Will catch 400, 404, etc.
            System.err.println("Delete failed: " + ex.getStatusCode() + " - " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            // Network/other failures
            System.err.println("Unexpected error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Product updateProduct(Product product) {
        try {
            FakestoreCreateProductRequestDto request = new FakestoreCreateProductRequestDto();
//        request.setId(1);
            request.setTitle(product.getTitle());
            request.setPrice(product.getPrice());
            request.setDescription(product.getDescription());
            request.setCategory(product.getCategory().getName());
            request.setId(product.getId());
            request.setImage(product.getImageURL());
            FakestoreCreateProductResponseDto response =
                    restTemplate.patchForObject("https://fakestoreapi.com/products/"+product.getId(),
                    request,
                    FakestoreCreateProductResponseDto.class);

            Product productReceived = new Product();
            productReceived.setId(response.getId());
            productReceived.setTitle(response.getTitle());
            productReceived.setPrice(response.getPrice());
            productReceived.setDescription(response.getDescription());
            Category category = new Category();
            category.setName(response.getCategory());
            productReceived.setCategory(category);
//            productReceived.setCategory(response.getCategory());
            productReceived.setImageURL(response.getImage());

            return productReceived;
        } catch (HttpClientErrorException ex) {
            // Will catch 400, 404, etc.
            System.err.println("Delete failed: " + ex.getStatusCode() + " - " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Product replaceProduct(Product product) {
        FakestoreCreateProductRequestDto request = new FakestoreCreateProductRequestDto();
//        request.setId(1);
        request.setTitle(product.getTitle());
        request.setPrice(product.getPrice());
        request.setDescription(product.getDescription());
        request.setCategory(product.getCategory().getName());
        request.setId(product.getId());
        request.setImage(product.getImageURL());
//        FakestoreCreateProductResponseDto response =
//                restTemplate.exchange("https://fakestoreapi.com/products/"+product.getId(),
//                        HttpMethod.PUT,
//                        request,
//                        FakestoreCreateProductResponseDto.class);

        return null;
    }
}
