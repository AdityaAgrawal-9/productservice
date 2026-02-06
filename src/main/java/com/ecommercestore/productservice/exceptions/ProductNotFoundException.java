package com.ecommercestore.productservice.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Product Not Found");
    }

    public ProductNotFoundException(String message){
        super(message);
    }
}
