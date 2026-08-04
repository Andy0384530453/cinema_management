package com.example.demo.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import com.example.demo.model.UserRole;
import com.example.demo.service.MovieService;
import com.example.demo.service.SecurityService;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({MovieController.class, GlobalExceptionHandler.class})
class MovieControllerTest {
  private Movie movie;
  private MovieDetail movieDetail;

  @Autowired private MockMvc mockMvc;

  @MockBean private MovieService movieService;

  @MockBean private SecurityService securityService;

  @BeforeEach
  void setUp() {
    movie =
        Movie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();
    movieDetail =
        MovieDetail.builder()
            .idMovie(movie.getIdMovie())
            .title(movie.getTitle())
            .genre(movie.getGenre())
            .description(movie.getDescription())
            .duration(movie.getDuration())
            .build();
  }

  private String movieJson() {
    return """
           {"idMovie":"%s","title":"%s","genre":"%s","description":"%s","duration":"PT2H28M"}
           """
        .formatted(movie.getIdMovie(), movie.getTitle(), movie.getGenre(), movie.getDescription());
  }

  @Test
  void putMovie_withManagerRole_shouldReturn200() throws Exception {
    when(movieService.save(any(MovieInput.class))).thenReturn(movieDetail);

    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieJson()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idMovie").value(movie.getIdMovie().toString()))
        .andExpect(jsonPath("$.title").value("Inception"))
        .andExpect(jsonPath("$.genre").value("ACTION"))
        .andExpect(jsonPath("$.duration").value("PT2H28M"));
  }

  @Test
  void putMovie_withClientRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("Access denied for role CLIENT"))
        .when(securityService)
        .requireRole(UserRole.MANAGER);

    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieJson()))
        .andExpect(status().isForbidden());
  }

  @Test
  void putMovie_withEmployeeRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("Access denied for role EMPLOYEE"))
        .when(securityService)
        .requireRole(UserRole.MANAGER);

    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieJson()))
        .andExpect(status().isForbidden());
  }

  @Test
  void putMovie_withMissingTitle_shouldThrow400() throws Exception {
    when(movieService.save(any(MovieInput.class)))
        .thenThrow(new BadRequestException("Title is required"));

    mockMvc
        .perform(
            put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {"idMovie":"%s","genre":"ACTION","duration":"PT2H28M"}
                    """
                        .formatted(UUID.randomUUID())))
        .andExpect(status().isBadRequest());
  }

  @Test
  void putMovie_withInvalidJson_shouldThrow400() throws Exception {
    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content("{invalid"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void putMovie_withServiceFailure_shouldThrow500() throws Exception {
    when(movieService.save(any(MovieInput.class))).thenThrow(new RuntimeException("boom"));

    mockMvc
        .perform(put("/movies").contentType(MediaType.APPLICATION_JSON).content(movieJson()))
        .andExpect(status().isInternalServerError());
  }
}
