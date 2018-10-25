package com.stackroute.movieservice.services;

import com.stackroute.movieservice.domain.Movies;
import com.stackroute.movieservice.exceptions.EmptyDBException;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieServices {

    public Movies saveMovie(Movies movies) throws MovieAlreadyExistsException;

    public List<Movies> getAllMovies() throws EmptyDBException;

    public Movies updateMovie(Movies movie,String imdbId) throws MovieNotFoundException;

    public Movies deleteMovie(Movies movies) throws MovieNotFoundException;

    public Movies getByIMDBId(String ImdbId) throws MovieNotFoundException;

    public List<Movies> getMovieByTitle(String movieTitle) throws MovieNotFoundException;
}