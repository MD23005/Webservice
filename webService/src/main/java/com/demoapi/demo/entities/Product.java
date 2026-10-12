package com.demoapi.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Code")
    private Integer code;
    @Column(name = "Name")
    private String name;
    @Column(name = "Status")
    private boolean status;
}