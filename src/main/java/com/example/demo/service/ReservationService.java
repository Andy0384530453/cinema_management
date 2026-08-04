package com.example.demo.service;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.entity.JReservation;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.JReservationMapper;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.repository.ReservationRepository;
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
}
