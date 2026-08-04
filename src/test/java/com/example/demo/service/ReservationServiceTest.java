package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.ReservationInput;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JReservation;
import com.example.demo.entity.JSeat;
import com.example.demo.entity.JUser;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.JReservationMapper;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.model.Projection;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
  @Mock private ReservationRepository reservationRepository;

  @Mock private ProjectionRepository projectionRepository;

  @Mock private UserRepository userRepository;

  @Mock private SeatRepository seatRepository;

  @Mock private ReservationMapper reservationMapper;

  @Mock private JReservationMapper jReservationMapper;

  @InjectMocks private ReservationService reservationService;

  @Test
  void findAll_shouldReturnMappedReservations() {
    JReservation jReservation = JReservation.builder().idReservation(UUID.randomUUID()).build();
    Reservation reservation =
        Reservation.builder().idReservation(jReservation.getIdReservation()).build();
    ReservationDetail detail =
        ReservationDetail.builder().idReservation(reservation.getIdReservation()).build();
    when(reservationRepository.findAll()).thenReturn(List.of(jReservation));
    when(jReservationMapper.toDomain(jReservation)).thenReturn(reservation);
    when(reservationMapper.toDetail(reservation)).thenReturn(detail);

    List<ReservationDetail> result = reservationService.findAll();

    assertEquals(List.of(detail), result);
  }

  @Test
  void findAll_withEmptyTable_shouldReturnEmptyList() {
    when(reservationRepository.findAll()).thenReturn(List.of());

    assertEquals(List.of(), reservationService.findAll());
  }

  @Test
  void findById_withExistingReservation_shouldReturnMappedDetail() {
    UUID id = UUID.randomUUID();
    JReservation jReservation = JReservation.builder().idReservation(id).build();
    Reservation reservation = Reservation.builder().idReservation(id).build();
    ReservationDetail detail = ReservationDetail.builder().idReservation(id).build();
    when(reservationRepository.findById(id)).thenReturn(Optional.of(jReservation));
    when(jReservationMapper.toDomain(jReservation)).thenReturn(reservation);
    when(reservationMapper.toDetail(reservation)).thenReturn(detail);

    ReservationDetail result = reservationService.findById(id);

    assertEquals(detail, result);
  }

  @Test
  void findById_withNonExistingReservation_shouldThrow404() {
    UUID id = UUID.randomUUID();
    when(reservationRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationService.findById(id));
  }

  @Test
  void save_shouldAttachEntitiesAndReturnDetail() {
    UUID userId = UUID.randomUUID();
    UUID idProjection = UUID.randomUUID();
    UUID seatId = UUID.randomUUID();
    ReservationInput input =
        ReservationInput.builder()
            .idReservation(UUID.randomUUID())
            .idProjection(idProjection)
            .seatIds(Set.of(seatId))
            .build();
    Reservation domain =
        Reservation.builder()
            .idReservation(input.getIdReservation())
            .projection(Projection.builder().idProjection(idProjection).build())
            .build();
    JUser user = JUser.builder().idUser(userId).build();
    JProjection projection = JProjection.builder().idProjection(idProjection).build();
    JSeat seat = JSeat.builder().idSeat(seatId).build();
    JReservation saved = JReservation.builder().idReservation(input.getIdReservation()).build();
    Reservation savedDomain = Reservation.builder().idReservation(saved.getIdReservation()).build();
    ReservationDetail detail =
        ReservationDetail.builder().idReservation(saved.getIdReservation()).build();

    when(reservationMapper.toDomain(input)).thenReturn(domain);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(projectionRepository.findById(idProjection)).thenReturn(Optional.of(projection));
    when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
    when(reservationRepository.save(any(JReservation.class))).thenReturn(saved);
    when(jReservationMapper.toDomain(saved)).thenReturn(savedDomain);
    when(reservationMapper.toDetail(savedDomain)).thenReturn(detail);

    ReservationDetail result = reservationService.save(input, userId);

    assertEquals(detail, result);
    ArgumentCaptor<JReservation> captor = ArgumentCaptor.forClass(JReservation.class);
    verify(reservationRepository).save(captor.capture());
    JReservation captured = captor.getValue();
    assertEquals(userId, captured.getUser().getIdUser());
    assertEquals(idProjection, captured.getProjection().getIdProjection());
    assertEquals(
        Set.of(seatId),
        captured.getSeats().stream().map(JSeat::getIdSeat).collect(Collectors.toSet()));
    assertNotNull(captured.getCreatedAt());
  }

  @Test
  void save_withNullProjection_shouldThrow400() {
    ReservationInput input = ReservationInput.builder().idReservation(UUID.randomUUID()).build();

    assertThrows(
        BadRequestException.class, () -> reservationService.save(input, UUID.randomUUID()));
  }

  @Test
  void save_withMissingUser_shouldThrow404() {
    UUID userId = UUID.randomUUID();
    UUID idProjection = UUID.randomUUID();
    ReservationInput input =
        ReservationInput.builder()
            .idReservation(UUID.randomUUID())
            .idProjection(idProjection)
            .build();
    Reservation domain =
        Reservation.builder()
            .idReservation(input.getIdReservation())
            .projection(Projection.builder().idProjection(idProjection).build())
            .build();
    when(reservationMapper.toDomain(input)).thenReturn(domain);
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationService.save(input, userId));
  }

  @Test
  void save_withMissingProjection_shouldThrow404() {
    UUID userId = UUID.randomUUID();
    UUID idProjection = UUID.randomUUID();
    ReservationInput input =
        ReservationInput.builder()
            .idReservation(UUID.randomUUID())
            .idProjection(idProjection)
            .build();
    Reservation domain =
        Reservation.builder()
            .idReservation(input.getIdReservation())
            .projection(Projection.builder().idProjection(idProjection).build())
            .build();
    when(reservationMapper.toDomain(input)).thenReturn(domain);
    when(userRepository.findById(userId))
        .thenReturn(Optional.of(JUser.builder().idUser(userId).build()));
    when(projectionRepository.findById(idProjection)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationService.save(input, userId));
  }

  @Test
  void save_withMissingSeat_shouldThrow404() {
    UUID userId = UUID.randomUUID();
    UUID idProjection = UUID.randomUUID();
    UUID seatId = UUID.randomUUID();
    ReservationInput input =
        ReservationInput.builder()
            .idReservation(UUID.randomUUID())
            .idProjection(idProjection)
            .seatIds(Set.of(seatId))
            .build();
    Reservation domain =
        Reservation.builder()
            .idReservation(input.getIdReservation())
            .projection(Projection.builder().idProjection(idProjection).build())
            .build();
    when(reservationMapper.toDomain(input)).thenReturn(domain);
    when(userRepository.findById(userId))
        .thenReturn(Optional.of(JUser.builder().idUser(userId).build()));
    when(projectionRepository.findById(idProjection))
        .thenReturn(Optional.of(JProjection.builder().idProjection(idProjection).build()));
    when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> reservationService.save(input, userId));
  }
}
