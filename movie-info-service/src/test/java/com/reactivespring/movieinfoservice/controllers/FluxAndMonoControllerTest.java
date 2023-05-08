package com.reactivespring.movieinfoservice.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
@Disabled
class FluxAndMonoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void flux() {
        // arrange
        Iterable<Integer> expected = List.of(1, 2, 3);
        // act
        webTestClient
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(listEntityExchangeResult -> {
                    var actual = listEntityExchangeResult.getResponseBody();

                    // assert
                    assertIterableEquals(expected, actual);
                });
    }

    @Test
    public void stream() {
        var flux = webTestClient
                .get()
                .uri("/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNext(0L, 1L, 2L)
                .thenCancel()
                .verify();
    }
}