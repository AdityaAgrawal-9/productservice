package com.ecommercestore.productservice.dtos;

import com.ecommercestore.productservice.models.Category;
import com.ecommercestore.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private String title;
    private String description;
    private Double price;
    private String categoryName;
    private String imageURL;

    public Product toProduct() {
        Product product = new Product();
        product.setDescription(this.description);
        product.setTitle(this.title);
        product.setPrice(this.price);
        Category category = new Category();
        category.setName(this.categoryName);
        product.setCategory(category);
//        product.setCategory(this.category);
        product.setImageURL(this.imageURL);
        return product;
    }
}
