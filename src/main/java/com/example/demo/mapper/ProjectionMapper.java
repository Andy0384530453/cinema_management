package com.example.demo.mapper;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.model.Movie;
import com.example.demo.model.Projection;
import com.example.demo.model.Room;
import java.util.UUID;
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

  public Projection toDomain(ProjectionInput input) {
    if (input == null) {
      return null;
    }
    return Projection.builder()
        .idProjection(input.getIdProjection() != null ? input.getIdProjection() : UUID.randomUUID())
        .datetime(input.getDatetime())
        .seatPrice(input.getSeatPrice())
        .movie(
            input.getIdMovie() != null ? Movie.builder().idMovie(input.getIdMovie()).build() : null)
        .room(input.getIdRoom() != null ? Room.builder().idRoom(input.getIdRoom()).build() : null)
        .build();
  }
}
