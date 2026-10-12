package com.demoapi.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoapi.demo.dtos.AddProductDTO;
import com.demoapi.demo.dtos.ProductResponseDTO;
import com.demoapi.demo.dtos.ProductsDTO;
import com.demoapi.demo.services.ProductServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestController 
@RequestMapping("/products")
public class ProductController {
    final private ProductServices productServices;

    private ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllItems() {
        try {
            List<ProductsDTO> items = productServices.getAllProducts();
            if (items.isEmpty()) {
                return ResponseEntity.status(204).build();
            } else {
                return ResponseEntity.ok(items);
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody AddProductDTO product){
        try {
            ProductResponseDTO response = productServices.createProduct(product);
            if(response.isSuccess()){
                return ResponseEntity.status(201).body(response);
            }else{
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(500).build();
        }
    }
}
