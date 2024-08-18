package com.twigcodes.authserver.services;

import com.twigcodes.authserver.entities.User;
import com.twigcodes.authserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        // Assuming User is the JPA entity implementing UserDetails
        User user = (User) userDetails;
        user.setPassword(newPassword); // The new password is already encoded

        // Save the updated user entity to the database
        userRepository.save(user);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
