package com.fangxiaoer.authserver.repositories;

import com.fangxiaoer.authserver.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOptionalByUsernameIgnoreCase() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findOptionalByUsernameIgnoreCase("TESTUSER");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testCountByUsernameIgnoreCase() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        userRepository.save(user);

        // When
        long count = userRepository.countByUsernameIgnoreCase("TESTUSER");

        // Then
        assertThat(count).isEqualTo(1);
    }
}
