package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import com.example.demo.model.Projection;
import com.example.demo.model.Room;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProjectionMapperTest {
  private final MovieMapper movieMapper = new MovieMapper();
  private final RoomMapper roomMapper = new RoomMapper();
  private final ProjectionMapper projectionMapper = new ProjectionMapper(movieMapper, roomMapper);

  @Test
  void toDetail_shouldMapAllFields() {
    Movie movie =
        Movie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();
    Room room = Room.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();
    Projection projection =
        Projection.builder()
            .idProjection(UUID.randomUUID())
            .datetime(Instant.parse("2026-08-10T20:00:00Z"))
            .seatPrice(new BigDecimal("12.50"))
            .movie(movie)
            .room(room)
            .build();

    ProjectionDetail detail = projectionMapper.toDetail(projection);

    assertEquals(projection.getIdProjection(), detail.getIdProjection());
    assertEquals(Instant.parse("2026-08-10T20:00:00Z"), detail.getDatetime());
    assertEquals(new BigDecimal("12.50"), detail.getSeatPrice());
    assertEquals(movie.getIdMovie(), detail.getMovie().getIdMovie());
    assertEquals(room.getIdRoom(), detail.getRoom().getIdRoom());
    assertEquals(50, detail.getRoom().getCapacity());
  }

  @Test
  void toDetail_withNull_shouldReturnNull() {
    assertNull(projectionMapper.toDetail(null));
  }
}
