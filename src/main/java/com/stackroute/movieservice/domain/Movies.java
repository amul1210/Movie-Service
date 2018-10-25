package com.stackroute.movieservice.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Document(collection = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movies {

    @Id
    @ApiModelProperty(notes = "Primary ID")
    @NotNull(message = "Id cannot be null")
    @Size(min = 1, message = "Id cannot be null")
    private String imdbId;

    @ApiModelProperty(notes = "Title of the Movie")
    @NotNull
    @Size(min = 1, message = "Minimum length for movie title is 1")
    private String movieTitle;

    @ApiModelProperty(notes = "Poster Url")
    @NotNull
    @Size(min = 5, message = "Minimum length for Url is 5")
    private String posterURL;

    @ApiModelProperty(notes = "Movie rating out of 5")
    @NotNull(message = "Rating Cannot be empty")
    private double rating;

    @ApiModelProperty(notes = "Year of Realease")
    @NotNull
    @Size(min = 2, message = "Year of release should have atleast two digits")
    private String yearOfRelease;

    @ApiModelProperty(notes = "Comment")
    @Size(min = 2, message = "Comment should have more than two characters")
    private String comments;

//    public Movies() {
//    }
//
//    public Movies(String imdbId, String movieTitle, String posterURL, double rating, String yearOfRelease, String comments) {
//        this.imdbId = imdbId;
//        this.movieTitle = movieTitle;
//        this.posterURL = posterURL;
//        this.rating = rating;
//        this.yearOfRelease = yearOfRelease;
//        this.comments = comments;
//    }
//
//    public String getImdbId() {
//        return imdbId;
//    }
//
//    public void setImdbId(String imdbId) {
//        this.imdbId = imdbId;
//    }
//
//    public String getMovieTitle() {
//        return movieTitle;
//    }
//
//    public void setMovieTitle(String movieTitle) {
//        this.movieTitle = movieTitle;
//    }
//
//    public String getPosterURL() {
//        return posterURL;
//    }
//
//    public void setPosterURL(String posterURL) {
//        this.posterURL = posterURL;
//    }
//
//    public double getRating() {
//        return rating;
//    }
//
//    public void setRating(double rating) {
//        this.rating = rating;
//    }
//
//    public String getYearOfRelease() {
//        return yearOfRelease;
//    }
//
//    public void setYearOfRelease(String yearOfRelease) {
//        this.yearOfRelease = yearOfRelease;
//    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
//
//    @Override
//    public String toString() {
//        return "Movies{" +
//                "imdbId='" + imdbId + '\'' +
//                ", movieTitle='" + movieTitle + '\'' +
//                ", posterURL='" + posterURL + '\'' +
//                ", rating=" + rating +
//                ", yearOfRelease='" + yearOfRelease + '\'' +
//                ", comments='" + comments + '\'' +
//                '}';
//    }
}