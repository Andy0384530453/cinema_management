package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ReservationTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    Instant createdAt = Instant.now();
    User user = User.builder().idUser(UUID.randomUUID()).build();
    Projection projection = Projection.builder().idProjection(UUID.randomUUID()).build();
    Seat seat = Seat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();
    Reservation reservation =
        Reservation.builder()
            .idReservation(id)
            .user(user)
            .projection(projection)
            .createdAt(createdAt)
            .seats(Set.of(seat))
            .build();

    assertNotNull(new Reservation());
    assertEquals(id, reservation.getIdReservation());
    assertEquals(user, reservation.getUser());
    assertEquals(projection, reservation.getProjection());
    assertEquals(createdAt, reservation.getCreatedAt());
    assertEquals(Set.of(seat), reservation.getSeats());

    reservation.setIdReservation(id);
    assertEquals(id, reservation.getIdReservation());
  }
}
