package com.surveyapp.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "favorite_foods", joinColumns = @JoinColumn(name = "response_id"))
    @Column(name = "food")
    private Set<String> favoriteFoods = new HashSet<>();

    @Min(1) @Max(5)
    private int moviesRating;

    @Min(1) @Max(5)
    private int radioRating;

    @Min(1) @Max(5)
    private int eatOutRating;

    @Min(1) @Max(5)
    private int tvRating;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
