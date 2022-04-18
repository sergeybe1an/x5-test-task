package com.sbelan.x5testproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbelan.x5testproject.model.business.Product;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private Long id;
    private String name;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);

        return product;
    }

    public static ProductDto fromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());

        return productDto;
    }
}
