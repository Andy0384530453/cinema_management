package com.example.demo.mapper;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.model.Movie;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

  public MovieDetail toDetail(Movie movie) {
    if (movie == null) {
      return null;
    }
    return MovieDetail.builder()
        .idMovie(movie.getIdMovie())
        .title(movie.getTitle())
        .genre(movie.getGenre())
        .description(movie.getDescription())
        .duration(movie.getDuration())
        .build();
  }

  public Movie toDomain(MovieInput input) {
    if (input == null) {
      return null;
    }
    return Movie.builder()
        .idMovie(input.getIdMovie() != null ? input.getIdMovie() : UUID.randomUUID())
        .title(input.getTitle())
        .genre(input.getGenre())
        .description(input.getDescription())
        .duration(input.getDuration())
        .build();
  }
}
