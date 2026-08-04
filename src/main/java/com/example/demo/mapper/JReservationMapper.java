package com.example.demo.mapper;

import com.example.demo.entity.JReservation;
import com.example.demo.model.Reservation;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JReservationMapper {

  private final JUserMapper userMapper;
  private final JProjectionMapper projectionMapper;
  private final JSeatMapper seatMapper;

  public JReservation toJpa(Reservation reservation) {
    if (reservation == null) {
      return null;
    }
    return JReservation.builder()
        .idReservation(reservation.getIdReservation())
        .user(userMapper.toJpa(reservation.getUser()))
        .projection(projectionMapper.toJpa(reservation.getProjection()))
        .createdAt(reservation.getCreatedAt())
        .seats(
            reservation.getSeats() == null
                ? new HashSet<>()
                : reservation.getSeats().stream()
                    .map(seatMapper::toJpa)
                    .collect(Collectors.toSet()))
        .build();
  }

  public Reservation toDomain(JReservation jReservation) {
    if (jReservation == null) {
      return null;
    }
    return Reservation.builder()
        .idReservation(jReservation.getIdReservation())
        .user(userMapper.toDomain(jReservation.getUser()))
        .projection(projectionMapper.toDomain(jReservation.getProjection()))
        .createdAt(jReservation.getCreatedAt())
        .seats(
            jReservation.getSeats() == null
                ? new HashSet<>()
                : jReservation.getSeats().stream()
                    .map(seatMapper::toDomain)
                    .collect(Collectors.toSet()))
        .build();
  }
}
