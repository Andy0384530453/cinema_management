package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JMovie;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JRoom;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import com.example.demo.model.Projection;
import com.example.demo.model.Room;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JProjectionMapperTest {
  private final JMovieMapper movieMapper = new JMovieMapper();
  private final JRoomMapper roomMapper = new JRoomMapper(new JSeatMapper());
  private final JProjectionMapper jProjectionMapper =
      new JProjectionMapper(movieMapper, roomMapper);

  @Test
  void toJpa_shouldMapAllFields() {
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

    JProjection jProjection = jProjectionMapper.toJpa(projection);

    assertEquals(projection.getIdProjection(), jProjection.getIdProjection());
    assertEquals(Instant.parse("2026-08-10T20:00:00Z"), jProjection.getDatetime());
    assertEquals(new BigDecimal("12.50"), jProjection.getSeatPrice());
    assertEquals(movie.getIdMovie(), jProjection.getMovie().getIdMovie());
    assertEquals("Inception", jProjection.getMovie().getTitle());
    assertEquals(room.getIdRoom(), jProjection.getRoom().getIdRoom());
    assertEquals(50, jProjection.getRoom().getCapacity());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JMovie jMovie =
        JMovie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();
    JRoom jRoom = JRoom.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();
    JProjection jProjection =
        JProjection.builder()
            .idProjection(UUID.randomUUID())
            .datetime(Instant.parse("2026-08-10T20:00:00Z"))
            .seatPrice(new BigDecimal("12.50"))
            .movie(jMovie)
            .room(jRoom)
            .build();

    Projection projection = jProjectionMapper.toDomain(jProjection);

    assertEquals(jProjection.getIdProjection(), projection.getIdProjection());
    assertEquals(Instant.parse("2026-08-10T20:00:00Z"), projection.getDatetime());
    assertEquals(new BigDecimal("12.50"), projection.getSeatPrice());
    assertEquals(jMovie.getIdMovie(), projection.getMovie().getIdMovie());
    assertEquals("Inception", projection.getMovie().getTitle());
    assertEquals(jRoom.getIdRoom(), projection.getRoom().getIdRoom());
    assertEquals(50, projection.getRoom().getCapacity());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jProjectionMapper.toJpa(null));
    assertNull(jProjectionMapper.toDomain(null));
  }
}
