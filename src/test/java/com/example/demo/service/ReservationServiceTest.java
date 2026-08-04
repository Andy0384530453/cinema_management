package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.entity.JReservation;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.JReservationMapper;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
  @Mock private ReservationRepository reservationRepository;

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
}
