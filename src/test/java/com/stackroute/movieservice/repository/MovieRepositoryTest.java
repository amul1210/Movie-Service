package com.stackroute.movieservice.repository;

import com.stackroute.movieservice.domain.Movies;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


//This is integrated test we need DB server
@RunWith(SpringRunner.class)
@DataMongoTest
//@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    Movies movies;

    @Before
    public void setUp()
    {

        movies = new Movies();
        movies.setComments("Very Good");
        movies.setImdbId("31");
        movies.setYearOfRelease("2014");
        movies.setMovieTitle("Venom");
        movies.setRating(4.4);
        movies.setPosterURL("#######");
    }

    @After
    public void tearDown(){

        movieRepository.deleteAll();
    }


    @Test
    public void testSaveMovie(){
        movieRepository.save(movies);
        Movies fetchMovie = movieRepository.findByimdbId(movies.getImdbId());
        Assert.assertEquals("31",fetchMovie.getImdbId());

    }

    @Test
    public void testSaveMovieFailure(){
        Movies testMovie = new Movies("12", "Inception", "######", 4.8,"2015", "excellent");
        movieRepository.save(movies);
        Movies fetchMovie = movieRepository.findByimdbId(movies.getImdbId());
        Assert.assertNotSame(movies,fetchMovie);
    }

    @Test
    public void testGetAllMovie(){
        Movies u = new Movies("12", "Inception", "######", 4.8,"2015", "excellent");
        Movies u1 = new Movies("13", "Prestige", "######", 4.7,"2014", "awesome");
        movieRepository.save(u);
        movieRepository.save(u1);

        List<Movies> movies = (List<Movies>)movieRepository.findAll();
        Assert.assertEquals("Inception",movies.get(0).getMovieTitle());
    }

    @Test
    public void  testFindMovieByTitle(){
        movieRepository.save(movies);
        Movies fetchMovie = movieRepository.findByMovieTitle(movies.getMovieTitle()).get(0);
        Assert.assertEquals("Venom", fetchMovie.getMovieTitle());
    }


    @Test
    public void testDeleteById(){
        movieRepository.save(movies);
        movieRepository.delete(movies);
        Assert.assertEquals(null, movieRepository.findByimdbId(movies.getImdbId()));
    }


}