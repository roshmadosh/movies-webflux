package com.reactivespring.movieinfoservice.moviedetail;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/moviedetail")
public class MovieDetailController {
    private final MovieDetailRepository movieDetailRepository;

    public MovieDetailController(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }

    @GetMapping
    public Flux<MovieDetail> getAllMovieDetails() {
        return movieDetailRepository.findAll();
    }

    @PostMapping
    public Mono<MovieDetail> addMovieDetail(@RequestBody MovieDetail movieDetail) {
        return movieDetailRepository.insert(movieDetail);
    }
}
