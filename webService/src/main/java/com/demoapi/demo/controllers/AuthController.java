package com.demoapi.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demoapi.demo.dtos.LoginDTO;
import com.demoapi.demo.dtos.LogoutDTO;
import com.demoapi.demo.dtos.RegisterDTO;
import com.demoapi.demo.dtos.RegisterResponseDTO;
import com.demoapi.demo.dtos.TokenDTO;
import com.demoapi.demo.services.AuthServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestController 
@RequestMapping("/auth")
public class AuthController {
    final private AuthServices authServices;

    private AuthController(AuthServices authServices) {
        this.authServices = authServices;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO authRequest) {
        try {
            System.out.println("DTO enviado : " +
                    authRequest.toString());
            TokenDTO token = authServices.login(authRequest.getUser(),
                    authRequest.getPass());
            if (token == null) {
                return ResponseEntity.status(401).build();
            } else {
                return ResponseEntity.ok(token);
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutDTO> logout(@RequestHeader(value = "Authorization", required = false) String authHeader){
        try {
            String token = null;
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
            }else{
                token = authHeader;
            }
            LogoutDTO response = authServices.logout(token);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            }else{
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            log.error("{}",e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterDTO register){
        try {
            RegisterResponseDTO response = authServices.register(register);
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
