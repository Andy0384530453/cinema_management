package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ReservationTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    User user = User.builder().idUser(UUID.randomUUID()).build();
    Projection projection = Projection.builder().idProjection(UUID.randomUUID()).build();
    Reservation reservation =
        Reservation.builder().idReservation(id).user(user).projection(projection).build();

    assertNotNull(new Reservation());
    assertEquals(id, reservation.getIdReservation());
    assertEquals(user, reservation.getUser());
    assertEquals(projection, reservation.getProjection());

    reservation.setIdReservation(id);
    assertEquals(id, reservation.getIdReservation());
  }
}