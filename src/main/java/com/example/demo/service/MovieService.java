package com.example.demo.service;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.entity.Movie;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.MovieRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;

  public MovieDetail save(MovieInput input) {
    validate(input);
    Movie movie =
        movieRepository
            .findById(input.getIdMovie())
            .map(existing -> update(existing, input))
            .orElseGet(() -> create(input));
    return movieMapper.toDetail(movieRepository.save(movie));
  }

  private void validate(MovieInput input) {
    if (input.getTitle() == null || input.getTitle().isBlank()) {
      throw new BadRequestException("Title is required");
    }
    if (input.getGenre() == null) {
      throw new BadRequestException("Genre is required");
    }
    if (input.getDuration() == null) {
      throw new BadRequestException("Duration is required");
    }
  }

  private Movie create(MovieInput input) {
    return Movie.builder()
        .idMovie(input.getIdMovie() != null ? input.getIdMovie() : UUID.randomUUID())
        .title(input.getTitle())
        .genre(input.getGenre())
        .description(input.getDescription())
        .duration(input.getDuration())
        .build();
  }

  private Movie update(Movie existing, MovieInput input) {
    existing.setTitle(input.getTitle());
    existing.setGenre(input.getGenre());
    existing.setDescription(input.getDescription());
    existing.setDuration(input.getDuration());
    return existing;
  }
}
