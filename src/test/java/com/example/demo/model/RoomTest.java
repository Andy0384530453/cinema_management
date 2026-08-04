package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RoomTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    Room room = Room.builder().idRoom(id).number("A1").capacity(50).build();

    assertNotNull(new Room());
    assertEquals(id, room.getIdRoom());
    assertEquals("A1", room.getNumber());
    assertEquals(50, room.getCapacity());
    assertEquals(Set.of(), room.getSeats());

    room.setNumber("B2");
    room.setCapacity(100);
    assertEquals("B2", room.getNumber());
    assertEquals(100, room.getCapacity());
  }

  @Test
  void builderWithSeats_shouldExposeSeats() {
    Seat seat = Seat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();

    Room room = Room.builder().idRoom(UUID.randomUUID()).seats(Set.of(seat)).build();

    assertEquals(Set.of(seat), room.getSeats());
  }
}
