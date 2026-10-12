package com.demoapi.demo.services;

import java.util.List;

import com.demoapi.demo.dtos.AddProductDTO;
import com.demoapi.demo.dtos.ProductResponseDTO;
import com.demoapi.demo.dtos.ProductsDTO;

public interface ProductServices {
    public List<ProductsDTO> getAllProducts();
    public ProductResponseDTO createProduct(AddProductDTO product);
    public ProductResponseDTO editProduct(Integer code ,ProductsDTO product);
    public ProductResponseDTO deleteProduct(Integer code);
}
