package com.datababa.namma_shops.dto;

import com.datababa.namma_shops.model.Category;
import com.datababa.namma_shops.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;

    private  String name;

    private  String brand;

    private BigDecimal price;

    private  int inventory;

    private String description;

    private Category category;

    private List<ImageDto> images;
}
