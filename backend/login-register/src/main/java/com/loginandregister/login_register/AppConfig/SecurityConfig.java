package com.loginandregister.login_register.AppConfig;

import com.loginandregister.login_register.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Enable Spring Security for the application
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    // Constructor injection for CustomUserDetailsService
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder is used to hash the user's password before saving it to the database
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection since the frontend is separate from the backend
                .csrf(csrf -> csrf.disable())
                // Configure which endpoints can be accessed without authentication
                .authorizeHttpRequests(auth -> auth
                        // Allow "/register" and "/login" endpoints to be accessed by everyone (no authentication required)
                        .requestMatchers("/register", "/login").permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                // Allow logout for any authenticated user
                .logout(logout -> logout.permitAll());

        return http.build();  // Build the security filter chain
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Configure the AuthenticationManager bean, which is responsible for handling authentication requests
        return authenticationConfiguration.getAuthenticationManager();
    }
}
