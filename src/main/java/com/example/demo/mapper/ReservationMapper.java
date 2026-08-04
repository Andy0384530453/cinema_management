package com.example.demo.mapper;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

  private final UserMapper userMapper;
  private final ProjectionMapper projectionMapper;

  public ReservationDetail toDetail(Reservation reservation) {
    if (reservation == null) {
      return null;
    }
    return ReservationDetail.builder()
        .idReservation(reservation.getIdReservation())
        .user(userMapper.toDetail(reservation.getUser()))
        .projection(projectionMapper.toDetail(reservation.getProjection()))
        .build();
  }
}
