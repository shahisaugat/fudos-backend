package com.example.fooddelivery.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final CustomUserDetailService userDetailService;

    private final JwtAuthEntryPoint authEntryPoint;

    public SecurityConfig(CustomUserDetailService userDetailService, JwtAuthEntryPoint authEntryPoint) {
        this.userDetailService = userDetailService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/auth/register/user").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/register/admin").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(withDefaults())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
}
