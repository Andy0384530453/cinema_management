package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.entity.Movie;
import com.example.demo.entity.enums.Genre;
import com.example.demo.exception.BadRequestException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.repository.MovieRepository;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
  @Mock private MovieRepository movieRepository;

  @Mock private MovieMapper movieMapper;

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
  void save_withNewMovie_shouldCreateAndReturnDetail() {
    MovieInput input = validInput();
    Movie saved = Movie.builder().idMovie(input.getIdMovie()).build();
    MovieDetail detail = MovieDetail.builder().idMovie(input.getIdMovie()).build();
    when(movieRepository.findById(input.getIdMovie())).thenReturn(Optional.empty());
    when(movieRepository.save(any(Movie.class))).thenReturn(saved);
    when(movieMapper.toDetail(saved)).thenReturn(detail);

    MovieDetail result = movieService.save(input);

    assertEquals(detail, result);
    ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
    verify(movieRepository).save(captor.capture());
    assertEquals(input.getIdMovie(), captor.getValue().getIdMovie());
    assertEquals("Inception", captor.getValue().getTitle());
    assertEquals(Genre.ACTION, captor.getValue().getGenre());
  }

  @Test
  void save_withNewMovieAndNullId_shouldGenerateUuid() {
    MovieInput input = validInput();
    input.setIdMovie(null);
    Movie saved = Movie.builder().idMovie(UUID.randomUUID()).build();
    when(movieRepository.findById(null)).thenReturn(Optional.empty());
    when(movieRepository.save(any(Movie.class))).thenReturn(saved);
    when(movieMapper.toDetail(saved)).thenReturn(MovieDetail.builder().build());

    movieService.save(input);

    ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
    verify(movieRepository).save(captor.capture());
    assertNotNull(captor.getValue().getIdMovie());
  }

  @Test
  void save_withExistingMovie_shouldUpdateAndReturnDetail() {
    MovieInput input = validInput();
    Movie existing = Movie.builder().idMovie(input.getIdMovie()).title("Old title").build();
    MovieDetail detail = MovieDetail.builder().idMovie(input.getIdMovie()).build();
    when(movieRepository.findById(input.getIdMovie())).thenReturn(Optional.of(existing));
    when(movieRepository.save(existing)).thenReturn(existing);
    when(movieMapper.toDetail(existing)).thenReturn(detail);

    MovieDetail result = movieService.save(input);

    assertEquals(detail, result);
    assertEquals("Inception", existing.getTitle());
    assertEquals(Genre.ACTION, existing.getGenre());
    assertEquals(Duration.ofHours(2).plusMinutes(28), existing.getDuration());
  }

  @Test
  void save_withBlankTitle_shouldThrow400() {
    MovieInput input = validInput();
    input.setTitle("   ");

    assertThrows(BadRequestException.class, () -> movieService.save(input));
  }

  @Test
  void save_withNullGenre_shouldThrow400() {
    MovieInput input = validInput();
    input.setGenre(null);

    assertThrows(BadRequestException.class, () -> movieService.save(input));
  }

  @Test
  void save_withNullDuration_shouldThrow400() {
    MovieInput input = validInput();
    input.setDuration(null);

    assertThrows(BadRequestException.class, () -> movieService.save(input));
  }
}
