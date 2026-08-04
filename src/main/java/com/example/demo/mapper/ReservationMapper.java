package com.example.demo.mapper;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.ReservationInput;
import com.example.demo.model.Projection;
import com.example.demo.model.Reservation;
import java.util.UUID;
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

  public Reservation toDomain(ReservationInput input) {
    if (input == null) {
      return null;
    }
    return Reservation.builder()
        .idReservation(
            input.getIdReservation() != null ? input.getIdReservation() : UUID.randomUUID())
        .projection(
            input.getIdProjection() != null
                ? Projection.builder().idProjection(input.getIdProjection()).build()
                : null)
        .build();
  }
}
