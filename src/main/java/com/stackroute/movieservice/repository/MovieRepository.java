package com.stackroute.movieservice.repository;

import com.stackroute.movieservice.domain.Movies;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movies, String> {

    public List<Movies> findByMovieTitle(String movieTitle);
    public Movies findByimdbId (String imdbId);

}