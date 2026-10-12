package com.demoapi.demo.repositories;

import com.demoapi.demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}
