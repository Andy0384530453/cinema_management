package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JMovie;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JMovieMapperTest {
  private final JMovieMapper jMovieMapper = new JMovieMapper();

  @Test
  void toJpa_shouldMapAllFields() {
    Movie movie =
        Movie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();

    JMovie jMovie = jMovieMapper.toJpa(movie);

    assertEquals(movie.getIdMovie(), jMovie.getIdMovie());
    assertEquals("Inception", jMovie.getTitle());
    assertEquals(Genre.ACTION, jMovie.getGenre());
    assertEquals("A thief who steals corporate secrets", jMovie.getDescription());
    assertEquals(Duration.ofHours(2).plusMinutes(28), jMovie.getDuration());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JMovie jMovie =
        JMovie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();

    Movie movie = jMovieMapper.toDomain(jMovie);

    assertEquals(jMovie.getIdMovie(), movie.getIdMovie());
    assertEquals("Inception", movie.getTitle());
    assertEquals(Genre.ACTION, movie.getGenre());
    assertEquals("A thief who steals corporate secrets", movie.getDescription());
    assertEquals(Duration.ofHours(2).plusMinutes(28), movie.getDuration());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jMovieMapper.toJpa(null));
    assertNull(jMovieMapper.toDomain(null));
  }
}
