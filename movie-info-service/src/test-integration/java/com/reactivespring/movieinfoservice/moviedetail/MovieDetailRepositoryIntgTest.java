package com.reactivespring.movieinfoservice.moviedetail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class MovieDetailRepositoryIntgTest {
    @Autowired
    MovieDetailRepository sut;

    @BeforeEach
    void init() {
        var movieDetails = List.of(
            new MovieDetail(null, "Karate Kid", LocalDate.of(1999, 2, 14)),
            new MovieDetail(null, "Titanic", LocalDate.of(2000, 10, 9)),
            new MovieDetail(null, "Peter Pan", LocalDate.of(1987, 5, 25))
        );
        sut.saveAll(movieDetails)
                // remember our sut is reactive, without blocking the tests will start right away
                .blockLast();
    }
    @AfterEach
    void tearDown() {
        sut.deleteAll().block();
    }

    @Test
    void findAll() {
        var movieDetailsFlux = sut.findAll().log();

        StepVerifier.create(movieDetailsFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

}