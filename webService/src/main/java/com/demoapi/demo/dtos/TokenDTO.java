package com.demoapi.demo.dtos;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    private String expireIn;
    private String msj;
}