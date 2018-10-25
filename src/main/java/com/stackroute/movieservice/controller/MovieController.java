package com.stackroute.movieservice.controller;

import com.stackroute.movieservice.domain.Movies;
import com.stackroute.movieservice.exceptions.EmptyDBException;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.services.MovieServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value="MovieCruiser", description="Operations pertaining to Movie in Movie Cruiser App")
@RequestMapping("/api/v1")

public class MovieController {
    static Logger logger= LoggerFactory.getLogger(MovieController.class);
    @Autowired
    @Qualifier("Impl2")
    private MovieServices movieServices;

    @ApiOperation(value = "Save a Movie")
    @PostMapping("/movie")
    public ResponseEntity<?> saveMovie(@RequestBody @Valid Movies movies){
        ResponseEntity responseEntity;
        try {
            Movies storedMovie = movieServices.saveMovie(movies);
            responseEntity = new ResponseEntity<Movies>(storedMovie, HttpStatus.CREATED);

        }catch (MovieAlreadyExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
            logger.error(ex.getMessage());
        }return responseEntity;
    }

    @ApiOperation(value = "Show all Movies")
    @GetMapping("/movie")
    public ResponseEntity<?> getAllMovies(){
        ResponseEntity responseEntity;
        try {
            List<Movies> moviesList = movieServices.getAllMovies();
            responseEntity = new ResponseEntity<List<Movies>>(moviesList, HttpStatus.OK);
        }catch (EmptyDBException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
            logger.error(ex.getMessage());
        }
        return responseEntity;
    }

    @ApiOperation(value = "Find a movie by ID")
    @GetMapping("/movie/{imdbId}")
    public ResponseEntity<?> getById(@PathVariable @Valid String imdbId){
        ResponseEntity responseEntity;
        try {
            Movies returnedMovie = movieServices.getByIMDBId(imdbId);
            responseEntity = new ResponseEntity<Movies>(returnedMovie, HttpStatus.FOUND);
        }catch (MovieNotFoundException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
            logger.error(ex.getMessage());

        }
        return responseEntity;
    }

    @ApiOperation(value = "Delete a movie by ID")
    @DeleteMapping("/movie/{imdbId}")
    public  ResponseEntity<?> deleteMovie(@PathVariable @Valid String imdbId){
        ResponseEntity responseEntity;
        try {
            Movies returnedMovie = movieServices.getByIMDBId(imdbId);
            movieServices.deleteMovie(returnedMovie);
            responseEntity = new ResponseEntity<String>("Movie Deleted", HttpStatus.OK);
        }catch (MovieNotFoundException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
            logger.error(ex.getMessage());

        }
        return responseEntity;
    }

    @ApiOperation(value = "Update a movies comment")
    @PutMapping("/movie/{imdbId}")
    public ResponseEntity<?> updatedMovie(@PathVariable ("imdbId") String imdbId,@RequestBody @Valid Movies movie)
    {
        try {
            Movies updatedMovie = movieServices.updateMovie(movie, imdbId);
            return new ResponseEntity<Movies>(updatedMovie, HttpStatus.OK);
        }catch (MovieNotFoundException ex){
            logger.error(ex.getMessage());

            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Find a movie by title")
    @GetMapping("/movie/title/{movieTitle}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable @Valid String movieTitle){
        ResponseEntity responseEntity;
        try {
            List<Movies> returnedMovie = movieServices.getMovieByTitle(movieTitle);
            responseEntity = new ResponseEntity<List<Movies>>(returnedMovie, HttpStatus.OK);
        }catch (MovieNotFoundException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
            logger.error(ex.getMessage());

        }
        return responseEntity;
    }
}