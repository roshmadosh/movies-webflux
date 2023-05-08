package com.reactivespring.movieinfoservice.moviedetail;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieDetailRepository extends ReactiveMongoRepository<MovieDetail, String> {
}
