package com.example.fooddelivery.security;

import com.example.fooddelivery.Entity.Customer;
import com.example.fooddelivery.Entity.Role;
import com.example.fooddelivery.Repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        return User.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(customer.getRole().getName())))
                .build();
    }
}
