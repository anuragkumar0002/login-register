package com.loginandregister.login_register.Repository;

import com.loginandregister.login_register.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String username);
}
