package com.stackroute.movieservice.services;

import com.stackroute.movieservice.domain.Movies;
import com.stackroute.movieservice.exceptions.EmptyDBException;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("Impl2")
public class MovieServicesImpl2 implements MovieServices{
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    public MovieServicesImpl2(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public Movies saveMovie (Movies movies) throws MovieAlreadyExistsException {
        if(movieRepository.existsById(movies.getImdbId())){
            throw new MovieAlreadyExistsException("User Already Exists 2");
        }
        Movies savedMovie = movieRepository.save(movies);
        if(savedMovie == null){
            throw new MovieAlreadyExistsException("User Already Exists");
        }
        return savedMovie;
    }

    @Override
    public List<Movies> getAllMovies () throws EmptyDBException {
        List<Movies> emptylist = (List)movieRepository.findAll();
        if (emptylist.size() == 0){
            throw new EmptyDBException("Database is Empty");
        }
        return (List) movieRepository.findAll();
    }

    @Override
    public Movies deleteMovie(Movies movies) throws MovieNotFoundException {
        if(movieRepository.existsById(movies.getImdbId()) == false){
            throw new MovieNotFoundException("No such Movie Exists");
        }
        Movies deletedMovie = movies;
        movieRepository.delete(movies);
        return deletedMovie;
    }

    @Override
    public Movies updateMovie(Movies movies,String imdbId) throws MovieNotFoundException{
        if(movieRepository.findById(imdbId).isPresent() == false){
            throw new MovieNotFoundException("No such Movie Exists");
        }
        Movies updateMovie = movieRepository.findByimdbId(imdbId);
        Movies storedMovie = (Movies)updateMovie;
        storedMovie.setComments(movies.getComments());
        return movieRepository.save(storedMovie);
    }

    @Override
    public Movies getByIMDBId (String ImdbId) throws MovieNotFoundException{
        if(movieRepository.findById(ImdbId).isPresent() == false){
            throw new MovieNotFoundException("No such Movie Exists");
        }
        Movies returnedMovie = movieRepository.findByimdbId(ImdbId);
        return returnedMovie;
    }

    @Override
    public List<Movies> getMovieByTitle(String movieTitle) throws MovieNotFoundException{
        if(movieRepository.findByMovieTitle(movieTitle)== null) {
            throw new MovieNotFoundException("No such movie exists");
        }
            List<Movies> foundMovie = movieRepository.findByMovieTitle(movieTitle);
        return  foundMovie;
    }
}
