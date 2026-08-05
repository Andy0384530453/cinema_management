package com.example.demo.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ProjectionInput;
import com.example.demo.entity.JMovie;
import com.example.demo.entity.JRoom;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.validator.ProjectionValidator.ProjectionDeps;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectionValidatorTest {

  @Mock private MovieRepository movieRepository;

  @Mock private RoomRepository roomRepository;

  @InjectMocks private ProjectionValidator projectionValidator;

  private ProjectionInput validInput() {
    return ProjectionInput.builder()
        .idProjection(UUID.randomUUID())
        .datetime(Instant.parse("2026-08-10T20:00:00Z"))
        .seatPrice(new BigDecimal("12.50"))
        .idMovie(UUID.randomUUID())
        .idRoom(UUID.randomUUID())
        .build();
  }

  @Test
  void validate_withValidInput_shouldReturnResolvedDeps() {
    ProjectionInput input = validInput();
    JMovie movie = JMovie.builder().idMovie(input.getIdMovie()).build();
    JRoom room = JRoom.builder().idRoom(input.getIdRoom()).build();
    when(movieRepository.findById(input.getIdMovie())).thenReturn(Optional.of(movie));
    when(roomRepository.findById(input.getIdRoom())).thenReturn(Optional.of(room));

    ProjectionDeps deps = projectionValidator.validate(input);

    assertEquals(movie, deps.movie());
    assertEquals(room, deps.room());
  }

  @Test
  void validate_withNullDatetime_shouldThrow400() {
    ProjectionInput input = validInput();
    input.setDatetime(null);

    assertThrows(BadRequestException.class, () -> projectionValidator.validate(input));
  }

  @Test
  void validate_withNullSeatPrice_shouldThrow400() {
    ProjectionInput input = validInput();
    input.setSeatPrice(null);

    assertThrows(BadRequestException.class, () -> projectionValidator.validate(input));
  }

  @Test
  void validate_withNullMovieId_shouldThrow400() {
    ProjectionInput input = validInput();
    input.setIdMovie(null);

    assertThrows(BadRequestException.class, () -> projectionValidator.validate(input));
  }

  @Test
  void validate_withNullRoomId_shouldThrow400() {
    ProjectionInput input = validInput();
    input.setIdRoom(null);

    assertThrows(BadRequestException.class, () -> projectionValidator.validate(input));
  }

  @Test
  void validate_withMissingMovie_shouldThrow404() {
    ProjectionInput input = validInput();
    when(movieRepository.findById(input.getIdMovie())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> projectionValidator.validate(input));
  }

  @Test
  void validate_withMissingRoom_shouldThrow404() {
    ProjectionInput input = validInput();
    when(movieRepository.findById(input.getIdMovie()))
        .thenReturn(Optional.of(JMovie.builder().idMovie(input.getIdMovie()).build()));
    when(roomRepository.findById(input.getIdRoom())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> projectionValidator.validate(input));
  }
}
