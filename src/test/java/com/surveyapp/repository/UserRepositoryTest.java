package com.surveyapp.repository;

import com.surveyapp.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindBothYoungestAndOldestDob() {

        LocalDate oldest = LocalDate.of(1990, 1, 1);
        LocalDate middle = LocalDate.of(2000, 6, 15);
        LocalDate youngest = LocalDate.of(2010, 12, 31);

        userRepository.deleteAll();

        userRepository.save(new User("User One", "user1@test.com", oldest, "0987654321"));
        userRepository.save(new User("User Two", "user2@test.com", middle, "1234567890"));
        userRepository.save(new User("User Three", "user3@test.com", youngest, "1122334455"));

        LocalDate youngestResult = userRepository.findYoungestDob();
        LocalDate oldestResult = userRepository.findOldestDob();

        assertThat(youngestResult).isEqualTo(youngest);
        assertThat(oldestResult).isEqualTo(oldest);
    }

    @Test
    void findAllWithDateOfBirth_shouldReturnOnlyUsersWithNonNullDob() {

        User withDob1 = new User("John Doe", "john@test.com", LocalDate.of(1990, 5, 15), "1234567890");
        User withDob2 = new User("Jane Smith", "jane@test.com", LocalDate.of(1985, 10, 20), "9876543210");

        userRepository.deleteAll();
        userRepository.saveAll(List.of(withDob1, withDob2));

        List<User> result = userRepository.findAllWithDateOfBirth();

        assertThat(result)
                .hasSize(2)
                .extracting(User::getEmail)
                .containsExactlyInAnyOrder("john@test.com", "jane@test.com");
    }
}
