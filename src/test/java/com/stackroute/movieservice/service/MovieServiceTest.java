package com.stackroute.movieservice.service;

import com.stackroute.movieservice.domain.Movies;
import com.stackroute.movieservice.exceptions.EmptyDBException;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.repository.MovieRepository;
import com.stackroute.movieservice.services.MovieServicesImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    Movies movies;

    //Create a mock for MoviesRepository
    @Mock//test double
            MovieRepository movieRepository;

    //Inject the mocks as dependencies into MoviesServiceImpl
    @InjectMocks
    MovieServicesImpl movieServices;
    List<Movies> list= null;

    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);

        movies = new Movies();
        movies.setComments("Very Good");
        movies.setImdbId("31");
        movies.setYearOfRelease("2014");
        movies.setMovieTitle("Venom");
        movies.setRating(4.4);
        movies.setPosterURL("#######");

        list = new ArrayList<>();
        list.add(movies);
    }

    @Test
    public void saveMoviesTestSuccess() throws MovieAlreadyExistsException {

        when(movieRepository.save((Movies)any())).thenReturn(movies);
        Movies savedMovie = movieServices.saveMovie(movies);
        Assert.assertEquals(movies,savedMovie);

        //verify here verifies that movieRepository save method is only called once
        verify(movieRepository,times(1)).save(movies);
    }

    @Test(expected = MovieAlreadyExistsException.class)
    public void saveMoviesTestFailure() throws MovieAlreadyExistsException {
        when(movieRepository.save((Movies) any())).thenReturn(null);
        Movies savedMovie = movieServices.saveMovie(movies);
        System.out.println("savedMovies" + savedMovie);
        Assert.assertEquals(movies,savedMovie);
//add verify
       doThrow(new MovieAlreadyExistsException()).when(movieRepository).findById(eq("31"));
       movieServices.saveMovie(movies);
    }

    @Test
    public void getAllMovies() throws EmptyDBException {

        movieRepository.save(movies);
        //stubbing the mock to return specific data
        when(movieRepository.findAll()).thenReturn(list);
        List<Movies> moviesList = movieServices.getAllMovies();
        Assert.assertEquals(list,moviesList);

        //add verify
    }

    @Test(expected = MovieNotFoundException.class)
    public void testDeleteMovie() throws MovieNotFoundException {
        when(movieRepository.findById((String)any())).thenReturn(java.util.Optional.of((Movies) movies));
        Movies deletedMovie = movieServices.deleteMovie(movies);
        Assert.assertEquals(movies,deletedMovie);
    }

    @Test
    public void testUpdateMovie() throws MovieNotFoundException {
        when(movieRepository.save(movies)).thenReturn(movies);
        when(movieRepository.existsById(movies.getImdbId())).thenReturn(true);
        when(movieRepository.findByimdbId(movies.getImdbId())).thenReturn(movies);
        Movies updatedMovie = movieServices.updateMovie(movies,movies.getImdbId());
        updatedMovie.setComments("Is Funny");
        Assert.assertEquals("Is Funny",updatedMovie.getComments());
    }

    @Test(expected = MovieNotFoundException.class)
    public void testGetMovieByID() throws MovieNotFoundException {
        when(movieRepository.save(movies)).thenReturn(movies);
        when(movieRepository.existsById(movies.getImdbId())).thenReturn(true);
        when(movieRepository.findByimdbId(movies.getImdbId())).thenReturn(movies);
        Movies movieByID = movieServices.getByIMDBId("31");
        Assert.assertEquals(movies.getImdbId(),movieByID.getImdbId());
    }

    @Test
    public void testGetMovieByTitle() throws MovieNotFoundException {
        when(movieRepository.findByMovieTitle(any())).thenReturn(list);
        List<Movies> movieList = movieServices.getMovieByTitle(movies.getMovieTitle());
        Assert.assertEquals(1,movieList.size());
    }

}
