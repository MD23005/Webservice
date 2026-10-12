package com.demoapi.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class RegisterResponseDTO {
    private String message;
    private boolean success;
    private Integer userId;
}
