package com.twigcodes.authserver.repositories;

import com.twigcodes.authserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOptionalByUsernameIgnoreCase(String username);
    long countByUsernameIgnoreCase(String username);
}
