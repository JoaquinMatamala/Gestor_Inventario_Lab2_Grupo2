package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import com.example.Lab1TBD.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts(){
        return productRepository.findAllProducts();
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findProductById(id);
    }

    public List<ProductEntity> getProductsByName(String name) {
        return productRepository.findProductByName(name);
    }

    public List<ProductEntity> getProductsByStatus(String status) {
        return productRepository.findProductsByStatus(status);
    }

    public List<ProductEntity> getProductsByCategoryId(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }

    public List<ProductEntity> getProductsByStock(int stock){
        return productRepository.findProductByStock(stock);
    }

    public List<ProductEntity> getProductsByDescription(String description){
        return productRepository.findProductByDescription(description);
    }

    public List<ProductEntity> getProductsByPrice(float price){
        return productRepository.findProductByPrice(price);
    }

    public void saveProduct(ProductEntity product) {
        productRepository.saveProduct(product);
    }

    public void updateProduct(ProductEntity product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProductById(id);
    }
}
