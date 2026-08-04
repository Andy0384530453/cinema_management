package com.example.demo.service;

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
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ProjectionRepository projectionRepository;
  private final UserRepository userRepository;
  private final SeatRepository seatRepository;
  private final ReservationMapper reservationMapper;
  private final JReservationMapper jReservationMapper;

  public List<ReservationDetail> findAll() {
    return reservationRepository.findAll().stream()
        .map(jReservationMapper::toDomain)
        .map(reservationMapper::toDetail)
        .toList();
  }

  public ReservationDetail findById(UUID id) {
    JReservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Reservation with id " + id + " not found"));
    return reservationMapper.toDetail(jReservationMapper.toDomain(reservation));
  }

  public ReservationDetail save(ReservationInput input, UUID userId) {
    validate(input);
    com.example.demo.model.Reservation reservation = reservationMapper.toDomain(input);
    JUser user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    JProjection projection =
        projectionRepository
            .findById(reservation.getProjection().getIdProjection())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Projection with id "
                            + reservation.getProjection().getIdProjection()
                            + " not found"));
    Set<JSeat> seats =
        input.getSeatIds() == null
            ? new HashSet<>()
            : input.getSeatIds().stream()
                .map(
                    seatId ->
                        seatRepository
                            .findById(seatId)
                            .orElseThrow(
                                () ->
                                    new NotFoundException("Seat with id " + seatId + " not found")))
                .collect(Collectors.toSet());
    JReservation jReservation =
        JReservation.builder()
            .idReservation(reservation.getIdReservation())
            .user(user)
            .projection(projection)
            .createdAt(Instant.now())
            .seats(seats)
            .build();
    return reservationMapper.toDetail(
        jReservationMapper.toDomain(reservationRepository.save(jReservation)));
  }

  private void validate(ReservationInput input) {
    if (input.getIdProjection() == null) {
      throw new BadRequestException("Projection is required");
    }
  }
}
