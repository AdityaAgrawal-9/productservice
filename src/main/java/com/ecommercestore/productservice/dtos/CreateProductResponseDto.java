package com.ecommercestore.productservice.dtos;

import com.ecommercestore.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String categoryName;
    private String imageURL;

    public static CreateProductResponseDto fromProduct(Product product) {
        CreateProductResponseDto createProductResponseDto = new CreateProductResponseDto();
        createProductResponseDto.setId(product.getId());
        createProductResponseDto.setTitle(product.getTitle());
        createProductResponseDto.setDescription(product.getDescription());
        createProductResponseDto.setPrice(product.getPrice());
        createProductResponseDto.setCategoryName(product.getCategory().getName());
        createProductResponseDto.setImageURL(product.getImageURL());

        return createProductResponseDto;
    }
}
