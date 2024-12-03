package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import java.util.List;

public interface ProductRepository {
    ProductEntity findProductById(Long product_id);
    List<ProductEntity> findAllProducts();
    List<ProductEntity> findProductByName(String product_name);
    List<ProductEntity> findProductsByStatus(String product_status);
    List<ProductEntity> findProductsByCategoryId(Long category_id);
    List<ProductEntity> findProductByStock(int stock);
    List<ProductEntity> findProductByDescription(String description);
    List<ProductEntity> findProductByPrice(float price);

    void saveProduct(ProductEntity product);
    void updateProduct(ProductEntity product);
    void deleteProductById(Long product_id);

    void updateProductStock(Long productId);

    int getProductStockById(Long productId);
}