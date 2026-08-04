package com.example.demo.mapper;

import com.example.demo.entity.JReservation;
import com.example.demo.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JReservationMapper {

  private final JUserMapper userMapper;
  private final JProjectionMapper projectionMapper;

  public JReservation toJpa(Reservation reservation) {
    if (reservation == null) {
      return null;
    }
    return JReservation.builder()
        .idReservation(reservation.getIdReservation())
        .user(userMapper.toJpa(reservation.getUser()))
        .projection(projectionMapper.toJpa(reservation.getProjection()))
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
        .build();
  }
}
