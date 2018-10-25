package com.stackroute.movieservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.movieservice.domain.Movies;
import com.stackroute.movieservice.exceptions.EmptyDBException;
import com.stackroute.movieservice.exceptions.MovieAlreadyExistsException;
import com.stackroute.movieservice.exceptions.MovieNotFoundException;
import com.stackroute.movieservice.services.MovieServicesImpl2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private Movies movies;

    @MockBean
    private MovieServicesImpl2 movieServices;

    @InjectMocks
    private MovieController movieController;

    private List<Movies> list =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movies = new Movies();
        movies.setComments("Very Good");
        movies.setImdbId("31");
        movies.setYearOfRelease("2014");
        movies.setMovieTitle("Venom");
        movies.setRating(4.4);
        movies.setPosterURL("#######");
        list = new ArrayList();

        list.add(movies);
    }

    @Test
    public void saveMovie() throws Exception {
        when(movieServices.saveMovie(any())).thenReturn(movies);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

      @Test
     public void saveMovieFailure() throws Exception {
        when(movieServices.saveMovie(any())).thenThrow(MovieAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMovie() throws Exception {
        when(movieServices.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMoviesFailure() throws Exception{
        when(movieServices.getAllMovies()).thenThrow(EmptyDBException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchMovieByName() throws Exception{
        when(movieServices.getMovieByTitle((String)any())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/title/Venom")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchMovieByNameFailure() throws Exception{
        when(movieServices.getMovieByTitle((String)any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/title/Venom")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByID() throws Exception {
        when(movieServices.getByIMDBId((String)any())).thenReturn(movies);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByIDFailure() throws Exception {
        when(movieServices.getByIMDBId((String)any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovie() throws Exception{
        when(movieServices.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1//movie/31")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovieFailure() throws Exception{
        when(movieServices.updateMovie(any(), any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1//movie/311")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovie() throws Exception {
        //movieService.saveMovie(movie);
        when(movieServices.getAllMovies()).thenReturn(list);
        System.out.println(list);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/31")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieFailure() throws Exception{
        when(movieServices.deleteMovie(any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/55")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movies)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
