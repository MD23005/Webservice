package com.demoapi.demo.configs;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demoapi.demo.entities.User;
import com.demoapi.demo.repositories.UserRepository;

@Service 
public class CustomerDetailServices implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = userRepo.findByEmail(username);
        if (userDetail != null) {
            try {
                return new org.springframework.security.core.userdetails.User(userDetail.getEmail(),
                        userDetail.getPassword(),
                        new ArrayList<>());
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public User getUserDetail() {
        return userDetail;
    }
}
