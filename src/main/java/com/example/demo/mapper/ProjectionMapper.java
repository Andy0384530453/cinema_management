package com.example.demo.mapper;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.entity.Projection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectionMapper {

  private final MovieMapper movieMapper;
  private final RoomMapper roomMapper;

  public ProjectionDetail toDetail(Projection projection) {
    if (projection == null) {
      return null;
    }
    return ProjectionDetail.builder()
        .idProjection(projection.getIdProjection())
        .datetime(projection.getDatetime())
        .seatPrice(projection.getSeatPrice())
        .movie(movieMapper.toDetail(projection.getMovie()))
        .room(roomMapper.toDetail(projection.getRoom()))
        .build();
  }
}
