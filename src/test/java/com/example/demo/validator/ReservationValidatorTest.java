package com.example.demo.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ReservationInput;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JSeat;
import com.example.demo.entity.JUser;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.validator.ReservationValidator.ReservationDeps;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationValidatorTest {

  @Mock private UserRepository userRepository;

  @Mock private ProjectionRepository projectionRepository;

  @Mock private SeatRepository seatRepository;

  @InjectMocks private ReservationValidator reservationValidator;

  private ReservationInput validInput(UUID seatId) {
    return ReservationInput.builder()
        .idReservation(UUID.randomUUID())
        .idProjection(UUID.randomUUID())
        .seatIds(Set.of(seatId))
        .build();
  }

  @Test
  void validate_withValidInput_shouldReturnResolvedDeps() {
    UUID userId = UUID.randomUUID();
    UUID seatId = UUID.randomUUID();
    ReservationInput input = validInput(seatId);
    JUser user = JUser.builder().idUser(userId).build();
    JProjection projection = JProjection.builder().idProjection(input.getIdProjection()).build();
    JSeat seat = JSeat.builder().idSeat(seatId).build();
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(projectionRepository.findById(input.getIdProjection()))
        .thenReturn(Optional.of(projection));
    when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

    ReservationDeps deps = reservationValidator.validate(input, userId);

    assertEquals(user, deps.user());
    assertEquals(projection, deps.projection());
    assertEquals(
        Set.of(seatId), deps.seats().stream().map(JSeat::getIdSeat).collect(Collectors.toSet()));
  }

  @Test
  void validate_withNullSeatIds_shouldReturnEmptySeats() {
    UUID userId = UUID.randomUUID();
    ReservationInput input =
        ReservationInput.builder()
            .idReservation(UUID.randomUUID())
            .idProjection(UUID.randomUUID())
            .build();
    when(userRepository.findById(userId))
        .thenReturn(Optional.of(JUser.builder().idUser(userId).build()));
    when(projectionRepository.findById(input.getIdProjection()))
        .thenReturn(
            Optional.of(JProjection.builder().idProjection(input.getIdProjection()).build()));

    ReservationDeps deps = reservationValidator.validate(input, userId);

    assertTrue(deps.seats().isEmpty());
  }

  @Test
  void validate_withNullProjectionId_shouldThrow400() {
    ReservationInput input = ReservationInput.builder().idReservation(UUID.randomUUID()).build();

    assertThrows(
        BadRequestException.class, () -> reservationValidator.validate(input, UUID.randomUUID()));
  }

  @Test
  void validate_withMissingUser_shouldThrow404() {
    UUID userId = UUID.randomUUID();
    ReservationInput input = validInput(UUID.randomUUID());
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationValidator.validate(input, userId));
  }

  @Test
  void validate_withMissingProjection_shouldThrow404() {
    UUID userId = UUID.randomUUID();
    ReservationInput input = validInput(UUID.randomUUID());
    when(userRepository.findById(userId))
        .thenReturn(Optional.of(JUser.builder().idUser(userId).build()));
    when(projectionRepository.findById(input.getIdProjection())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationValidator.validate(input, userId));
  }

  @Test
  void validate_withMissingSeat_shouldThrow404() {
    UUID userId = UUID.randomUUID();
    UUID seatId = UUID.randomUUID();
    ReservationInput input = validInput(seatId);
    when(userRepository.findById(userId))
        .thenReturn(Optional.of(JUser.builder().idUser(userId).build()));
    when(projectionRepository.findById(input.getIdProjection()))
        .thenReturn(
            Optional.of(JProjection.builder().idProjection(input.getIdProjection()).build()));
    when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationValidator.validate(input, userId));
  }
}
