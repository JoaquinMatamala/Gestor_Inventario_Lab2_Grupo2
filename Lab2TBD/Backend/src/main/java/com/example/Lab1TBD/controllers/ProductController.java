package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import com.example.Lab1TBD.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getall")
    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ProductEntity>> getProductsByName(@RequestParam String name){
        return ResponseEntity.ok(productService.getProductsByName(name));
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<ProductEntity>> getProductsByStatus(@RequestParam String status){
        return ResponseEntity.ok(productService.getProductsByStatus(status));
    }

    @GetMapping("/search/categoryId/{id}")
    public ResponseEntity<List<ProductEntity>> getProductsByCategoryId(@PathVariable long id){
        return ResponseEntity.ok(productService.getProductsByCategoryId(id));
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductEntity product){
        productService.saveProduct(product);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateProduct(@RequestParam ProductEntity product){
        productService.updateProduct(product);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        ProductEntity existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
