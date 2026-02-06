package com.ecommercestore.productservice.controllers;

import com.ecommercestore.productservice.dtos.*;
import com.ecommercestore.productservice.exceptions.ProductNotFoundException;
import com.ecommercestore.productservice.models.Product;
import com.ecommercestore.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Value("${productServiceType}")
    private String productServiceType;

    //@Qualifier()
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public CreateProductResponseDto createproduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        Product product = productService.createProduct(createProductRequestDto.toProduct());
        return CreateProductResponseDto.fromProduct(product);
    }


    @GetMapping("/")
    public GetAllProductsResposeDto getAllProducts(){
        List<Product> allProducts = productService.getAllProducts();
        GetAllProductsResposeDto getAllProductsResposeDto = new GetAllProductsResposeDto();
        if(getAllProductsResposeDto.getAllProducts() == null)
        {
            getAllProductsResposeDto.setAllProducts(new ArrayList<>());
        }
        for(Product product : allProducts){
            getAllProductsResposeDto.getAllProducts().add(CreateProductResponseDto.fromProduct(product));
        }
        return getAllProductsResposeDto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateProductResponseDto> getSingleProduct(@PathVariable("id") Long id)
            throws ProductNotFoundException {
        try {
            Product product = productService.getSingleProduct(id);
            if (product != null) {
                return ResponseEntity.ok(CreateProductResponseDto.fromProduct(product));
            } else {
                throw new ProductNotFoundException("Product with ID " + id + " not found");
            }
        }catch(ProductNotFoundException e){
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.NOT_FOUND.value());
            body.put("error", "Product Not Found");
            body.put("message", e.getMessage());
            body.put("path", "/products/" + id);
            return new ResponseEntity(body, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        String response =
                productService.deleteProduct(id) ? "Product deleted successfully" : "Something went wrong! ";
        return response;
    }

    @PutMapping("/{id}")
    public GetProductDto replaceProduct(@RequestBody CreateProductDto createProductDto,
                                        @PathVariable("id") Long id) {
        Product product = createProductDto.toProduct();
        product.setId(id);
        Product replacedProduct = productService.replaceProduct(product);
        return GetProductDto.from(replacedProduct);
    }

    @PatchMapping("/{id}")
    public CreateProductResponseDto updateProduct(@RequestBody CreateProductRequestDto createProductRequestDto,
                                                   @PathVariable("id") Long id){
        Product product = createProductRequestDto.toProduct();
        product.setId(id);
        Product product1 = productService.updateProduct(product);
        if(product1 != null) {
            CreateProductResponseDto response = CreateProductResponseDto.fromProduct(product1);
            return response;
        }else{
            return null;
        }
    }

//    How to create a random HTTP request Type  -
//    @RequestMapping(name = "ADITYA", value = "/products/")
//    public String adityasFunction(){
//        return "Aditya is the smartest person.";
//    }

}
