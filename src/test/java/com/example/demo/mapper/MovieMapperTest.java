package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.dto.MovieDetail;
import com.example.demo.entity.Movie;
import com.example.demo.entity.enums.Genre;
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
}
