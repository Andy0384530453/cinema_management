package com.example.demo.mapper;

import com.example.demo.entity.JMovie;
import com.example.demo.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class JMovieMapper {

  public JMovie toJpa(Movie movie) {
    if (movie == null) {
      return null;
    }
    return JMovie.builder()
        .idMovie(movie.getIdMovie())
        .title(movie.getTitle())
        .genre(movie.getGenre())
        .description(movie.getDescription())
        .duration(movie.getDuration())
        .build();
  }

  public Movie toDomain(JMovie jMovie) {
    if (jMovie == null) {
      return null;
    }
    return Movie.builder()
        .idMovie(jMovie.getIdMovie())
        .title(jMovie.getTitle())
        .genre(jMovie.getGenre())
        .description(jMovie.getDescription())
        .duration(jMovie.getDuration())
        .build();
  }
}
