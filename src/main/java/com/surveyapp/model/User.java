package com.surveyapp.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "survey_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private String contactNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Ratings ratings;

    public User(String fullName, String email, LocalDate dob, String contactNumber) {
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dob;
        this.contactNumber = contactNumber;
    }
}
