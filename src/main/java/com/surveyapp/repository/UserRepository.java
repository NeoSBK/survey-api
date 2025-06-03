package com.surveyapp.repository;

import com.surveyapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT MAX(u.dateOfBirth) FROM User u")
    LocalDate findYoungestDob();

    @Query("SELECT MIN(u.dateOfBirth) FROM User u")
    LocalDate findOldestDob();

    @Query("SELECT u FROM User u WHERE u.dateOfBirth IS NOT NULL")
    List<User> findAllWithDateOfBirth();

    boolean existsByEmail(String email);
}
