package com.demoapi.demo.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demoapi.demo.configs.CustomerDetailServices;
import com.demoapi.demo.dtos.LogoutDTO;
import com.demoapi.demo.dtos.RegisterDTO;
import com.demoapi.demo.dtos.RegisterResponseDTO;
import com.demoapi.demo.dtos.TokenDTO;
import com.demoapi.demo.entities.User;
import com.demoapi.demo.repositories.UserRepository;
import com.demoapi.demo.services.AuthServices;
import com.demoapi.demo.utilities.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServicesImpl implements AuthServices{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerDetailServices customerDetailServices;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDTO login(String user, String pass) {
        TokenDTO token = new TokenDTO();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, pass));
            if (authentication.isAuthenticated()) {
                UserDetails usuarioDetail = (UserDetails) authentication.getPrincipal();
                if (customerDetailServices.getUserDetail().getActive()) {
                    token = jwtUtil.generateToken(user, usuarioDetail);
                    return token;
                }
            }
        } catch (BadCredentialsException bad) {
            token.setMsj("Credenciales incorrectas!");
            return null;
        } catch (Exception e) {
            log.error("{}", e);
            token.setMsj("Error innesperado");
            return null;
        }
        return null;
    }

    @Override 
    public LogoutDTO logout(String token){
        LogoutDTO response = new LogoutDTO();
        try {
            if(token != null && !token.isEmpty()){
                response.setMessage("Sesión cerrada correctamente");
                response.setSuccess(true);
            }else{
                response.setMessage("Token inválido o no proporcionado");
                response.setSuccess(false);
            }
        } catch (Exception e) {
            response.setMessage("Error durante el cierre de sesión" + e.getMessage());
            response.setSuccess(false);
        }
        return  response;
    }

    @Override 
    public RegisterResponseDTO register(RegisterDTO register){
        RegisterResponseDTO response = new RegisterResponseDTO();

        try {
            User userExist = userRepository.findByEmail(register.getEmail());
            if(userExist != null){
                response.setMessage("Correo inválido, pruebe con otro...");
                response.setSuccess(false);
                return response;
            }

            User newUser = new User();
            newUser.setName(register.getName());
            newUser.setLastName(register.getLastName());
            newUser.setEmail(register.getEmail());

            //Hash de la contraseña
            newUser.setPassword(passwordEncoder.encode(register.getPassword()));
            newUser.setActive(true);
            
            User saveUsr = userRepository.save(newUser);

            response.setMessage("¡Usuario registrado existosamente!");
            response.setSuccess(true);
            response.setUserId(saveUsr.getId());


        } catch (Exception e) {
            log.error("{}", e);
            response.setMessage("Error durante el registro: " + e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
}
