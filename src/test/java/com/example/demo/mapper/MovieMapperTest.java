package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MovieMapperTest {
  private final MovieMapper movieMapper = new MovieMapper();

  @Test
  void toDetail_shouldMapAllFields() {
    Movie movie =
        Movie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();

    MovieDetail detail = movieMapper.toDetail(movie);

    assertEquals(movie.getIdMovie(), detail.getIdMovie());
    assertEquals("Inception", detail.getTitle());
    assertEquals(Genre.ACTION, detail.getGenre());
    assertEquals("A thief who steals corporate secrets", detail.getDescription());
    assertEquals(Duration.ofHours(2).plusMinutes(28), detail.getDuration());
  }

  @Test
  void toDetail_withNull_shouldReturnNull() {
    assertNull(movieMapper.toDetail(null));
  }

  @Test
  void toDomain_shouldMapAllFieldsAndKeepProvidedUuid() {
    UUID id = UUID.randomUUID();
    MovieInput input =
        MovieInput.builder()
            .idMovie(id)
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();

    Movie movie = movieMapper.toDomain(input);

    assertEquals(id, movie.getIdMovie());
    assertEquals("Inception", movie.getTitle());
    assertEquals(Genre.ACTION, movie.getGenre());
    assertEquals("A thief who steals corporate secrets", movie.getDescription());
    assertEquals(Duration.ofHours(2).plusMinutes(28), movie.getDuration());
  }

  @Test
  void toDomain_withNullUuid_shouldGenerateUuid() {
    MovieInput input =
        MovieInput.builder()
            .title("Inception")
            .genre(Genre.ACTION)
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();

    Movie movie = movieMapper.toDomain(input);

    assertNotNull(movie.getIdMovie());
  }

  @Test
  void toDomain_withNull_shouldReturnNull() {
    assertNull(movieMapper.toDomain(null));
  }
}