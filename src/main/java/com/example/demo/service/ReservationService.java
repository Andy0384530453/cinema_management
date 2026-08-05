package com.example.demo.service;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.ReservationInput;
import com.example.demo.entity.JReservation;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.JReservationMapper;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.validator.ReservationValidator;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationMapper reservationMapper;
  private final JReservationMapper jReservationMapper;
  private final ReservationValidator reservationValidator;

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
    ReservationValidator.ReservationDeps deps = reservationValidator.validate(input, userId);
    com.example.demo.model.Reservation reservation = reservationMapper.toDomain(input);
    JReservation jReservation =
        JReservation.builder()
            .idReservation(reservation.getIdReservation())
            .user(deps.user())
            .projection(deps.projection())
            .createdAt(Instant.now())
            .seats(deps.seats())
            .build();
    return reservationMapper.toDetail(
        jReservationMapper.toDomain(reservationRepository.save(jReservation)));
  }
}
