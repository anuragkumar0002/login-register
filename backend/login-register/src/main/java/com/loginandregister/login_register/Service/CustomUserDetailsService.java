package com.loginandregister.login_register.Service;

import com.loginandregister.login_register.Entity.User;
import com.loginandregister.login_register.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service  // Marks this class as a Spring service, so it can be injected where needed
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Injects UserRepository to interact with the database

    /**
     * This method loads a user by their username.
     * It is required by the UserDetailsService interface, which Spring Security uses for authentication.
     *
     * @param username The username of the user trying to log in.
     * @return UserDetails object that contains user information, used by Spring Security for authentication.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user from the database by their username
        User user = userRepository.findByUsername(username);

        // If the user is not found, throw an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return a UserDetails object that contains the username, password, and roles
        // Spring Security will use this information during the authentication process
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),  // Username
                user.getPassword(),  // Password (which is already hashed)
                // Grant the user their assigned role
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))  // Roles or authorities
        );
    }
}
