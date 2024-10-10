package com.datababa.namma_shops.service.product;

import com.datababa.namma_shops.model.Product;
import com.datababa.namma_shops.request.AddProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    void updateProduct(Product product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);





}
