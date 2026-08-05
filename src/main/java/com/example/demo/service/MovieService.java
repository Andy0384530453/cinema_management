package com.example.demo.service;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.mapper.JMovieMapper;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.validator.MovieValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;
  private final MovieMapper movieMapper;
  private final JMovieMapper jMovieMapper;
  private final MovieValidator movieValidator;

  public MovieDetail save(MovieInput input) {
    movieValidator.validate(input);
    Movie movie = movieMapper.toDomain(input);
    Movie saved = jMovieMapper.toDomain(movieRepository.save(jMovieMapper.toJpa(movie)));
    return movieMapper.toDetail(saved);
  }
}
