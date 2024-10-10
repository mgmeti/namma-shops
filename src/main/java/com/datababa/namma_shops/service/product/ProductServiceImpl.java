package com.datababa.namma_shops.service.product;

import com.datababa.namma_shops.exceptions.ProductNotFoundException;
import com.datababa.namma_shops.model.Category;
import com.datababa.namma_shops.model.Product;
import com.datababa.namma_shops.repository.CategoryRepository;
import com.datababa.namma_shops.repository.ProductRepository;
import com.datababa.namma_shops.request.AddProductRequest;
import com.datababa.namma_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // if the category is found in the DB
        // if Yes, set it as hte new product category
        // if No, then save it as a new category
        // Then set it as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> new ProductNotFoundException("Product not found") );

    }

    /**
     * Updates an existing product with the provided data in the {@link ProductUpdateRequest}.
     *
     * @param request the {@link ProductUpdateRequest} containing the new product data
     *                to update the existing product.
     * @param productId the ID of the product to be updated.
     * @return        the updated {@link Product} after saving it to the database.
     * @throws ProductNotFoundException if a product with the given ID does not exist in the repository.
     *
     *  @see Product
     *  @see ProductUpdateRequest
     *  @see ProductRepository
     *  @see ProductNotFoundException
     */
    @Override
    public Product updateProduct(ProductUpdateRequest  request, Long productId) {
         return productRepository.findById(productId)
                 .map(existingProduct -> updateExistingProduct(existingProduct, request))
                 .map(productRepository :: save)
                 .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {

        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
