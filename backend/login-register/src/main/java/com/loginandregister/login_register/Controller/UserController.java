package com.loginandregister.login_register.Controller;

import com.loginandregister.login_register.Entity.User;
import com.loginandregister.login_register.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marks this class as a RESTful controller, allowing it to handle HTTP requests
@CrossOrigin(origins = "http://localhost:3000")  // Enables Cross-Origin Resource Sharing (CORS) for the React frontend on localhost:3000
public class UserController {

    @Autowired
    private UserRepository userRepository;  // Injects the UserRepository to interact with the database

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injects PasswordEncoder to hash user passwords

    @Autowired
    private AuthenticationManager authenticationManager;  // Injects AuthenticationManager to handle authentication

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Check if the username already exists in the database
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            // If the username exists, return a message
            return "Username already exists!";
        }
        // Encrypt the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set the default role for the user as "ROLE_USER"
        user.setRole("ROLE_USER");
        // Save the new user to the database
        userRepository.save(user);
        return "Success";  // Return a success message
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        // Create an authentication token from the login request (username and password)
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        try {
            // Attempt to authenticate the user using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(token);
            // If authentication is successful, store the authentication object in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Success");  // Return a success message
        } catch (AuthenticationException e) {
            // If authentication fails, return a 401 Unauthorized response with an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

}
