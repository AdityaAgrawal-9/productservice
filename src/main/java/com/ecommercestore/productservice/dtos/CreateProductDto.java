package com.ecommercestore.productservice.dtos;

import com.ecommercestore.productservice.models.Category;
import com.ecommercestore.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String categoryName;
    private String imageURL;

    public static CreateProductDto fromProduct(Product product) {
        CreateProductDto responseDto = new CreateProductDto();
        responseDto.setId(product.getId());
        responseDto.setTitle(product.getTitle());
        responseDto.setDescription(product.getDescription());
        responseDto.setPrice(product.getPrice());
        responseDto.setCategoryName(product.getCategory().getName());
        responseDto.setImageURL(product.getImageURL());
        return responseDto;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setDescription(this.description);
        product.setTitle(this.title);
        product.setPrice(this.price);
        Category category = new Category();
        category.setName(this.categoryName);
        product.setCategory(category);
        product.setImageURL(this.imageURL);
        return product;
    }

}
