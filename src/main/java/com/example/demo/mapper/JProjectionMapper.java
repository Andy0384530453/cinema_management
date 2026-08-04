package com.example.demo.mapper;

import com.example.demo.entity.JProjection;
import com.example.demo.model.Projection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JProjectionMapper {

  private final JMovieMapper movieMapper;
  private final JRoomMapper roomMapper;

  public JProjection toJpa(Projection projection) {
    if (projection == null) {
      return null;
    }
    return JProjection.builder()
        .idProjection(projection.getIdProjection())
        .datetime(projection.getDatetime())
        .seatPrice(projection.getSeatPrice())
        .movie(movieMapper.toJpa(projection.getMovie()))
        .room(roomMapper.toJpa(projection.getRoom()))
        .build();
  }

  public Projection toDomain(JProjection jProjection) {
    if (jProjection == null) {
      return null;
    }
    return Projection.builder()
        .idProjection(jProjection.getIdProjection())
        .datetime(jProjection.getDatetime())
        .seatPrice(jProjection.getSeatPrice())
        .movie(movieMapper.toDomain(jProjection.getMovie()))
        .room(roomMapper.toDomain(jProjection.getRoom()))
        .build();
  }
}
