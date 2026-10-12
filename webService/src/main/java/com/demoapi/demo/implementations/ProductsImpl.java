package com.demoapi.demo.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.stereotype.Service;

import com.demoapi.demo.dtos.AddProductDTO;
import com.demoapi.demo.dtos.ProductResponseDTO;
import com.demoapi.demo.dtos.ProductsDTO;
import com.demoapi.demo.entities.Product;
import com.demoapi.demo.repositories.ProductRepository;
import com.demoapi.demo.services.ProductServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service 
public class ProductsImpl implements ProductServices{
    private final ProductRepository productRepository;

    private ProductsImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductsDTO> getAllProducts() {
        List<ProductsDTO> result = new ArrayList<>();
        List<Product> items = this.productRepository.findAll();
        for (Product item : items) {
            result.add(new ProductsDTO(item.getCode(), item.getName(),
                    item.isStatus()));
        }
        return result;
    }

    @Override 
    public ProductResponseDTO createProduct(AddProductDTO product){
        ProductResponseDTO response = new ProductResponseDTO();

        try {
            if(product.getName() == null || product.getName().trim().isEmpty()){
                response.setMessage("Debe ingresar un nombre válido para el producto");
                response.setSuccess(false);
                return response;
            }

            Product newProduct = new Product();
            newProduct.setName(product.getName());
            newProduct.setStatus(product.isStatus());
            
            Product savedProduct = productRepository.save(newProduct);
            response.setMessage("El producto ha sido agregado exitosamente");
            response.setSuccess(true);
            response.setCode(savedProduct.getCode());
        } catch (Exception e) {
            log.error("{}", e);
            response.setMessage("Error mientras se agregaba el producto" + e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override 
    public ProductResponseDTO editProduct(Integer code, ProductsDTO product){
        ProductResponseDTO response = new ProductResponseDTO();
        try {
            Optional<Product> existingProduct = productRepository.findById(code);

            if(existingProduct.isEmpty()){
                response.setMessage("No existe un producto asociado a ese ID...");
                response.setSuccess(false);
                return response;
            }

            if(product.getName() == null || product.getName().trim().isEmpty()){
                response.setMessage("El producto debe tener un nombre válido");
                response.setSuccess(false);
                return response;
            }

            Product updateProduct = existingProduct.get();
            updateProduct.setName(product.getName());
            updateProduct.setStatus(product.isStatus());

            Product savedProduct = productRepository.save(updateProduct);

            response.setMessage("Producto editado exitosamente");
            response.setSuccess(true);
            response.setCode(updateProduct.getCode());

        } catch (Exception e) {
            log.error("{}", e);
            response.setMessage("Error al tratar de editar un producto: "+ e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }

    @Override 
    public ProductResponseDTO deleteProduct(Integer code){
        ProductResponseDTO response = new ProductResponseDTO();

        try {
            Optional<Product> existingProduct = productRepository.findById(code);
            if(existingProduct.isEmpty()){
                response.setMessage("No se encontró un producto para ese ID");
                response.setSuccess(false);
                return response;
            }

            productRepository.deleteById(code);

            response.setMessage("El producto ha sido eliminado exitosamente");
            response.setSuccess(true);
            response.setCode(code);

        } catch (Exception e) {
            log.error("{}", e);
            response.setMessage("Ocurrió un error al momento de eliminar el producto: "+ e.getMessage());
            response.setSuccess(false);
        }

        return response;
    }
}
