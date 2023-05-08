package com.reactivespring.movieinfoservice.moviedetail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MovieDetailController.class)
@AutoConfigureWebTestClient
@Slf4j
class MovieDetailControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieDetailRepository movieDetailRepository;

    @Test
    void getMovieDetails() {
        log.info("Starting getMovieDetails test...");
        Iterable<MovieDetail> movieDetails = List.of(
                new MovieDetail(null, "title1", LocalDate.of(2005, 3, 11)),
                new MovieDetail(null, "title2", LocalDate.of(2000, 2, 22)));

        Flux<MovieDetail> moviesFlux = Flux.fromIterable(movieDetails);

        when(movieDetailRepository.findAll()).thenReturn(moviesFlux);

        log.info("Initiating webclient request...");
        webTestClient
                .get()
                .uri("/moviedetail")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(MovieDetail.class)
                .consumeWith(listEntityExchangeResult -> {
                    var movies = listEntityExchangeResult.getResponseBody();
                    assertIterableEquals(movieDetails, movies);
                });
        log.info("getMovieDetails test complete.");
    }
}