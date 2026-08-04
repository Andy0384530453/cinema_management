package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProjectionTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    Instant datetime = Instant.now();
    Movie movie = Movie.builder().idMovie(UUID.randomUUID()).build();
    Room room = Room.builder().idRoom(UUID.randomUUID()).build();
    Projection projection =
        Projection.builder()
            .idProjection(id)
            .datetime(datetime)
            .seatPrice(new BigDecimal("12.50"))
            .movie(movie)
            .room(room)
            .build();

    assertNotNull(new Projection());
    assertEquals(id, projection.getIdProjection());
    assertEquals(datetime, projection.getDatetime());
    assertEquals(new BigDecimal("12.50"), projection.getSeatPrice());
    assertEquals(movie, projection.getMovie());
    assertEquals(room, projection.getRoom());

    projection.setSeatPrice(new BigDecimal("15.00"));
    assertEquals(new BigDecimal("15.00"), projection.getSeatPrice());
  }
}