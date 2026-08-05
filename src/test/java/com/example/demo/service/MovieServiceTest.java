package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.entity.JMovie;
import com.example.demo.mapper.JMovieMapper;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.validator.MovieValidator;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

  @Mock private MovieRepository movieRepository;

  @Mock private MovieMapper movieMapper;

  @Mock private JMovieMapper jMovieMapper;

  @Mock private MovieValidator movieValidator;

  @InjectMocks private MovieService movieService;

  private MovieInput validInput() {
    return MovieInput.builder()
        .idMovie(UUID.randomUUID())
        .title("Inception")
        .genre(Genre.ACTION)
        .description("A thief who steals corporate secrets")
        .duration(Duration.ofHours(2).plusMinutes(28))
        .build();
  }

  @Test
  void save_shouldMapInputToEntityThenBackToDetail() {
    MovieInput input = validInput();
    Movie domain = Movie.builder().idMovie(input.getIdMovie()).build();
    JMovie jpa = JMovie.builder().idMovie(input.getIdMovie()).build();
    JMovie savedJpa = JMovie.builder().idMovie(input.getIdMovie()).build();
    Movie savedDomain = Movie.builder().idMovie(input.getIdMovie()).build();
    MovieDetail detail = MovieDetail.builder().idMovie(input.getIdMovie()).build();

    when(movieMapper.toDomain(input)).thenReturn(domain);
    when(jMovieMapper.toJpa(domain)).thenReturn(jpa);
    when(movieRepository.save(jpa)).thenReturn(savedJpa);
    when(jMovieMapper.toDomain(savedJpa)).thenReturn(savedDomain);
    when(movieMapper.toDetail(savedDomain)).thenReturn(detail);

    MovieDetail result = movieService.save(input);

    assertEquals(detail, result);
    verify(movieValidator).validate(input);
    verify(movieRepository).save(jpa);
  }
}
