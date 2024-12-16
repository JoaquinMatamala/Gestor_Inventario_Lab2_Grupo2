package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import java.util.List;

public interface ProductRepository {
    // DEFAULT
    List<ProductEntity> findAllProducts();
    ProductEntity findProductById(Long product_id);
    void saveProduct(ProductEntity product);
    void updateProduct(ProductEntity product);
    void deleteProductById(Long product_id);

    // SEARCH
    List<ProductEntity> findProductsByName(String product_name);
    List<ProductEntity> findProductsByDescription(String description);
    List<ProductEntity> findProductsByPrice(float price);
    List<ProductEntity> findProductsByStock(int stock);
    List<ProductEntity> findProductsByStatus(String product_status);
    List<ProductEntity> findProductsByCategoryId(Long category_id);

    // OTHER
    void updateProductStock(Long productId);
    int getProductStockById(Long productId);
}