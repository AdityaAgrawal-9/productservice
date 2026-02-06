package com.ecommercestore.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllProductsResposeDto {
    private List<CreateProductResponseDto> allProducts;
}
