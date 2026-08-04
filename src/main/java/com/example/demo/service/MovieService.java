package com.example.demo.service;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.JMovieMapper;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;
  private final JMovieMapper jMovieMapper;

  public MovieDetail save(MovieInput input) {
    validate(input);
    Movie movie = movieMapper.toDomain(input);
    Movie saved = jMovieMapper.toDomain(movieRepository.save(jMovieMapper.toJpa(movie)));
    return movieMapper.toDetail(saved);
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
}
