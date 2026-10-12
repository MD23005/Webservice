package com.demoapi.demo.services;

import com.demoapi.demo.dtos.LogoutDTO;
import com.demoapi.demo.dtos.RegisterDTO;
import com.demoapi.demo.dtos.RegisterResponseDTO;
import com.demoapi.demo.dtos.TokenDTO;

public interface AuthServices {
    public TokenDTO login(String user, String pass);
    public LogoutDTO logout(String token);
    public RegisterResponseDTO register(RegisterDTO registerdto);
}
