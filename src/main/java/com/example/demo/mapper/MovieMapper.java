package com.example.demo.mapper;

import com.example.demo.dto.MovieDetail;
import com.example.demo.entity.Movie;
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
}
