package com.example.fooddelivery.Controller;

import com.example.fooddelivery.Entity.Customer;
import com.example.fooddelivery.Entity.Role;
import com.example.fooddelivery.Pojo.AuthResponsePojo;
import com.example.fooddelivery.Pojo.CustomerPojo;
import com.example.fooddelivery.Repository.CustomerRepo;
import com.example.fooddelivery.Repository.RoleRepository;
import com.example.fooddelivery.security.JwtGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepo customerRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing");
        }

        try {
            if (jwtGenerator.validateToken(refreshToken)) {
                String username = jwtGenerator.getUsernameFromJwt(refreshToken);
                String newAccessToken = jwtGenerator.generateToken(username);
                return ResponseEntity.ok(new AuthResponsePojo(newAccessToken, refreshToken, null, null));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token");
        }
    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponsePojo> login(@RequestBody CustomerPojo loginPojo) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginPojo.getUsername(), loginPojo.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtGenerator.generateToken(loginPojo.getUsername());
        String refreshToken = jwtGenerator.generateRefreshToken(loginPojo.getUsername());

        Customer user = customerRepo.findByUsername(loginPojo.getUsername()).orElseThrow();
        Role role = user.getRole();

        AuthResponsePojo response = new AuthResponsePojo(accessToken, refreshToken, Math.toIntExact(user.getId()), role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register/user")
    public ResponseEntity<String> registerUser(@RequestBody CustomerPojo registerPojo) {
        return registerUserWithRole(registerPojo, "USER");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody CustomerPojo registerPojo) {
        return registerUserWithRole(registerPojo, "ADMIN");
    }

    private ResponseEntity<String> registerUserWithRole(CustomerPojo registerPojo, String roleName) {
        if (customerRepo.existsByUsername(registerPojo.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        Customer user = new Customer();
        user.setUsername(registerPojo.getUsername());
        user.setPassword(passwordEncoder.encode(registerPojo.getPassword()));

        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isPresent()) {
            user.setRole(role.get());
        } else {
            return new ResponseEntity<>("Role not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        customerRepo.save(user);
        return ResponseEntity.ok("User registered successfully as " + roleName);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody CustomerPojo request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtGenerator.generateToken(request.getUsername());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint(@RequestHeader("Authorization") String token) {
        if (!jwtGenerator.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String username = jwtGenerator.getUsernameFromJwt(token);

        return ResponseEntity.ok("Hello, " + username + "! This is a secured endpoint.");
    }
}
