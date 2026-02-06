package com.ecommercestore.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakestoreCreateProductResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
